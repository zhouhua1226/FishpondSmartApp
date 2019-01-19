package com.fishpond.smartapp.module.https;

import com.fishpond.smartapp.BuildConfig;
import com.fishpond.smartapp.utils.UrlUtils;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhouhua on 2018/12/1.
 */
public class RetrofitManager {
    private Retrofit mRetrofit;
    private static RetrofitManager mRetrofitManager;

    public static synchronized RetrofitManager getInstance() {
        if (mRetrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (mRetrofitManager == null) {
                    mRetrofitManager = new RetrofitManager();
                }
            }
        }
        return mRetrofitManager;
    }

    private RetrofitManager() {
        initRetrofit();
    }

    private void initRetrofit() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(UrlUtils.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpManager.getInstance().getOkHttpClient())
                .build();
    }

    public <T> T setCreate(Class<T> reqServer) {
        return mRetrofit.create(reqServer);
    }
}
