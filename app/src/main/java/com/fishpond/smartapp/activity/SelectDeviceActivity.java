package com.fishpond.smartapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Toast;

import com.fishpond.smartapp.BaseActivity;
import com.fishpond.smartapp.MainActivity;
import com.fishpond.smartapp.R;
import com.fishpond.smartapp.adapter.DeviceAdapter;
import com.fishpond.smartapp.api.ObserverApiCallBack;
import com.fishpond.smartapp.bean.UserBean;
import com.fishpond.smartapp.bean.UserConfigBean;
import com.fishpond.smartapp.manager.UserManager;
import com.fishpond.smartapp.manager.control.DeviceCommand;
import com.fishpond.smartapp.utils.CommonUtils;
import com.fishpond.smartapp.utils.UrlUtils;
import com.fishpond.smartapp.utils.Utils;
import com.fishpond.smartapp.view.BottomLayout;
import com.gatz.netty.UserInfo;
import com.gatz.netty.global.AppGlobal;
import com.gatz.netty.utils.NettyUtils;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SelectDeviceActivity extends BaseActivity {
    private final static String TAG = "SelectDeviceActivity-";

    private List<UserConfigBean> devices = new ArrayList<>(); //设备个数
    private DeviceAdapter deviceAdapter;

    @BindView(R.id.device_recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.bottom_layout1)
    BottomLayout bottomLayout;

    private DeviceAdapter.OnRecyclerItemClick onRecyclerItemClick = new DeviceAdapter.OnRecyclerItemClick() {
        @Override
        public void onItemClick(int pos) {
            CommonUtils.showLogE(TAG, "======" + pos);
            UserManager.getInstance().setConfigBean(devices.get(pos));
            //进入房间
            DeviceCommand.sendInRoomCmd(devices.get(pos));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void initData() {
        devices = (List<UserConfigBean>) getIntent().getSerializableExtra("devices");
        if (devices == null) {
            return;
        }
        CommonUtils.showLogE(TAG, "===================" + devices.size());
        deviceAdapter = new DeviceAdapter(this, devices);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(deviceAdapter);
        deviceAdapter.setClick(onRecyclerItemClick);
        bottomLayout.setSelect(2);
        bottomLayout.setBottomClick(new BottomLayout.BottomClick() {
            @Override
            public void onBottomClick(int pos) {
                if (pos == 1) {
                    startActivity(new Intent(SelectDeviceActivity.this, EnviomentActivity.class));
                } else if (pos == 3) {
                    startActivity(new Intent(SelectDeviceActivity.this, SettingActivity.class));
                }
            }
        });
        RxBus.get().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.get().unregister(this);
        CommonUtils.destoryConnect();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_device;
    }

    @Subscribe(thread = EventThread.MAIN_THREAD, tags = {
            @Tag(Utils.TAG_IN)})
    public void getInReponse(String s) {
        //进入成功
        CommonUtils.showLogE(TAG, "AppInMarketResponse");
        startActivity(new Intent(SelectDeviceActivity.this, FishPondsActivity.class));
    }

    @Subscribe(thread = EventThread.MAIN_THREAD,
            tags = {@Tag(Utils.TAG_SESSION_INVALID)})
    public void getKnxConnectTag(String tag) {
        if (tag.equals(Utils.TAG_SESSION_INVALID)){
            logInAgain();
        }
    }

    private void logInAgain() {
        String name = CommonUtils.SpGetString(getApplicationContext(), "name", "");
        String pwd = CommonUtils.SpGetString(getApplicationContext(), "pwd", "");
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pwd)) {
            observerApiCallBack.setTag(UrlUtils.TAG_GET_LOGIN);
            MainActivity.doLogin(name, pwd, String.valueOf(System.currentTimeMillis()),
                    observerApiCallBack);
        }
    }

    private ObserverApiCallBack observerApiCallBack = new ObserverApiCallBack() {
        @Override
        public void onSuccessHttp(Object o, int tag) {
            if (tag == UrlUtils.TAG_GET_LOGIN) {
                UserBean bean = (UserBean) o;
                if (bean != null) {
                    CommonUtils.showLogE(TAG, bean.getMobile());
                    UserInfo userInfo = new UserInfo();
                    userInfo.setSessionId(bean.getSessionId());
                    userInfo.setUserName(bean.getUserName());
                    userInfo.setUserId(bean.getMobile());
                    userInfo.setLanIp(CommonUtils.getIPAddress(getApplicationContext()));
                    AppGlobal.getInstance().setUserInfo(userInfo);
                    MainActivity.doNettyConnect();
                    UserManager.getInstance().setId(String.valueOf(bean.getId()));
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


    @Override
    protected void afterCreate(Bundle savedInstanceState) {
        initData();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        NettyUtils.pingRequest();
        bottomLayout.setSelect(2);
    }
}
