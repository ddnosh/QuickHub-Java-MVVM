package com.androidwind.github.mvvm;

import com.androidwind.base.module.BaseObserver;
import com.androidwind.base.module.exeception.ApiException;
import com.androidwind.base.util.RxUtil;
import com.androidwind.github.BuildConfig;
import com.androidwind.github.bean.Data;
import com.androidwind.github.bean.GithubOAuthToken;
import com.androidwind.github.bean.GithubUser;
import com.androidwind.github.common.App;
import com.androidwind.github.module.retrofit.RetrofitApi;
import com.androidwind.github.module.room.User;
import com.androidwind.github.ui.login.LoginActivity;
import com.androidwind.github.ui.main.MainActivity;
import com.blankj.utilcode.util.LogUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class BaseRepository {

    private final String TAG = "BaseRepository";

    protected MutableLiveData<Data<GithubOAuthToken>> liveDataLogin;

    private MutableLiveData<Data<GithubUser>> liveDataUser;

    public void doLogin(String code, String state) {
        liveDataLogin = new MutableLiveData<>();
        liveDataLogin.setValue(Data.loading());
        RetrofitApi.getLoginApi()
                .login(code, state, BuildConfig.CLIENT_ID, BuildConfig.CLIENT_SECRET)
                .compose(RxUtil.applySchedulers())
                .subscribe(new BaseObserver<GithubOAuthToken>() {

                    @Override
                    public void onError(ApiException exception) {
                        LogUtils.eTag(TAG, exception.toString());//打印错误日志
                        liveDataLogin.setValue(Data.error(exception.message));
                    }

                    @Override
                    public void onSuccess(GithubOAuthToken githubOAuthToken) {
                        User user = new User();
                        user.setName("github");
                        user.setToken(githubOAuthToken.getAccessToken());
                        App.sLastLoginUser = user;
                        AppRepository.insertUser(user);
                        liveDataLogin.setValue(Data.success(githubOAuthToken));
                    }
                });
    }


    public LiveData<Data<GithubUser>> getGithubUser() {
        liveDataUser =  new MutableLiveData<>();
        liveDataUser.setValue(Data.loading());
        RetrofitApi.getUserApi()
                .getGithubUser()
                .compose(RxUtil.applySchedulers())
                .subscribe(new BaseObserver<GithubUser>() {
                    @Override
                    public void onError(ApiException exception) {
                        LogUtils.eTag(TAG, exception.toString());//打印错误日志
                        liveDataUser.setValue(Data.error(exception.message));
                    }

                    @Override
                    public void onSuccess(GithubUser githubUser) {
                        App.sGithubUser = githubUser;

                        App.sLastLoginUser.setName(githubUser.getName());
                        App.sLastLoginUser.setLoginTime(System.currentTimeMillis());
                        AppRepository.insertUser(App.sLastLoginUser);
                        liveDataUser.setValue(Data.success(githubUser));
                    }
                });
        return liveDataUser;
    }
}
