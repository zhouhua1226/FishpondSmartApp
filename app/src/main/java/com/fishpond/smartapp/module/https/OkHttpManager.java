package com.fishpond.smartapp.module.https;


import com.fishpond.smartapp.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by zhouh on 2017/1/17.
 */
public class OkHttpManager {
    private static final String TAG = "OkHttpManager";
    private static OkHttpManager okHttpManager;
    private OkHttpClient okHttpClient;

    public synchronized static OkHttpManager getInstance() {
        if (null == okHttpManager) {
            okHttpManager = new OkHttpManager();
        }
        return okHttpManager;
    }


    private OkHttpManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//日志级别
            builder.addInterceptor(loggingInterceptor);
        }
        okHttpClient = builder
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
