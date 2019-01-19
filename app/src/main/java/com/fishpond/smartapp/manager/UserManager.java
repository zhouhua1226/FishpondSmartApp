package com.fishpond.smartapp.manager;

import com.fishpond.smartapp.bean.UserConfigBean;

import java.util.List;

/**
 * Created by zhouh on 2018/12/15.
 */
public class UserManager {
    private static UserManager inStance;

    private String id;
    private UserConfigBean configBean;

    private String city;
    private String district;

    private UserManager(){

    }

    public static synchronized UserManager getInstance() {
        if (inStance == null) {
            inStance = new UserManager();
        }
        return inStance;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setConfigBean(UserConfigBean configBean) {
        this.configBean = configBean;
    }

    public UserConfigBean getConfigBean() {
        return configBean;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDistrict() {
        return district;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }
}
