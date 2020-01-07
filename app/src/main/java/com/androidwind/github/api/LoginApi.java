package com.androidwind.github.api;

import com.androidwind.github.bean.GithubAuth;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public interface LoginApi {

    @GET("user")
    Observable<GithubAuth> login(@Header("Authorization") String authorization);

    // @GET("users/{github_name}")
    // Observable<GithubUser> getGithubUser(@Path("github_name") String github_name);

    //retrofit访问普通url
    // @GET
    // Observable<GithubUser> getGithubUser(@Url String url);
}
