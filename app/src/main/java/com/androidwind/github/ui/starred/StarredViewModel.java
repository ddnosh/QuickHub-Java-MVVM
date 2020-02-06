package com.androidwind.github.ui.starred;

import android.app.Application;

import com.androidwind.github.bean.Data;
import com.androidwind.github.bean.GithubRepository;
import com.androidwind.github.mvvm.BaseViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class StarredViewModel extends BaseViewModel<StarredRepository> {

    private MutableLiveData<Data<List<GithubRepository>>> liveDataGithubRepository;

    public MutableLiveData<Data<List<GithubRepository>>> getLiveDataGithubRepository() {
        return liveDataGithubRepository;
    }

    public StarredViewModel(@NonNull Application application) {
        super(application);
        liveDataGithubRepository = repository.getLiveDataGithubRepository();
    }

    public LiveData<Data<List<GithubRepository>>> getStarred(int page, int per_page) {
        return repository.getStarred(page, per_page);
    }
}
