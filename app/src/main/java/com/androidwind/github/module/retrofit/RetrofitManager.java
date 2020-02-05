package com.androidwind.github.module.retrofit;

import com.androidwind.base.util.FileUtil;
import com.androidwind.base.util.StringUtil;
import com.androidwind.github.MyApplication;
import com.androidwind.github.common.Constant;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public enum RetrofitManager {
    INSTANCE;

    private final String TAG = "RetrofitManager";

    private HashMap<String, Retrofit> retrofitMap = new HashMap<>();
    private String token;

    private void createRetrofit(@NonNull String baseUrl, boolean isJson) {
        int timeOut = Constant.HTTP_TIME_OUT;
        Cache cache = new Cache(FileUtil.getHttpImageCacheDir(MyApplication.getInstance()),
                Constant.HTTP_MAX_CACHE_SIZE);

        // Log信息拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//这里可以选择拦截级别

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.MILLISECONDS)
                .addInterceptor(loggingInterceptor)
                .cache(cache);
        if (!StringUtil.isEmpty(token)) {
            String auth = token.startsWith("Basic") ? token : "token " + token;
            okHttpClientBuilder.interceptors().add(0, chain -> {
                Request.Builder reqBuilder = chain.request()
                        .newBuilder().addHeader("Authorization", auth);
                return chain.proceed(reqBuilder.build());
            });
        }

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())//定义转化器,用Gson将服务器返回的Json格式解析成实体
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//关联RxJava
                .client(okHttpClientBuilder.build());

        if (isJson) {
            builder.addConverterFactory(GsonConverterFactory.create());
        } else {
            builder.addConverterFactory(SimpleXmlConverterFactory.createNonStrict());
        }

        retrofitMap.put(baseUrl + "-" + isJson, builder.build());
    }

    /**
     * 区分登录态的Retrofit
     *
     * @param baseUrl
     * @param token
     * @param isJson
     * @return
     */
    public Retrofit getRetrofit(@NonNull String baseUrl, String token, boolean isJson) {
        this.token = token;
        String key = baseUrl + "-" + isJson;
        if (!retrofitMap.containsKey(key)) {
            createRetrofit(baseUrl, isJson);
        }
        return retrofitMap.get(key);
    }

    public Retrofit getRetrofit(@NonNull String baseUrl, String token) {
        return getRetrofit(baseUrl, token, true);
    }

    public Retrofit getRetrofit(@NonNull String baseUrl) {
        return getRetrofit(baseUrl, null);
    }

    public void reset() {
        retrofitMap.clear();
    }
}
