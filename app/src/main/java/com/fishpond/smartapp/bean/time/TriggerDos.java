package com.fishpond.smartapp.bean.time;

import java.io.Serializable;

/**
 * Created by zhouh on 2018/12/25.
 */
public class TriggerDos implements Serializable{
    private String name;
    private String group;
    private String cronExpression;
    private String description;
    private String triggerState;
    private String value;

    public void setTriggerState(String triggerState) {
        this.triggerState = triggerState;
    }

    public String getTriggerState() {
        return triggerState;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }


    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public String getDescription() {
        return description;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
