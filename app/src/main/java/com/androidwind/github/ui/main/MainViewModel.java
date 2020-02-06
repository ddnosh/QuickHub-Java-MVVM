package com.androidwind.github.ui.main;

import android.app.Application;

import com.androidwind.github.bean.Data;
import com.androidwind.github.bean.GithubUser;
import com.androidwind.github.mvvm.BaseViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class MainViewModel extends BaseViewModel<MainRepository> {

    private MutableLiveData<Data<GithubUser>> liveDataUser;

    public MutableLiveData<Data<GithubUser>> getLiveDataUser() {
        return liveDataUser;
    }

    public MainViewModel(@NonNull Application application) {
        super(application);
        liveDataUser = repository.getLiveDataUser();
    }

    public LiveData<Data<GithubUser>> getGithubUser(String token) {
        return repository.getGithubUser(token);
    }
}
