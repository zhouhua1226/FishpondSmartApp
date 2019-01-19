package com.fishpond.smartapp.bean.time;

import java.io.Serializable;
import java.util.List;


/**
 * Created by zhouh on 2018/12/25.
 */
public class TimeBean implements Serializable {
    private JobDo jobDO;
    private List<TriggerDos> triggerDOs;

    public void setJobDO(JobDo jobDO) {
        this.jobDO = jobDO;
    }

    public JobDo getJobDO() {
        return jobDO;
    }

    public void setTriggerDOs(List<TriggerDos> triggerDOs) {
        this.triggerDOs = triggerDOs;
    }

    public List<TriggerDos> getTriggerDOs() {
        return triggerDOs;
    }
}
