package com.androidwind.github.mvvm;

import com.androidwind.base.module.BaseObserver;
import com.androidwind.base.module.exeception.ApiException;
import com.androidwind.base.util.RxUtil;
import com.androidwind.github.bean.Data;
import com.androidwind.github.bean.GithubBasicToken;
import com.androidwind.github.common.App;
import com.androidwind.github.module.retrofit.AuthRequestModel;
import com.androidwind.github.module.retrofit.RetrofitApi;
import com.androidwind.github.module.room.User;
import com.blankj.utilcode.util.LogUtils;

import androidx.lifecycle.MutableLiveData;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class BaseRepository {

    private final String TAG = "BaseRepository";

    protected MutableLiveData<Data<GithubBasicToken>> liveDataLogin;

    public void getToken(String token, AuthRequestModel authRequestModel) {
        liveDataLogin = new MutableLiveData<>();
        liveDataLogin.setValue(Data.loading());
        RetrofitApi.getLoginApi(token)
                .authorizations(authRequestModel)
                .compose(RxUtil.applySchedulers())
                .subscribe(new BaseObserver<GithubBasicToken>() {

                    @Override
                    public void onError(ApiException exception) {
                        LogUtils.eTag(TAG, exception.toString());//打印错误日志
                        liveDataLogin.setValue(Data.error(exception.message));
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
    }
}
