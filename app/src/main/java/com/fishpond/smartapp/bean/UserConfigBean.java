package com.fishpond.smartapp.bean;

import java.io.Serializable;

/**
 * Created by zhouh on 2018/12/15.
 */
public class UserConfigBean implements Serializable{
    private int id;
    private String storeCode;
    private String storeName;
    private String companyId;
    private String comCode;
    private String address;
    private String creator;
    private String updator;
    private String created;
    private String updated;
    private int yn;
    private String equCode;
    private String userName;
    private String mobile;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setEquCode(String equCode) {
        this.equCode = equCode;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public void setYn(int yn) {
        this.yn = yn;
    }

    public String getEquCode() {
        return equCode;
    }

    public String getUserName() {
        return userName;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public String getUpdated() {
        return updated;
    }

    public int getId() {
        return id;
    }

    public int getYn() {
        return yn;
    }

    public String getAddress() {
        return address;
    }

    public String getComCode() {
        return comCode;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getCreated() {
        return created;
    }

    public String getCreator() {
        return creator;
    }

    public String getUpdator() {
        return updator;
    }

    public String getMobile() {
        return mobile;
    }

    public String getStoreName() {
        return storeName;
    }
}
