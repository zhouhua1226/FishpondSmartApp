package com.fishpond.smartapp.manager.control;

import com.fishpond.smartapp.bean.GatewayBean;
import com.fishpond.smartapp.bean.UserConfigBean;
import com.fishpond.smartapp.manager.UserManager;
import com.fishpond.smartapp.utils.CmdType;
import com.fishpond.smartapp.utils.CommonUtils;
import com.gatz.netty.UserInfo;
import com.gatz.netty.common.AppConstants;
import com.gatz.netty.global.AppGlobal;
import com.gatz.netty.manager.SendManager;
import com.george.iot.fish.server.entity.json.app.AppInMarketRequest;
import com.george.iot.fish.server.entity.json.control.SwitchControlRequest;
import com.george.iot.fish.server.entity.json.enums.FromType;
import com.george.iot.fish.server.entity.json.enums.control.OnOffType;

/**
 * Created by zhouh on 2018/12/15.
 */
public class DeviceCommand {

    public static void sendInRoomCmd(UserConfigBean bean) {
        String storeCode = bean.getStoreCode();
        if (CommonUtils.isEmpty(storeCode)) {
            return;
        }
        AppInMarketRequest appInMarketRequest = new AppInMarketRequest();
        appInMarketRequest.setMarketId(storeCode);
        appInMarketRequest.setMarketName(bean.getStoreName());
        UserInfo userInfo = AppGlobal.getInstance().getUserInfo();
        if (userInfo != null) {
            appInMarketRequest.setSeq(AppConstants.SEQ.incrementAndGet());
            appInMarketRequest.setSessionId(userInfo.getSessionId());
        }
        CommonUtils.showLogE("sendFishpondCmd", appInMarketRequest.toString());
        SendManager.getInstance().sendMessage(appInMarketRequest);
    }

    public static void sendFishpondCmd(GatewayBean.DeviceBean bean, CmdType type) {
        if (bean == null) {
            return;
        }
        SwitchControlRequest controlRequest = new SwitchControlRequest();
        if (bean.getFacilityCode() == null) {
           return;
        }
        controlRequest.setAddr(bean.getFacilityCode());
        if (bean.getName() != null) {
            controlRequest.setDesc(bean.getName());
        }
        controlRequest.setFrom(FromType.APP);
        if (type.name().equals(CmdType.ON.toString())) {
            controlRequest.setValue(OnOffType.ON);
        } else if (type.name().equals(CmdType.OFF.toString())) {
            controlRequest.setValue(OnOffType.OFF);
        }
        setHead(controlRequest);
        CommonUtils.showLogE("sendFishpondCmd", controlRequest.toString());
        SendManager.getInstance().sendMessage(controlRequest);
    }

    private static void setHead(SwitchControlRequest request) {
        if (null != request) {
            UserInfo userInfo = AppGlobal.getInstance().getUserInfo();
            if (userInfo != null) {
                request.setSeq(AppConstants.SEQ.incrementAndGet());
                request.setSessionId(userInfo.getSessionId());
            }
        }
    }
}
