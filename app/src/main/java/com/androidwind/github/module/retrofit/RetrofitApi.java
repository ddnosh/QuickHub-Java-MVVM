package com.androidwind.github.module.retrofit;

import com.androidwind.github.api.LoginApi;
import com.androidwind.github.api.RepoApi;
import com.androidwind.github.api.UserApi;
import com.androidwind.github.common.Constant;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class RetrofitApi {

    private static <T> T getApis(Class<T> serviceClass, String baseUrl){
        return RetrofitManager.INSTANCE.getRetrofit(baseUrl).create(serviceClass);
    }

    private static <T> T getApis(Class<T> serviceClass, String baseUrl, boolean withLogin){
        return RetrofitManager.INSTANCE.getRetrofit(baseUrl, withLogin).create(serviceClass);
    }

    public static LoginApi getLoginApi() {
        return getApis(LoginApi.class, Constant.GITHUB_API_URL, false);
    }

    public static UserApi getUserApi() {
        return getApis(UserApi.class, Constant.GITHUB_API_URL);
    }

    public static RepoApi getRepoApi() {
        return getApis(RepoApi.class, Constant.GITHUB_API_URL, false);
    }
}
