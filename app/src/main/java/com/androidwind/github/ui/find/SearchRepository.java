package com.androidwind.github.ui.find;

import com.androidwind.base.module.BaseObserver;
import com.androidwind.base.module.exeception.ApiException;
import com.androidwind.base.util.RxUtil;
import com.androidwind.github.bean.Data;
import com.androidwind.github.bean.GithubRepository;
import com.androidwind.github.bean.GithubSearch;
import com.androidwind.github.module.retrofit.RetrofitApi;
import com.androidwind.github.mvvm.BaseRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class SearchRepository extends BaseRepository {

    private MutableLiveData<Data<List<GithubRepository>>> liveDataGithubSearch =  new MutableLiveData<>();

    public MutableLiveData<Data<List<GithubRepository>>> getLiveDataGithubSearch() {
        return liveDataGithubSearch;
    }

    public LiveData<Data<List<GithubRepository>>> getSearchResult(String keyword, int page, int per_page) {
        liveDataGithubSearch.setValue(Data.loading());
        RetrofitApi.getRepoApi().query(keyword, "stars", "desc", page, per_page)
                .compose(RxUtil.applySchedulers())
                .subscribe(new BaseObserver<GithubSearch>() {
                    @Override
                    public void onError(ApiException exception) {
                        liveDataGithubSearch.setValue(Data.error(exception.message));
                    }

                    @Override
                    public void onSuccess(GithubSearch githubSearch) {
                        liveDataGithubSearch.setValue(Data.success(githubSearch.getRepositories()));
                    }
                });
        return liveDataGithubSearch;
    }
}
