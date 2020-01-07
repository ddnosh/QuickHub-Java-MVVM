package com.androidwind.github.ui.base;

import android.os.Bundle;

import com.androidwind.github.mvvm.BaseViewModel;

import java.lang.reflect.ParameterizedType;

import androidx.lifecycle.ViewModelProviders;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class MVVMFragment<T extends BaseViewModel> extends BaseFragment{

    protected T mViewModel;

    public T getViewModel() {
        return mViewModel;
    }

    public Class<T> getTClass() {
        try {
            return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        if (mViewModel == null) {
            Class<T> clazz = getTClass();
            if (clazz != null) {
                mViewModel = ViewModelProviders.of(this).get(getTClass());
            }
        }
    }
}
