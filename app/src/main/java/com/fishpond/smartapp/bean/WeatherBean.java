package com.fishpond.smartapp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by YANYI on 2019/1/6.
 */
public class WeatherBean {
    @SerializedName("HeWeather6")
    private List<WeatherInfo> weatherBeans;

    public void setWeatherBeans(List<WeatherInfo> weatherBeans) {
        this.weatherBeans = weatherBeans;
    }

    public List<WeatherInfo> getWeatherBeans() {
        return weatherBeans;
    }

    public class WeatherInfo {
        private String status;
        private Now now;

        public void setNow(Now now) {
            this.now = now;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Now getNow() {
            return now;
        }

        public String getStatus() {
            return status;
        }
    }

    public class Now {
        private String tmp;
        private String wind_dir;
        private String wind_sc;
        private String hum;
        @SerializedName("cond_txt")
        private String condTxt;

        public void setCondTxt(String condTxt) {
            this.condTxt = condTxt;
        }

        public String getCondTxt() {
            return condTxt;
        }

        public void setHum(String hum) {
            this.hum = hum;
        }

        public void setTmp(String tmp) {
            this.tmp = tmp;
        }

        public void setWind_dir(String wind_dir) {
            this.wind_dir = wind_dir;
        }

        public void setWind_sc(String wind_sc) {
            this.wind_sc = wind_sc;
        }

        public String getHum() {
            return hum;
        }

        public String getTmp() {
            return tmp;
        }

        public String getWind_dir() {
            return wind_dir;
        }

        public String getWind_sc() {
            return wind_sc;
        }
    }


}
