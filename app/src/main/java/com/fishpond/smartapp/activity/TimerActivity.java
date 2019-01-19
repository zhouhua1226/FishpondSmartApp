package com.fishpond.smartapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fishpond.smartapp.R;
import com.fishpond.smartapp.adapter.TimeAdapter;
import com.fishpond.smartapp.api.TimeApi;
import com.fishpond.smartapp.api.TimeObserverCallBack;
import com.fishpond.smartapp.bean.GatewayBean;
import com.fishpond.smartapp.bean.time.TimeBean;
import com.fishpond.smartapp.bean.time.TriggerDos;
import com.fishpond.smartapp.manager.UserManager;
import com.fishpond.smartapp.utils.CommonUtils;
import com.fishpond.smartapp.utils.TimeUtils;
import com.fishpond.smartapp.view.TimeDialog;
import com.gatz.netty.global.AppGlobal;
import com.gatz.netty.utils.NettyUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimerActivity extends Activity implements TimeDialog.OnSureClick{
    private static final String TAG = "TimerActivity-";
    private GatewayBean.DeviceBean bean;

    @BindView(R.id.addTime_Iv)
    RelativeLayout addTimeBtn;
    @BindView(R.id.nameEdit_Iv)
    RelativeLayout nameEditBtn;
    @BindView(R.id.name_Tv)
    TextView nameTv;
    @BindView(R.id.timeinfo_Listview)
    ListView timeInfoListView;

    private TimeDialog timeDialog;
    private Handler handler = new Handler();
    private TimeBean timeBean;
    private TimeAdapter timeAdapter;
    private List<TriggerDos> triggerDoses = new ArrayList<>();

    private TimeObserverCallBack observerCallBack = new TimeObserverCallBack() {

        @Override
        public void onSuccessHttp(String responseInfo, int resultCode) {
            if (resultCode == 0) {
                timeBean = TimeUtils.getTimeInfos(responseInfo);
                if (timeBean.getTriggerDOs() != null) {
                    triggerDoses = timeBean.getTriggerDOs();
                } else {
                    triggerDoses.clear();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        timeAdapter.notify(triggerDoses);
                    }
                });
            } else if (resultCode == 1) {
                if (responseInfo.equals("true")) {
                    getTimeInfos();
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "删除触发器失败!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }

        @Override
        public void onFailureHttp(IOException e, int resultCode) {
            Toast.makeText(getApplicationContext(), "执行失败!",
                    Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        initData();
    }

    private void initData() {
        bean = (GatewayBean.DeviceBean) getIntent().getSerializableExtra("deviceBean");
        if (bean == null) {
            return;
        }
        ButterKnife.bind(this);
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        nameTv.setText(bean.getName());
        timeAdapter = new TimeAdapter(this, triggerDoses);
        timeInfoListView.setAdapter(timeAdapter);
        timeInfoListView.setOnItemLongClickListener(longClickListener);
        timeDialog = new TimeDialog(this, R.style.mDialog);
        timeDialog.setSureClick(this);
        getTimeInfos();
    }

    private AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            timeDialog._show(i);
            return false;
        }
    };

    private void deleteTime(TriggerDos dos) {
        TimeApi.getInstance().deleteTime(dos.getGroup(), dos.getName(),
                "" , observerCallBack, 1);
    }

    private void getTimeInfos() {
        String sessionId = AppGlobal.getInstance().getUserInfo().getSessionId();
        TimeApi.getInstance().getTimeInfos(observerCallBack,
                    sessionId,
                    UserManager.getInstance().getConfigBean().getStoreCode(),
                    bean.getFacilityCode(),
                    0);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getTimeInfos();
        NettyUtils.pingRequest();
    }

    @OnClick({R.id.addTime_Iv, R.id.nameEdit_Iv})
    public void onBtnClick(View v) {
        int id = v.getId();
        if (id == R.id.addTime_Iv) {
            CommonUtils.showLogE(TAG, "==addTime==");
            Intent intent = new Intent(TimerActivity.this, TimerAddActivity.class);
            intent.putExtra("jobs", timeBean.getJobDO());
            intent.putExtra("device", bean);
            startActivity(intent);
        } else if (id == R.id.nameEdit_Iv) {
            CommonUtils.showLogE(TAG, "==nameEdit==");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onTimeDeleteClick(int pos) {
        TriggerDos triggerDos = triggerDoses.get(pos);
        deleteTime(triggerDos);
        timeDialog.dismiss();
    }
}
