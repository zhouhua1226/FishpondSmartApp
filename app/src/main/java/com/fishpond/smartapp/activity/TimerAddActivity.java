package com.fishpond.smartapp.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fishpond.smartapp.R;
import com.fishpond.smartapp.api.TimeApi;
import com.fishpond.smartapp.api.TimeObserverCallBack;
import com.fishpond.smartapp.bean.GatewayBean;
import com.fishpond.smartapp.bean.time.JobDo;
import com.fishpond.smartapp.bean.time.TimeBean;
import com.fishpond.smartapp.bean.time.TriggerDos;
import com.fishpond.smartapp.manager.UserManager;
import com.fishpond.smartapp.utils.CommonUtils;
import com.fishpond.smartapp.utils.TimeUtils;
import com.gatz.netty.utils.NettyUtils;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimerAddActivity extends Activity implements View.OnClickListener{
    @BindView(R.id.saveBtn)
    ImageButton timeSaveBtn;
    @BindView(R.id.d1)
    Button d1;
    @BindView(R.id.d2)
    Button d2;
    @BindView(R.id.d3)
    Button d3;
    @BindView(R.id.d4)
    Button d4;
    @BindView(R.id.d5)
    Button d5;
    @BindView(R.id.d6)
    Button d6;
    @BindView(R.id.d7)
    Button d7;

    @BindView(R.id.openBtn)
    Button openBtn;
    @BindView(R.id.closeBtn)
    Button closeBtn;
    private static final String TAG = "TimerAddActivity-";
    private WheelView<String> h_wheelView;
    private WheelView<String> m_wheelView;
    private Map<String, Boolean> dataMap;
    private int timeStatus = 2;
    private JobDo mJobDo;
    private GatewayBean.DeviceBean bean;

    private TimeObserverCallBack observerCallBack = new TimeObserverCallBack() {
        @Override
        public void onSuccessHttp(String responseInfo, int resultCode) {
            if (resultCode == 1) {
                CommonUtils.showLogE(TAG, "=onSuccessHttp==" + responseInfo);
                if (responseInfo.equals("true")) {
                    finish();
                }
            } else if (resultCode == 2) {
                CommonUtils.showLogE(TAG, "=onSuccessHttp==" + responseInfo);
            }
        }

        @Override
        public void onFailureHttp(IOException e, int resultCode) {
            if (resultCode == 1) {
                CommonUtils.showLogE(TAG, "=onFailureHttp==" + e.getMessage());
                Toast.makeText(getApplicationContext(), "添加定时器任务失败!",
                        Toast.LENGTH_SHORT).show();
            } else if (resultCode == 2) {
                CommonUtils.showLogE(TAG, "=onFailureHttp==" + e.getMessage());
                Toast.makeText(getApplicationContext(), "添加定时器触发器失败!",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_add);
        initData();
    }

    private void initData() {
        ButterKnife.bind(this);
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mJobDo = (JobDo) getIntent().getSerializableExtra("jobs");
        bean = (GatewayBean.DeviceBean) getIntent().getSerializableExtra("device");
        if (bean == null) {
            return;
        }
        h_wheelView = (WheelView<String>) findViewById(R.id.time_set_hour_wheelview);
        m_wheelView = (WheelView<String>) findViewById(R.id.time_set_minute_wheelview);
        timeSaveBtn.setOnClickListener(this);
        d1.setOnClickListener(this);
        d2.setOnClickListener(this);
        d3.setOnClickListener(this);
        d4.setOnClickListener(this);
        d5.setOnClickListener(this);
        d6.setOnClickListener(this);
        d7.setOnClickListener(this);
        openBtn.setOnClickListener(this);
        closeBtn.setOnClickListener(this);
        h_wheelView.setWheelAdapter(new ArrayWheelAdapter(this));
        h_wheelView.setSkin(WheelView.Skin.Holo);
        h_wheelView.setWheelData(createHours());
        h_wheelView.setWheelSize(5);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextColor = Color.parseColor("#808080");
        style.selectedTextSize = 26;
        style.textSize = 23;
        style.textColor = Color.parseColor("#adadad");
        style.backgroundColor = Color.TRANSPARENT;
        style.holoBorderColor = Color.parseColor("#adadad");
        h_wheelView.setStyle(style);

        m_wheelView.setWheelAdapter(new ArrayWheelAdapter(this));
        m_wheelView.setWheelSize(5);
        m_wheelView.setSkin(WheelView.Skin.Holo);
        m_wheelView.setWheelData(createMinutes());
        m_wheelView.setStyle(style);

        dataMap = new HashMap<>();
        for(int i=1;i<8;i++) {
            dataMap.put(String.valueOf(i), false);
        }
    }

    private List<String> createHours() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                list.add("0" + i);
            } else {
                list.add("" + i);
            }
        }
        return list;
    }

    private List<String> createMinutes() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                list.add("0" + i);
            } else {
                list.add("" + i);
            }
        }
        return list;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        boolean isSelect;
        if (id == R.id.saveBtn) {
            String oh = h_wheelView.getSelectionItem();
            String om = m_wheelView.getSelectionItem();
            CommonUtils.showLogE("======", "=====" + oh + "=====" + om);
            if (timeStatus == 2) {
                Toast.makeText(getApplicationContext(), "请选择开关!", Toast.LENGTH_SHORT).show();
                return;
            }
            String data = isSetDay();
            if (TextUtils.isEmpty(data)) {
                Toast.makeText(getApplicationContext(), "请选择日期!", Toast.LENGTH_SHORT).show();
                return;
            }
            saveTime(data, om, oh);
//            if (mJobDo == null) {
//                //需要创建任务
//                saveTime(data, om, oh);
//            } else {
//                //创建触发器
//                saveTriggerDo(data, om, oh);
//            }

        } else if (id == R.id.d1) {
            isSelect = !dataMap.get("1");
            setDay(d1, isSelect);
            dataMap.put("1", isSelect);
        } else if (id == R.id.d2) {
            isSelect = !dataMap.get("2");
            setDay(d2, isSelect);
            dataMap.put("2", isSelect);
        } else if (id == R.id.d3) {
            isSelect = !dataMap.get("3");
            setDay(d3, isSelect);
            dataMap.put("3", isSelect);
        } else if (id == R.id.d4) {
            isSelect = !dataMap.get("4");
            setDay(d4, isSelect);
            dataMap.put("4", isSelect);
        } else if (id == R.id.d5) {
            isSelect = !dataMap.get("5");
            setDay(d5, isSelect);
            dataMap.put("5", isSelect);
        } else if (id == R.id.d6) {
            isSelect = !dataMap.get("6");
            setDay(d6, isSelect);
            dataMap.put("6", isSelect);
        } else if (id == R.id.d7) {
            isSelect = !dataMap.get("7");
            setDay(d7, isSelect);
            dataMap.put("7", isSelect);
        } else if (id == R.id.openBtn) {
            timeStatus = 1;
            openBtn.setBackground(getResources().getDrawable(R.drawable.on_p));
            closeBtn.setBackground(getResources().getDrawable(R.drawable.off_n));
        } else if (id == R.id.closeBtn) {
            timeStatus = 0;
            openBtn.setBackground(getResources().getDrawable(R.drawable.on_n));
            closeBtn.setBackground(getResources().getDrawable(R.drawable.off_p));
        }
    }

    private void saveTriggerDo(String data, String om, String oh) {
        String tag = "";
        TriggerDos triggerDos = TimeUtils.setTriggerDos(
                bean.getFacilityCode(),
                UserManager.getInstance().getConfigBean().getStoreCode(),
                TimeUtils.getTimeCronExpression(data, om, oh),
                TimeUtils.setDescription(om, oh, timeStatus, data),
                timeStatus);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            tag = objectMapper.writeValueAsString(triggerDos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(tag)) {
            TimeApi.getInstance().addTriggerDo(
                    UserManager.getInstance().getConfigBean().getStoreCode(),
                    bean.getFacilityCode(),
                    tag,
                    observerCallBack,
                    2);
        }
    }

    private void saveTime(String data, String om, String oh) {
        String tag = "";
        List<TriggerDos> triggerDoses = new ArrayList<>();
        TriggerDos triggerDos = TimeUtils.setTriggerDos(
                bean.getFacilityCode(),
                UserManager.getInstance().getConfigBean().getStoreCode(),
                TimeUtils.getTimeCronExpression(data, om, oh),
                TimeUtils.setDescription(om, oh, timeStatus, data),
                timeStatus);
        triggerDoses.add(triggerDos);
        TimeBean timeBean = TimeUtils.setTimeJob(
                bean.getFacilityCode(),
                UserManager.getInstance().getConfigBean().getStoreCode(),
                bean.getName() + "order",
                "serial_485_job",
                UserManager.getInstance().getConfigBean().getStoreCode(),
                triggerDoses);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            tag = objectMapper.writeValueAsString(timeBean);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(tag)) {
            CommonUtils.showLogE(TAG, "======addOrder=======" + tag);
            TimeApi.getInstance().addOrder(tag, observerCallBack, 1);
        }
    }

    private void setDay(Button btn, boolean isSelect) {
        if (isSelect) {
            btn.setBackground(getResources().getDrawable(R.drawable.time_data_bg_p));
        } else {
            btn.setBackground(getResources().getDrawable(R.drawable.time_data_bg_n));
        }
    }

    private String isSetDay() {
        StringBuffer sp = new StringBuffer();
        Iterator<Map.Entry<String, Boolean>> iterator = dataMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Boolean> entry = iterator.next();
            if(entry.getValue()) {
                if (entry.getKey().contains("7")) {
                    sp.append("1,");
                }
                if (entry.getKey().contains("1")) {
                    sp.append("2,");
                }
                if (entry.getKey().contains("2")) {
                    sp.append("3,");
                }
                if (entry.getKey().contains("3")) {
                    sp.append("4,");
                }
                if (entry.getKey().contains("4")) {
                    sp.append("5,");
                }
                if (entry.getKey().contains("5")) {
                    sp.append("6,");
                }
                if (entry.getKey().contains("6")) {
                    sp.append("7,");
                }
            }
        }
        if (sp.length() > 0) {
            sp.deleteCharAt(sp.length() - 1);
        }
        return sp.toString();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        NettyUtils.pingRequest();
    }
}
