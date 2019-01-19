package com.fishpond.smartapp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhouh on 2018/12/15.
 */
public class GatewayBean {
    private String storeCode;
    private int fishNo;
    private String fishName;

    private List<DeviceBean> list;

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public void setFishName(String fishName) {
        this.fishName = fishName;
    }

    public void setFishNo(int fishNo) {
        this.fishNo = fishNo;
    }

    public int getFishNo() {
        return fishNo;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public String getFishName() {
        return fishName;
    }


    public void setList(List<DeviceBean> list) {
        this.list = list;
    }

    public List<DeviceBean> getList() {
        return list;
    }

    public class DeviceBean implements Serializable{
        private String facilityCode;
        private String name;

        public void setFacilityCode(String facilityCode) {
            this.facilityCode = facilityCode;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFacilityCode() {
            return facilityCode;
        }

        public String getName() {
            return name;
        }
    }
}
