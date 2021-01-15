package com.androidwind.github.ui.login;

import com.androidwind.github.bean.Data;
import com.androidwind.github.bean.GithubOAuthToken;
import com.androidwind.github.mvvm.BaseRepository;

import androidx.lifecycle.LiveData;

import retrofit2.Response;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class LoginRepository extends BaseRepository {

    private final String TAG = "LoginRepository";

    public LiveData<Data<GithubOAuthToken>> login(String code, String state) {
        super.doLogin(code, state);
        return liveDataLogin;
    }
}
