package com.androidwind.github.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
@IntDef({Status.LOADING,Status.SUCCESS,Status.ERROR})
@Retention(RetentionPolicy.SOURCE)
public @interface Status {
    int LOADING = 0;
    int SUCCESS = 1;
    int ERROR = 2;
}
