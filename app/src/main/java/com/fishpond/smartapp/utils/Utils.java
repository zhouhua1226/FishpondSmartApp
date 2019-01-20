package com.fishpond.smartapp.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by zhouh on 2018/12/15.
 */
public class Utils {
    public static boolean isExit = false;

    public static final String TAG_IN = "TAG_IN";
    public static final String TAG_SWITCH = "TAG_SWITCH";
    public static final String TAG_STATUS = "TAG_STATUS";
    public static final String TAG_SESSION_INVALID = "TAG_SESSION_INVALID";

    public static final String KEY_NAME = "KEY_NAME";
    public static final String KEY_PWD = "KEY_PWD";

    public static final String BAIDU_KEY = "knZV8pYqLjQfLf726FL2vlPWLZ5wSCCv";
    public static final String WEATHER_KEY = "613a5123d89a4f64b9eb3a62580f0d8b";

    public static Location getLocation(Context context) {
        String locationProvider;
        //获取地理位置管理器
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //获取所有可用的位置提供器
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            return null;
        }
        Location location = null;
        //获取Location
        location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            //不为空,显示地理位置经纬度
            return location;
        }
        //切换定位
        if (locationProvider.contains("gps")) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } else if (locationProvider.contains("network")){
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        return location;

    }

    public static String StringData(){
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return String.valueOf(c.get(Calendar.YEAR)) + "年"
                + String.valueOf(c.get(Calendar.MONTH) + 1) + "月"
                + String.valueOf(c.get(Calendar.DAY_OF_MONTH))+"日";}

    public static String StringWeek() {
        String mWay;
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if("1".equals(mWay)){
            mWay ="天";
        }else if("2".equals(mWay)){
            mWay ="一";
        }else if("3".equals(mWay)){
            mWay ="二";
        }else if("4".equals(mWay)){
            mWay ="三";
        }else if("5".equals(mWay)){
            mWay ="四";
        }else if("6".equals(mWay)){
            mWay ="五";
        }else if("7".equals(mWay)){
            mWay ="六";
        }else {
            mWay = "一";
        }
        return "星期"+mWay;
    }
}

