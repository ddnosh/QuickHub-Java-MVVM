package com.androidwind.github.api;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public interface OtherApi {

    @NonNull
    @GET("zen")
    Observable<String> getZen();
}
