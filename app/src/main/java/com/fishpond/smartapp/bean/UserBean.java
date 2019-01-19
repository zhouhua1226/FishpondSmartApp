package com.fishpond.smartapp.bean;

import java.io.Serializable;

/**
 * Created by zhouh on 2018/12/1.
 */
public class UserBean implements Serializable {
    private int id;
    private String userName;
    private String password;
    private String nickName;
    private String mobile;
    private String email;
    private String creator;
    private String updator;
    private String created;
    private String updated;
    private int yn;
    private String roleId;
    private String storeNum;
    private String storeCode;
    private String sessionId;
    private String userRole;


    public void setId(int id) {
        this.id = id;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setYn(int yn) {
        this.yn = yn;
    }

    public int getId() {
        return id;
    }

    public int getYn() {
        return yn;
    }

    public String getCreated() {
        return created;
    }

    public String getCreator() {
        return creator;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPassword() {
        return password;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getUpdated() {
        return updated;
    }

    public String getUpdator() {
        return updator;
    }

    public String getUserName() {
        return userName;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public void setStoreNum(String storeNum) {
        this.storeNum = storeNum;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public String getStoreNum() {
        return storeNum;
    }

    public String getUserRole() {
        return userRole;
    }
}
