package com.fishpond.smartapp.api;

import android.util.Log;

import com.fishpond.smartapp.module.https.OkHttpManager;
import com.fishpond.smartapp.utils.UrlUtils;
import com.gatz.netty.global.AppGlobal;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zhouh on 2018/12/31.
 */
public class TimeApi {
    private static final String TAG = "TimeApi-";
    private static TimeApi timeApi;

    public static synchronized TimeApi getInstance() {
        if (timeApi == null) {
            timeApi = new TimeApi();
        }
        return timeApi;
    }

    public void getTimeInfos(TimeObserverCallBack callBack,
                             String sessionId,
                             String jobGroup,
                             String jobName,
                             int resultCode) {
        String url = UrlUtils.TIME_ROOT_URL + UrlUtils.TIME_GET_TASK +
                "?sessionId=" + sessionId
                  + "&jobGroup=" + jobGroup
                  + "&jobName=" + jobName;
        getRequest(url, callBack, resultCode);
    }

    public void addOrder(String json, TimeObserverCallBack callBack, int resultCode) {
        String url = UrlUtils.TIME_ROOT_URL + UrlUtils.TIME_ADD_ORDER +
                "?sessionId=" + AppGlobal.getInstance().getUserInfo().getSessionId();
        POSTJSON(url, json, callBack, resultCode);
    }

    public void addTriggerDo(String group, String name, String json, TimeObserverCallBack callBack, int resultCode) {
        String url = UrlUtils.TIME_ROOT_URL + UrlUtils.TIME_ADD_TRIGGER +
                "?jobGroup=" + group + "&jobName=" + name + "&sessionId=" + AppGlobal.getInstance().getUserInfo().getSessionId();
        POSTJSON(url, json, callBack, resultCode);
    }

    public void deleteTime(String group, String name, String json, TimeObserverCallBack callBack, int resultCode) {
        String url = UrlUtils.TIME_ROOT_URL + UrlUtils.TIME_DELETE_TIME +
                "?triggerGroup=" + group + "&triggerName=" + name + "&sessionId=" + AppGlobal.getInstance().getUserInfo().getSessionId();
        DELETE(url, json, callBack, resultCode);
    }

    public void DELETE(String url, String json, TimeObserverCallBack callBack, int resultCode) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .delete(body)
                .addHeader("Accept", "*/*")
                .build();
        handleResponse(request, callBack, resultCode);
    }

    private void getRequest(String url, TimeObserverCallBack callBack, int resultCode) {
        Request request = new Request.Builder().get().url(url).build();
        Log.e(TAG, url);
        handleResponse(request, callBack, resultCode);
    }

    private void postRequest(String url, HashMap<String, String> params, TimeObserverCallBack callBack, int resultCode) {
        //FormBody.Builder formEncodingBuilder = new FormBody.Builder();
        StringBuffer str = new StringBuffer();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            str.append(key+"="+value);
            str.append("&");
            //formEncodingBuilder.add(key, value);
        }
        str.deleteCharAt(str.length() - 1);
       // RequestBody requestBody = formEncodingBuilder.build();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                str.toString());
        Request request = new Request.Builder()
                .post(requestBody)
                .url(url).build();
        handleResponse(request, callBack, resultCode);
    }

    public void POSTJSON(String url, String json, TimeObserverCallBack callBack, int resultCode) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        handleResponse(request, callBack, resultCode);
    }


    private void handleResponse(Request request, final TimeObserverCallBack callBack, final int resultCode) {
        OkHttpManager.getInstance().getOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (null != callBack) {
                    callBack.onFailureHttp(e, resultCode);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (null != callBack && (response != null)) {
                    callBack.onSuccessHttp(response.body().string(), resultCode);
                }
            }
        });
    }
}
