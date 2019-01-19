package com.fishpond.smartapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fishpond.smartapp.R;
import com.fishpond.smartapp.api.UserApi;
import com.fishpond.smartapp.bean.AirLevelBean;
import com.fishpond.smartapp.bean.LocationBean;
import com.fishpond.smartapp.bean.WeatherBean;
import com.fishpond.smartapp.manager.UserManager;
import com.fishpond.smartapp.utils.Utils;
import com.fishpond.smartapp.view.BottomLayout;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class EnviomentActivity extends Activity {
    @BindView(R.id.bottom_layout)
    BottomLayout bottomLayout;

    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.weather)
    ImageView weather;
    @BindView(R.id.temp)
    TextView temp;
    @BindView(R.id.data2)
    TextView data2;
    @BindView(R.id.date1)
    TextView data1;

    @BindView(R.id.wind_d)
    TextView windD;
    @BindView(R.id.wind_s)
    TextView windS;
    @BindView(R.id.hum_s)
    TextView hum;
    @BindView(R.id.air)
    TextView air;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envioment);
        ButterKnife.bind(this);
        this.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        bottomLayout.setSelect(1);
        bottomLayout.setBottomClick(new BottomLayout.BottomClick() {
            @Override
            public void onBottomClick(int pos) {
                if (pos == 2) {
                    startActivity(new Intent(EnviomentActivity.this, SelectDeviceActivity.class));
                    finish();
                } else if (pos == 3) {
                    startActivity(new Intent(EnviomentActivity.this, SettingActivity.class));
                    finish();
                }
            }
        });
        initData();

    }

    private void initData() {
        if (!TextUtils.isEmpty(UserManager.getInstance().getCity())) {
            city.setText(UserManager.getInstance().getCity() + " " + UserManager.getInstance().getDistrict());
            initWeather();
            return;
        }
        final Location location = Utils.getLocation(getApplicationContext());
        if (location == null) {
            Toast.makeText(getApplicationContext(), "无法获取当前的位置信息!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        String l = location.getLatitude() + "," + location.getLongitude();
        UserApi.getInstance().getCityInfo(l, new Observer<LocationBean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull LocationBean locationBean) {
                if ((locationBean != null) && (!TextUtils.isEmpty(locationBean.getStatus()))) {
                    if (locationBean.getStatus().equals("OK")) {
                        LocationBean.AddressComponent addressComponent = locationBean.getResult().getAddressComponent();
                        if (addressComponent != null) {
                            UserManager.getInstance().setCity(addressComponent.getCity());
                            UserManager.getInstance().setDistrict(addressComponent.getDistrict());
                            city.setText(addressComponent.getCity() + " " + addressComponent.getDistrict());
                            initWeather();
                        }
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Toast.makeText(getApplicationContext(), "定位失败" + e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void initWeather() {
        UserApi.getInstance().getWeatherInfo(UserManager.getInstance().getCity()
                , new Observer<WeatherBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull WeatherBean weatherBean) {
                        if (weatherBean != null) {
                            if (weatherBean.getWeatherBeans() != null && weatherBean.getWeatherBeans().size() > 0) {
                                WeatherBean.WeatherInfo weatherInfo = weatherBean.getWeatherBeans().get(0);
                                if (weatherInfo.getStatus().equals("ok")) {
                                    WeatherBean.Now now = weatherInfo.getNow();
                                    if (now != null) {
                                        windD.setText(now.getWind_dir());
                                        windS.setText(now.getWind_sc() + "级");

                                        hum.setText(now.getHum() + "％");

                                        temp.setText(now.getTmp());

                                        initWeatheIv(now.getCondTxt());
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        UserApi.getInstance().getAirLevel(UserManager.getInstance().getCity(), new Observer<AirLevelBean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull AirLevelBean airLevelBean) {
                if ((airLevelBean != null) && (airLevelBean.getAirLevelBean() != null) && (airLevelBean.getAirLevelBean().size() > 0)) {
                    AirLevelBean.AirNowBean airNowBean = airLevelBean.getAirLevelBean().get(0);
                    if (TextUtils.isEmpty(airNowBean.getStatus())) {
                        return;
                    }
                    if (airNowBean.getStatus().equals("ok")) {
                        air.setText(airNowBean.getCity().getQlty());
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        initWeek();
    }

    private void initWeek() {
        data2.setText(Utils.StringWeek());
        data1.setText(Utils.StringData());
    }

    private void initWeatheIv(String condText) {
        if (condText.contains("晴")) {
            weather.setBackground(getResources().getDrawable(R.drawable.qing));
        } else if (condText.contains("云") || condText.contains("阴")) {
            weather.setBackground(getResources().getDrawable(R.drawable.yin));
        } else if (condText.contains("雨")) {
            weather.setBackground(getResources().getDrawable(R.drawable.yu));
        } else if (condText.contains("雪")) {
            weather.setBackground(getResources().getDrawable(R.drawable.xue));
        } else if (condText.contains("雾")
                || (condText.contains("霾")
             || condText.contains("尘")
             || condText.contains("沙"))) {
            weather.setBackground(getResources().getDrawable(R.drawable.wu));
        } else {
            weather.setBackground(getResources().getDrawable(R.drawable.qing));
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
