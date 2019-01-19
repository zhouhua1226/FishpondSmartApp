package com.fishpond.smartapp.bean.time;

import java.io.Serializable;

/**
 * Created by zhouh on 2018/12/25.
 */
public class JobDo implements Serializable{
    private String name;
    private String group;
    private String description;
    private String targetClass;
    private ExInfo extInfo;

    public void setTargetClass(String targetClass) {
        this.targetClass = targetClass;
    }

    public String getTargetClass() {
        return targetClass;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setExtInfo(ExInfo extInfo) {
        this.extInfo = extInfo;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExInfo getExtInfo() {
        return extInfo;
    }

    public String getDescription() {
        return description;
    }

    public String getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

}
