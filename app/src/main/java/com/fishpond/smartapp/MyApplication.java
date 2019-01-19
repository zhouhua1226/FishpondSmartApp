package com.fishpond.smartapp;

import android.app.Activity;
import android.app.Application;

import com.fishpond.smartapp.utils.CommonUtils;
import com.fishpond.smartapp.utils.Utils;
import com.gatz.netty.global.ConnectResultEvent;
import com.gatz.netty.observer.HandlerObserver;
import com.gatz.netty.observer.RequestSubscriber;
import com.gatz.netty.observer.SuberInfo;
import com.george.iot.fish.server.entity.json.app.AppInMarketResponse;
import com.george.iot.fish.server.entity.json.control.GetStatusResponse;
import com.george.iot.fish.server.entity.json.control.SwitchControlResponse;
import com.george.iot.fish.server.entity.json.enums.FromType;
import com.hwangjr.rxbus.RxBus;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhouh on 2018/12/1.
 */
public class MyApplication extends Application{
    private static final String TAG = "MyApplication-";
    private List<Activity> mList = new LinkedList<>();
    private static MyApplication instance = null;

    @Override
    public void onCreate() {
        super.onCreate();
        setHandlerCallBack();
        RxBus.get().register(this);
    }

    private void setHandlerCallBack() {
        if (!HandlerObserver.getInstance().getRequestSubscriberSet()) {
            HandlerObserver.getInstance().setRequestSubscriber(requestSubscriber);
        }
    }

    private RequestSubscriber requestSubscriber = new RequestSubscriber() {
        @Override
        public void _onSuccess(SuberInfo suberInfo) {
            String tag =  suberInfo.getTag();
            Object[] objs = suberInfo.getObject();
            CommonUtils.showLogE(TAG, "===" + tag);
            if (tag.equals(ConnectResultEvent.SWITCH_RESPONSE)) {
                SwitchControlResponse switchControlResponse = (SwitchControlResponse) objs[0];
                if (switchControlResponse.getFrom().name().equals(FromType.GATEWAY.name())) {
                    String r = switchControlResponse.getAddr() + "-" + switchControlResponse.getValue().name();
                    RxBus.get().post(Utils.TAG_SWITCH, r);
                }
            } else if (tag.equals(ConnectResultEvent.ROOM_IN_RESPONSE)) {
                RxBus.get().post(Utils.TAG_IN, "in");
            } else if (tag.equals(ConnectResultEvent.GET_STATUS_SUCESS)) {
                GetStatusResponse getStatusResponse = (GetStatusResponse) objs[0];
                RxBus.get().post(Utils.TAG_STATUS, getStatusResponse.getValue());
            } else if ((tag.equals(ConnectResultEvent.SESSION_INVALID) ||
                    (tag.equals(ConnectResultEvent.CONNECT_SESSION_INVALID)))) {
                RxBus.get().post(Utils.TAG_SESSION_INVALID, "");
            }

        }

        @Override
        public void _onError(Throwable throwable) {

        }
    };

    public static MyApplication getInstance() {
        return instance;
    }

    // add Activity
//    public void addActivity(Activity activity) {
//        mList.add(activity);
//    }
//
//    public void exit(boolean isCrash) {
//
//        try {
//            for (Activity activity : mList) {
//                if (activity != null)
//                    activity.finish();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (isCrash) {
//                System.exit(0);
//            }
//            com.fishpond.smartapp.utils.Utils.isExit = true;
//        }
//    }
}
