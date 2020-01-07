package com.androidwind.github.ui.login;

import android.util.Base64;

import com.androidwind.github.bean.Data;
import com.androidwind.github.bean.GithubAuth;
import com.androidwind.github.mvvm.BaseRepository;

import androidx.lifecycle.LiveData;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class LoginRepository extends BaseRepository {

    private final String TAG = "LoginRepository";

    public LiveData<Data<GithubAuth>> login(String name, String password) {
        String authorization =
                "Basic " + Base64.encodeToString((name + ":" + password).getBytes(), Base64.NO_WRAP);
        login(authorization);
        return liveDataLogin;
    }
}
