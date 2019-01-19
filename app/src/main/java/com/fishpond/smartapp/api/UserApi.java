package com.fishpond.smartapp.api;

import android.text.TextUtils;

import com.fishpond.smartapp.bean.AirLevelBean;
import com.fishpond.smartapp.bean.GatewayBean;
import com.fishpond.smartapp.bean.LocationBean;
import com.fishpond.smartapp.bean.Result;
import com.fishpond.smartapp.bean.UserBean;
import com.fishpond.smartapp.bean.UserConfigBean;
import com.fishpond.smartapp.bean.WeatherBean;
import com.fishpond.smartapp.manager.UserManager;
import com.fishpond.smartapp.module.https.RetrofitManager;
import com.fishpond.smartapp.utils.UrlUtils;
import com.fishpond.smartapp.utils.Utils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by zhouh on 2018/12/1.
 */
public class UserApi{
    private static UserApi userApi;
    private UserApiService userApiService;
    public static synchronized UserApi getInstance() {
        if (userApi == null) {
            userApi = new UserApi();
        }
        return userApi;
    }

    private UserApi() {
        userApiService = RetrofitManager.getInstance().setCreate(UserApiService.class);
    }

    public void logIn(String phone, String pwd, String dNo, ObserverApiCallBack<UserBean> callBack) {
        if (userApiService == null) {
            return;
        }
        userApiService.doLogin(phone, pwd, dNo)
                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    public void getUserConfig(String id, ObserverApiCallBack<List<UserConfigBean>> callBack) {
        if (userApiService == null)
            return;
        userApiService.getUserConfig(id)
                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    public void getGatewayInfo(String storeCode, ObserverApiCallBack<List<GatewayBean>> callBack) {
        if (userApiService == null)
            return;
        userApiService.getGatewayInfo(storeCode)
                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callBack);
    }

    public void getCityInfo(String location, Observer<LocationBean> observer) {
        if (userApiService == null)
            return;
        userApiService.getCityInfo(location, Utils.BAIDU_KEY)
                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getWeatherInfo(String location, Observer<WeatherBean> observer) {
        if (userApiService == null || (TextUtils.isEmpty(location)))
            return;
        userApiService.getWeather(location, Utils.WEATHER_KEY)
                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getAirLevel(String location, Observer<AirLevelBean> observer) {
        if (userApiService == null || (TextUtils.isEmpty(location)))
            return;
        userApiService.getAirLevel(location, Utils.WEATHER_KEY)
                .subscribeOn(Schedulers.newThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public interface UserApiService {
        @FormUrlEncoded
        @POST(UrlUtils.LOGIN_URL)
        Observable<Result<UserBean>> doLogin(
                @Field("mobile") String mobile,
                @Field("password") String pwd,
                @Field("deviceNo") String dNo);

        @FormUrlEncoded
        @POST(UrlUtils.USERCONFIG_URL)
        Observable<Result<List<UserConfigBean>>> getUserConfig(
                @Field("userId") String userId);

        @FormUrlEncoded
        @POST(UrlUtils.GET_GATEWAY_URL)
        Observable<Result<List<GatewayBean>>> getGatewayInfo(
                @Field("storeCode") String storeCode);


        @GET(UrlUtils.GET_LOCATION_CITY)
        Observable<LocationBean> getCityInfo(@Query("location") String location
                        ,@Query("ak") String key);

        @GET(UrlUtils.GET_WEATHER_INFO)
        Observable<WeatherBean> getWeather(@Query("location") String city
                        ,@Query("key") String key);

        @GET(UrlUtils.GET_AIRLEVEL_INFO)
        Observable<AirLevelBean> getAirLevel(@Query("location") String city
                ,@Query("key") String key);
    }

}
