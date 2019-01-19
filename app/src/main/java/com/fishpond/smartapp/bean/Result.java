package com.fishpond.smartapp.bean;


import java.io.Serializable;

public class Result<T> implements Serializable {

    private int code;
    private T obj;
    private String des;

    public void setDes(String des) {
        this.des = des;
    }

    public String getDes() {
        return des;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public T getObj() {
        return obj;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}