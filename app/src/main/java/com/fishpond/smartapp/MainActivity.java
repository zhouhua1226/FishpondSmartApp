package com.fishpond.smartapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fishpond.smartapp.activity.SelectDeviceActivity;
import com.fishpond.smartapp.api.ObserverApiCallBack;
import com.fishpond.smartapp.api.UserApi;
import com.fishpond.smartapp.bean.GatewayBean;
import com.fishpond.smartapp.bean.UserBean;
import com.fishpond.smartapp.bean.UserConfigBean;
import com.fishpond.smartapp.manager.UserManager;
import com.fishpond.smartapp.manager.control.DeviceCommand;
import com.fishpond.smartapp.utils.CmdType;
import com.fishpond.smartapp.utils.CommonUtils;
import com.fishpond.smartapp.utils.UrlUtils;
import com.fishpond.smartapp.utils.Utils;
import com.gatz.netty.AppClient;
import com.gatz.netty.UserInfo;
import com.gatz.netty.global.AppGlobal;
import com.gatz.netty.utils.NettyUtils;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends Activity {
    private final static String TAG = "MainActivity-";
    @BindView(R.id.et_name)
    EditText nameEt;
    @BindView(R.id.et_pwd)
    EditText pwdEt;
    @BindView(R.id.login_iv)
    ImageView logInIv;

    private String name;
    private String pwd;
    //18210331208 123456
    private ObserverApiCallBack observerApiCallBack = new ObserverApiCallBack() {
        @Override
        public void onSuccessHttp(Object o, int tag) {
            if (tag == UrlUtils.TAG_GET_LOGIN) {
                UserBean bean = (UserBean) o;
                CommonUtils.showLogE(TAG, bean.getMobile());
                setUserInfo(bean);
                observerApiCallBack.setTag(UrlUtils.TAG_GET_CONFIG);
                UserApi.getInstance().getUserConfig(UserManager.getInstance().getId(),
                        observerApiCallBack);
                CommonUtils.SpPutString(getApplicationContext(), "name", name);
                CommonUtils.SpPutString(getApplicationContext(), "pwd", pwd);

            } else if (tag == UrlUtils.TAG_GET_CONFIG) {
                List<UserConfigBean> devices = (List<UserConfigBean>) o;
                CommonUtils.showLogE(TAG, "===TAG_GET_CONFIG====" + devices.size());
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("devices", (Serializable) devices);
                intent.putExtras(bundle);
                intent.setClass(MainActivity.this, SelectDeviceActivity.class);
                startActivity(intent);
                finish();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);//闪屏
        connectService();
        NettyUtils.registerAppManager();
        initWelcome();
        Log.e("ty", "====" + CmdType.getSceneName(1, "ty"));
    }

    @OnClick({R.id.login_iv})
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.login_iv) {
            name = nameEt.getText().toString();
            pwd = pwdEt.getText().toString();
            if (CommonUtils.isEmpty(name)) {
                Toast.makeText(getApplicationContext(), "用户名不能为空,hhhhh!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (CommonUtils.isEmpty(pwd)) {
                Toast.makeText(getApplicationContext(), "密码不能为空!", Toast.LENGTH_SHORT).show();
                return;
            }
            observerApiCallBack.setTag(UrlUtils.TAG_GET_LOGIN);
            doLogin(name, pwd,
                    String.valueOf(System.currentTimeMillis()),
                    observerApiCallBack);
        }
    }

    private void connectService() {
        AppClient.getInstance().setHost("60.205.184.214");
        AppClient.getInstance().setPort(6570);
        new Thread(new Runnable() {
            @Override
            public void run() {
                NettyUtils.socketConnect(getResources(), getApplicationContext());
            }
        }).start();
    }

    public static void doLogin(String phone, String pwd, String deviceNo, ObserverApiCallBack<UserBean> observerApiCallBack) {
        observerApiCallBack.setTag(UrlUtils.TAG_GET_LOGIN);
        UserApi.getInstance().logIn(phone, pwd, deviceNo, observerApiCallBack);
    }

    private void setUserInfo(UserBean bean) {
        if (bean == null) return;
        UserInfo userInfo = new UserInfo();
        userInfo.setSessionId(bean.getSessionId());
        userInfo.setUserName(bean.getUserName());
        userInfo.setUserId(bean.getMobile());
        userInfo.setLanIp(CommonUtils.getIPAddress(getApplicationContext()));
        AppGlobal.getInstance().setUserInfo(userInfo);
        doNettyConnect();
        UserManager.getInstance().setId(String.valueOf(bean.getId()));
    }

    public static void doNettyConnect() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (NettyUtils.socketTag) {
                        AppClient.getInstance().doConnect();
                        break;
                    }
                    if (Utils.isExit) {
                        break;
                    }
                }
            }
        }).start();
    }

    private void initWelcome() {
        new Handler().postDelayed(initRunnable, 3000);
    }

    private Runnable initRunnable = new Runnable() {
        @Override
        public void run() {
            View MainView = getLayoutInflater().inflate(R.layout.activity_main, null);
            setContentView(MainView);
            ButterKnife.bind(MainActivity.this);
            name = CommonUtils.SpGetString(getApplicationContext(), "name", "");
            pwd = CommonUtils.SpGetString(getApplicationContext(), "pwd", "");
            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(pwd)) {
                nameEt.setText(name);
                pwdEt.setText(pwd);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("TAG,", "onDestroy");
        //NettyUtils.destoryConnect();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
