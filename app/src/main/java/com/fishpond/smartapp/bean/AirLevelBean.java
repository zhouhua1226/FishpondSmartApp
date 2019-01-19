package com.fishpond.smartapp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by YANYI on 2019/1/6.
 */
public class AirLevelBean {
    @SerializedName("HeWeather6")
    private List<AirNowBean> airLevelBean;

    public void setAirLevelBean(List<AirNowBean> airLevelBean) {
        this.airLevelBean = airLevelBean;
    }

    public List<AirNowBean> getAirLevelBean() {
        return airLevelBean;
    }

    public class AirNowBean {
        @SerializedName("air_now_city")
        private AirNowCity city;
        private String status;

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public void setCity(AirNowCity city) {
            this.city = city;
        }

        public AirNowCity getCity() {
            return city;
        }
    }

    public class AirNowCity{
       private String qlty;

        public void setQlty(String qlty) {
            this.qlty = qlty;
        }

        public String getQlty() {
            return qlty;
        }
    }
}
