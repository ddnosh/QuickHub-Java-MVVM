package com.androidwind.github.ui.login;

import android.app.Application;

import com.androidwind.github.bean.Data;
import com.androidwind.github.bean.GithubAuth;
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

    public LiveData<Data<GithubAuth>> login(String name, String password) {
        return repository.login(name, password);
    }
}
