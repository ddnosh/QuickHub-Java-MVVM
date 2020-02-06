package com.androidwind.github.ui.trends;

import android.app.Application;

import com.androidwind.github.bean.Data;
import com.androidwind.github.bean.GithubEvent;
import com.androidwind.github.mvvm.BaseViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class TrendsViewModel extends BaseViewModel<TrendsRepository> {

    private MutableLiveData<Data<List<GithubEvent>>> liveDataGithubEvent;

    public MutableLiveData<Data<List<GithubEvent>>> getLiveDataGithubEvent() {
        return liveDataGithubEvent;
    }

    public TrendsViewModel(@NonNull Application application) {
        super(application);
        liveDataGithubEvent = repository.getLiveDataGithubEvent();
    }

    public LiveData<Data<List<GithubEvent>>> getTrends(String name, int page, int per_page) {
        return repository.getTrends(name, page, per_page);
    }
}
