package com.androidwind.github.ui.login;

import android.app.Application;

import com.androidwind.github.bean.Data;
import com.androidwind.github.bean.GithubOAuthToken;
import com.androidwind.github.bean.GithubUser;
import com.androidwind.github.mvvm.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class LoginViewModel extends BaseViewModel<LoginRepository> {
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    // for browser
    public LiveData<Data<GithubOAuthToken>> login(String code, String state) {
        return repository.login(code, state);
    }

    // user info
    public LiveData<Data<GithubUser>> getUserInfo() {
        return repository.getGithubUser();
    }
}
