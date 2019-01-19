package com.fishpond.smartapp.bean;

/**
 * Created by zhouh on 2018/12/23.
 */
public class DeviceBeanInfo {
    private boolean isOn;
    private GatewayBean.DeviceBean bean;

    public DeviceBeanInfo(){

    }

    public void setBean(GatewayBean.DeviceBean bean) {
        this.bean = bean;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public GatewayBean.DeviceBean getBean() {
        return bean;
    }

    public boolean isOn() {
        return isOn;
    }
}

