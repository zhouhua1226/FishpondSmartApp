package com.fishpond.smartapp.utils;

/**
 * Created by zhouh on 2017/2/22.
 */
public class UrlUtils {
    public static final int TAG_GET_LOGIN = 0xa0;
    public static final int TAG_GET_CONFIG = 0xa1;
    public static final int TAG_GET_DEVICE = 0xa2;

    //生产环境 user/login
    public static final String ROOT_URL = "http://60.205.184.214:9088/";
    public static final String LOGIN_URL = "rest/user/login";
    public static final String USERCONFIG_URL = "rest/user/queryStoreByUser";
    public static final String GET_GATEWAY_URL = "rest/fish/queryfish";

    //Time
    public static final String TIME_ROOT_URL = "http://60.205.184.214:8206/";
    public static final String TIME_GET_TASK = "jobs/queryByJobKey";
    public static final String TIME_ADD_ORDER = "jobs/addJob";
    public static final String TIME_ADD_TRIGGER = "jobs/addTrigger";
    public static final String TIME_DELETE_TIME = "jobs/deleteTrigger";

    public static final String GET_LOCATION_CITY = "http://api.map.baidu.com/geocoder?output=json";
    public static final String GET_WEATHER_INFO = "https://free-api.heweather.net/s6/weather/now?";
    public static final String GET_AIRLEVEL_INFO = "https://free-api.heweather.net/s6/air/now?";
}
