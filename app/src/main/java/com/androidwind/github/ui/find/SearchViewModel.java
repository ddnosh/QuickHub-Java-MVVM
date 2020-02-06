package com.androidwind.github.ui.find;

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
public class SearchViewModel extends BaseViewModel<SearchRepository> {

    private MutableLiveData<Data<List<GithubRepository>>> liveDataGithubSearch;

    public MutableLiveData<Data<List<GithubRepository>>> getLiveDataGithubSearch() {
        return liveDataGithubSearch;
    }

    public SearchViewModel(@NonNull Application application) {
        super(application);
        liveDataGithubSearch = repository.getLiveDataGithubSearch();
    }

    public LiveData<Data<List<GithubRepository>>> search(String keyword, int page, int per_page) {
        return repository.getSearchResult(keyword, page, per_page);
    }
}
