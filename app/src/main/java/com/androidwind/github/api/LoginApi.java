package com.androidwind.github.api;

import com.androidwind.github.bean.GithubBasicToken;
import com.androidwind.github.module.retrofit.AuthRequestModel;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public interface LoginApi {

    @POST("authorizations")
    @Headers("Accept: application/json")
    Observable<GithubBasicToken> authorizations(@NonNull @Body AuthRequestModel authRequestModel);
}
