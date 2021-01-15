package com.androidwind.github.api;

import com.androidwind.github.bean.GithubOAuthToken;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public interface LoginApi {

//    @GET("user")
//    Observable<GithubAuth> login(@Header("Authorization") String authorization);

    // @GET("users/{github_name}")
    // Observable<GithubUser> getGithubUser(@Path("github_name") String github_name);

    //retrofit访问普通url
    // @GET
    // Observable<GithubUser> getGithubUser(@Url String url);

    @POST("login/oauth/access_token")
    @Headers("Accept: application/json")
    Observable<GithubOAuthToken> login(
            @Query("code") String code,
            @Query("state") String state,
            @Query("client_id") String clientId,
            @Query("client_secret") String clientSecret
    );
}
