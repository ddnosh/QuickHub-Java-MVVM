package com.androidwind.github.ui.login;

import android.util.Base64;

import com.androidwind.androidquick.module.exception.ApiException;
import com.androidwind.androidquick.module.rxjava.BaseObserver;
import com.androidwind.androidquick.util.RxUtil;
import com.androidwind.github.bean.Data;
import com.androidwind.github.bean.GithubBasicToken;
import com.androidwind.github.common.App;
import com.androidwind.github.module.retrofit.AuthRequestModel;
import com.androidwind.github.module.retrofit.RetrofitApi;
import com.androidwind.github.module.room.User;
import com.androidwind.github.mvvm.AppRepository;
import com.androidwind.github.mvvm.BaseRepository;
import com.blankj.utilcode.util.LogUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class LoginRepository extends BaseRepository {

    private final String TAG = "LoginRepository";

    private  MutableLiveData<Data<GithubBasicToken>> liveDataLogin =  new MutableLiveData<>();

    public MutableLiveData<Data<GithubBasicToken>> getLiveDataLogin() {
        return liveDataLogin;
    }

    public LiveData<Data<GithubBasicToken>> login(String name, String password) {
        String token = "Basic " + Base64.encodeToString((name + ":" + password).getBytes(), Base64.NO_WRAP);
        AuthRequestModel authRequestModel = AuthRequestModel.generate();
        App.sCurrentUserName = name;

        liveDataLogin.setValue(Data.loading());
        RetrofitApi.getLoginApi(token)
                .authorizations(authRequestModel)
                .compose(RxUtil.io2Main())
                .subscribe(new BaseObserver<GithubBasicToken>() {

                    @Override
                    public void onError(ApiException exception) {
                        LogUtils.eTag(TAG, exception.toString());//打印错误日志
                        liveDataLogin.setValue(Data.error(exception.getMsg()));
                    }

                    @Override
                    public void onSuccess(GithubBasicToken githubBasicToken) {
                        User user = new User();
                        user.setName(App.sCurrentUserName);
                        user.setToken(githubBasicToken.getToken());
                        user.setLoginTime(System.currentTimeMillis());
                        App.sLastLoginUser = user;//存缓存
                        AppRepository.insertUser(user);//存数据库
                        liveDataLogin.setValue(Data.success(githubBasicToken));
                    }
                });

        return liveDataLogin;
    }
}
