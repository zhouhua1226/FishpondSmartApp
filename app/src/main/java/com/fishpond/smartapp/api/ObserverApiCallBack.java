package com.fishpond.smartapp.api;

import android.util.Log;

import com.fishpond.smartapp.bean.Result;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhouh on 2018/12/1.
 */
public abstract class ObserverApiCallBack<T> implements Observer<Result<T>> {

    private int Tag;

    public void setTag(int tag) {
        Tag = tag;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onNext(@NonNull Result<T> tResult) {
        if (tResult.getCode() == 200) {
            onSuccessHttp(tResult.getObj(), Tag);
        } else {
            onFailureHttp(tResult.getDes(), Tag);
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        onFailureHttp(e.getMessage(), Tag);
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccessHttp(T t, int tag);
    public abstract void onFailureHttp(String e, int tag);
}
