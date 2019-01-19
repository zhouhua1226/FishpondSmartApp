package com.fishpond.smartapp.api;

import java.io.IOException;

/**
 * Created by YANYI on 2018/12/31.
 */
public interface TimeObserverCallBack {
    void onSuccessHttp(String responseInfo, int resultCode);

    void onFailureHttp(IOException e, int resultCode);
}
