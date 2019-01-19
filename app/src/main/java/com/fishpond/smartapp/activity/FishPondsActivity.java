package com.fishpond.smartapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fishpond.smartapp.R;
import com.fishpond.smartapp.adapter.FishPondAdapter;
import com.fishpond.smartapp.api.ObserverApiCallBack;
import com.fishpond.smartapp.api.UserApi;
import com.fishpond.smartapp.bean.DeviceBeanInfo;
import com.fishpond.smartapp.bean.GatewayBean;
import com.fishpond.smartapp.manager.UserManager;
import com.fishpond.smartapp.utils.CommonUtils;
import com.fishpond.smartapp.utils.UrlUtils;
import com.fishpond.smartapp.utils.Utils;
import com.fishpond.smartapp.view.BottomLayout;
import com.gatz.netty.utils.NettyUtils;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FishPondsActivity extends Activity implements FishPondAdapter.ArrowCopyClickListener{
    @BindView(R.id.fish_text)
    TextView fishText;
    @BindView(R.id.list)
    ListView listView;
    @BindView(R.id.bottom_layout2)
    BottomLayout bottomLayout;

    private static final String TAG = "FishPondsActivity-";
    private List<DeviceBeanInfo> deviceBeenInfos = new ArrayList<>();
    private FishPondAdapter fishPondAdapter;

    private ObserverApiCallBack observerApiCallBack = new ObserverApiCallBack() {
        @Override
        public void onSuccessHttp(Object o, int tag) {
            if (tag == UrlUtils.TAG_GET_DEVICE) {
                List<GatewayBean> gatewayInfos = (List<GatewayBean>) o;
                if (gatewayInfos.size() == 0) {
                    return;
                }
                for (GatewayBean bean: gatewayInfos) {
                    for(GatewayBean.DeviceBean b: bean.getList()) {
                        DeviceBeanInfo info = new DeviceBeanInfo();
                        info.setOn(false);
                        info.setBean(b);
                        deviceBeenInfos.add(info);
                    }
                }
                if (deviceBeenInfos.size() > 0) {
                    CommonUtils.showLogE(TAG, "get=====" + deviceBeenInfos.size());
                    NettyUtils.setGetstatusCmd();
                    fishPondAdapter.notify(deviceBeenInfos);
                }
            }
        }

        @Override
        public void onFailureHttp(String e, int tag) {
            CommonUtils.showLogE(TAG, "====onFailureHttp====" + e);
            Toast.makeText(getApplicationContext(), e,
                    Toast.LENGTH_SHORT).show();
        }
    };

    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(Utils.TAG_STATUS)})
    public void getInReponse(String s) {
        //进入成功
        CommonUtils.showLogE(TAG, "GetStatusResponse:" + s);
        if (TextUtils.isEmpty(s)) {
            return;
        }
        if (s.contains(";")) {
            String[] strings = s.split(";");
            for(int i=0; i<strings.length; i++) {
                String[] ss = strings[i].split("-");
                for(int k=0; k<deviceBeenInfos.size();k++) {
                    if (ss[0].equals(deviceBeenInfos.get(k).getBean()
                            .getFacilityCode())){
                        if (ss[2].equals("ON")) {
                            deviceBeenInfos.get(k).setOn(true);
                        } else {
                            deviceBeenInfos.get(k).setOn(false);
                        }
                    }
                }
            }
        } else {
            String[] ss = s.split("-");
            for(int k=0;k<deviceBeenInfos.size();k++) {
                if (ss[0].equals(deviceBeenInfos.get(k).getBean()
                        .getFacilityCode())){
                    if (ss[2].equals("ON")) {
                        deviceBeenInfos.get(k).setOn(true);
                    } else {
                        deviceBeenInfos.get(k).setOn(false);
                    }
                }
            }
        }
        for(DeviceBeanInfo info: deviceBeenInfos) {
            CommonUtils.showLogE(TAG, info.getBean().getFacilityCode() + "====" + info.isOn());
        }
        fishPondAdapter.notify(deviceBeenInfos);
    }

    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(Utils.TAG_SWITCH)})
    public void getSwitchReponse(String s) {
        CommonUtils.showLogE(TAG, "===" + s);
        String[] ss = s.split("-");
        for(int i=0;i<deviceBeenInfos.size();i++) {
            if (ss[0].equals(deviceBeenInfos.get(i).getBean().getFacilityCode())) {
                if (ss[1].equals("ON")) {
                    deviceBeenInfos.get(i).setOn(true);
                } else {
                    deviceBeenInfos.get(i).setOn(false);
                }
                break;
            }
        }
        fishPondAdapter.notify(deviceBeenInfos);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        NettyUtils.setGetstatusCmd();
        NettyUtils.pingRequest();
        bottomLayout.setSelect(2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fish_ponds);
        ButterKnife.bind(this);
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initData();
    }

    private void initData() {
        if (UserManager.getInstance().getConfigBean() == null) {
            return;
        }
        RxBus.get().register(this);
        fishText.setText(UserManager.getInstance().getConfigBean().getStoreName());
        if (TextUtils.isEmpty(UserManager.getInstance().getConfigBean().getStoreCode())){
            return;
        }
        observerApiCallBack.setTag(UrlUtils.TAG_GET_DEVICE);
        UserApi.getInstance().getGatewayInfo(UserManager.getInstance().getConfigBean().getStoreCode(),
                observerApiCallBack);
        fishPondAdapter = new FishPondAdapter(this, deviceBeenInfos);
        listView.setAdapter(fishPondAdapter);
        fishPondAdapter.setClickListener(this);
        bottomLayout.setSelect(2);
        bottomLayout.setBottomClick(new BottomLayout.BottomClick() {
            @Override
            public void onBottomClick(int pos) {
                if (pos == 1) {
                    startActivity(new Intent(FishPondsActivity.this, EnviomentActivity.class));
                    finish();
                } else if (pos == 3) {
                    startActivity(new Intent(FishPondsActivity.this, SettingActivity.class));
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
    }

    @Override
    public void onArrowCopyClick(int pos) {
        Intent intent = new Intent();
        intent.setClass(FishPondsActivity.this, TimerActivity.class);
        intent.putExtra("deviceBean", deviceBeenInfos.get(pos).getBean());
        startActivity(intent);
    }
}
