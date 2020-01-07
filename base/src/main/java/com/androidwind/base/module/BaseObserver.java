package com.androidwind.base.module;

import com.androidwind.base.module.exeception.ApiException;
import com.androidwind.base.module.exeception.ExeceptionEngine;

import androidx.annotation.NonNull;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/** RxJava订阅者封装,包括Exception
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class BaseObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }
    @Override
    public void onNext(@NonNull T t) {
        onSuccess(t);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        onError(ExeceptionEngine.handleException(e));
    }

    @Override
    public void onComplete() {
    }
    public abstract void onError(ApiException exception) ;
    public abstract void onSuccess( T t) ;
}
