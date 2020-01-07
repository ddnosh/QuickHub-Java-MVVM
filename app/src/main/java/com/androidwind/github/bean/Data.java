package com.androidwind.github.bean;


import com.androidwind.github.common.Status;

import androidx.annotation.Nullable;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class Data<T> {

    @Status
    public int status;

    @Nullable
    public T data;

    @Nullable
    public String msg;

    public Data(@Status int status, @Nullable T data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    public static <T> Data<T> loading() {
        return new Data<>(Status.LOADING, null, null);
    }

    public static <T> Data<T> success(@Nullable T data) {
        return new Data<>(Status.SUCCESS, data, null);
    }

    public static <T> Data<T> error(String msg) {
        return new Data<>(Status.ERROR, null, msg);
    }

    public boolean showLoading() {
        return status == Status.LOADING;
    }

    public boolean showSuccess() {
        return status == Status.SUCCESS;
    }

    public boolean showError() {
        return status == Status.ERROR;
    }

    // public void pickup(OnCallBack<T> callback) {
    //     switch (status) {
    //         case Status.LOADING:
    //             callback.onLoading();
    //             break;
    //         case Status.SUCCESS:
    //             callback.onSuccess(data);
    //             break;
    //         case Status.ERROR:
    //             callback.onError(msg);
    //             break;
    //     }
    // }
    //
    // public interface OnCallBack<T> {
    //     void onLoading();
    //
    //     void onSuccess(T data);
    //
    //     void onError(String msg);
    // }
}
