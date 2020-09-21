package com.androidwind.github.mvvm;

import com.androidwind.base.module.BaseObserver;
import com.androidwind.base.module.exeception.ApiException;
import com.androidwind.base.util.RxUtil;
import com.androidwind.github.bean.Data;
import com.androidwind.github.bean.GithubAuth;
import com.androidwind.github.common.App;
import com.androidwind.github.module.retrofit.RetrofitApi;
import com.androidwind.github.module.room.User;
import com.blankj.utilcode.util.LogUtils;

import androidx.lifecycle.MutableLiveData;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class BaseRepository {

    private final String TAG = "BaseRepository";

    protected MutableLiveData<Data<GithubAuth>> liveDataLogin;

    public void login(String authorization) {
        liveDataLogin = new MutableLiveData<>();
        liveDataLogin.setValue(Data.loading());
        RetrofitApi.getLoginApi()
                .login(authorization)
                .compose(RxUtil.applySchedulers())
                .subscribe(new BaseObserver<GithubAuth>() {

                    @Override
                    public void onError(ApiException exception) {
                        LogUtils.eTag(TAG, exception.toString());//打印错误日志
                        liveDataLogin.setValue(Data.error(exception.message));
                    }

                    @Override
                    public void onSuccess(GithubAuth githubAuth) {
                        App.sAuthorization = authorization;
                        App.sGithubAuth = githubAuth;//存缓存
                        User user = new User();
                        user.setName(githubAuth.getAuthorName());
                        user.setToken(authorization);
                        user.setLoginTime(System.currentTimeMillis());
                        App.sLastLoginUser = user;//存缓存
                        AppRepository.insertUser(user);
                        liveDataLogin.setValue(Data.success(githubAuth));
                    }
                });
    }
}
