package com.androidwind.github.ui.login;

import android.app.Application;

import com.androidwind.github.bean.Data;
import com.androidwind.github.bean.GithubBasicToken;
import com.androidwind.github.mvvm.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class LoginViewModel extends BaseViewModel<LoginRepository> {

    private LiveData<Data<GithubBasicToken>> mLiveDataLogin;

    public LiveData<Data<GithubBasicToken>> getLiveDataLogin() {
        return mLiveDataLogin;
    }

    public LoginViewModel(@NonNull Application application) {
        super(application);
        mLiveDataLogin = repository.getLiveDataLogin();
    }

    public LiveData<Data<GithubBasicToken>> login(String name, String password) {
        return repository.login(name, password);
    }
}
