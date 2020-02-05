package com.androidwind.github.ui.login;

import android.util.Base64;

import com.androidwind.github.bean.Data;
import com.androidwind.github.bean.GithubBasicToken;
import com.androidwind.github.common.App;
import com.androidwind.github.module.retrofit.AuthRequestModel;
import com.androidwind.github.mvvm.BaseRepository;

import androidx.lifecycle.LiveData;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class LoginRepository extends BaseRepository {

    private final String TAG = "LoginRepository";

    public LiveData<Data<GithubBasicToken>> login(String name, String password) {
        String token = "Basic " + Base64.encodeToString((name + ":" + password).getBytes(), Base64.NO_WRAP);
        AuthRequestModel authRequestModel = AuthRequestModel.generate();
        App.sCurrentUserName = name;
        getToken(token, authRequestModel);
        return liveDataLogin;
    }
}
