package com.fishpond.smartapp.utils;

import android.text.TextUtils;
import android.util.Log;

import com.fishpond.smartapp.bean.time.ExInfo;
import com.fishpond.smartapp.bean.time.JobDo;
import com.fishpond.smartapp.bean.time.TimeBean;
import com.fishpond.smartapp.bean.time.TriggerDos;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by zhouh on 2018/12/31.
 */
public class TimeUtils {

    public static String getTimeCronExpression(String sp, String om, String oh) {
        CommonUtils.showLogE("time", "======" + sp);
        return "0 " + om + " " + oh + " ? * " + sp;
    }

    public static String setDescription(String om, String oh, int s, String date) {
        StringBuffer sp = new StringBuffer();
        sp.append(oh + ":" + om + "-");
        if (s == 0) {
            sp.append("OFF-");
        } else if (s == 1) {
            sp.append("0N-");
        } else {
            sp.append("NULL-");
        }
        if(date.contains("1") && date.contains("2") && date.contains("3")
                && date.contains("4") && date.contains("5") && date.contains("6")
                && date.contains("7")) {
            sp.append("全周");
        } else {
            if (date.contains("2")) {
                sp.append("周一,");
            }
            if (date.contains("3")) {
                sp.append("周二,");
            }
            if (date.contains("4")) {
                sp.append("周三,");
            }
            if (date.contains("5")) {
                sp.append("周四,");
            }
            if (date.contains("6")) {
                sp.append("周五,");
            }
            if (date.contains("7")) {
                sp.append("周六,");
            }
            if (date.contains("1")) {
                sp.append("周七,");
            }
            sp.deleteCharAt(sp.length() - 1);
        }
        return sp.toString();
    }

    public static TriggerDos setTriggerDos(String name, String group, String cronExpression,
                                           String description, int value) {
        TriggerDos triggerDos = new TriggerDos();
        triggerDos.setName(name + System.currentTimeMillis());
        triggerDos.setGroup(group);
        triggerDos.setCronExpression(cronExpression);
        triggerDos.setDescription(description);
        triggerDos.setTriggerState("NORMAL");
        if (value == 0) {
            triggerDos.setValue(name + "-POWER-OFF");
        } else if (value == 1) {
            triggerDos.setValue(name + "-POWER-ON");
        }
        return triggerDos;
    }

    public static TimeBean setTimeJob(
            String name,
            String group,
            String description,
            String type,
            String marketId,
            List<TriggerDos> triggerDosList) {

        JobDo jobDo = new JobDo();
        jobDo.setDescription(description);
        jobDo.setGroup(group);
        jobDo.setName(name);
        jobDo.setTargetClass("com.george.iot.fish.schedule.job.schedule.Serial485Job");

        ExInfo exInfo = new ExInfo();
        exInfo.setMarketId(marketId);
        exInfo.setType(type);

        jobDo.setExtInfo(exInfo);

        TimeBean timeBean = new TimeBean();
        timeBean.setJobDO(jobDo);
        timeBean.setTriggerDOs(triggerDosList);
        return timeBean;
    }


    public static TimeBean getTimeInfos(String str) {
        TimeBean beens = new TimeBean();
        if (!TextUtils.isEmpty(str)) {
            try {

                JSONObject jsonObject = new JSONObject(str);
                JSONObject object = jsonObject.optJSONObject("jobDO");
                if (object == null) {
                    return beens;
                }
                ObjectMapper objectMapper = new ObjectMapper();
                JobDo jobDo = objectMapper.readValue(object.toString(),
                        JobDo.class);
                beens.setJobDO(jobDo);

                JSONArray array = jsonObject.getJSONArray("triggerDOs");
                if (array != null && (array.length() > 0)) {
                    JavaType javaTypeProfile = objectMapper.getTypeFactory().constructParametricType(List.class,
                            TriggerDos.class);
                    List<TriggerDos> triggerDoses = objectMapper.readValue(array.toString(), javaTypeProfile);
                    beens.setTriggerDOs(triggerDoses);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return beens;
    }


}
