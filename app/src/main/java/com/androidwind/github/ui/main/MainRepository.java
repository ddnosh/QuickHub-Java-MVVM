package com.androidwind.github.ui.main;

import com.androidwind.androidquick.module.exception.ApiException;
import com.androidwind.androidquick.module.rxjava.BaseObserver;
import com.androidwind.androidquick.util.RxUtil;
import com.androidwind.github.bean.Data;
import com.androidwind.github.bean.GithubUser;
import com.androidwind.github.module.retrofit.RetrofitApi;
import com.androidwind.github.mvvm.BaseRepository;
import com.blankj.utilcode.util.LogUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class MainRepository extends BaseRepository {

    private final String TAG = "MainRepository";

    private MutableLiveData<Data<GithubUser>> liveDataUser = new MutableLiveData<>();

    public MutableLiveData<Data<GithubUser>> getLiveDataUser() {
        return liveDataUser;
    }

    public LiveData<Data<GithubUser>> getGithubUser(String token) {
        liveDataUser.setValue(Data.loading());
        RetrofitApi.getUserApi(token)
                .getUserInfo()
                .compose(RxUtil.io2Main())
                .subscribe(new BaseObserver<GithubUser>() {
                    @Override
                    public void onError(ApiException exception) {
                        LogUtils.eTag(TAG, exception.toString());//打印错误日志
                        liveDataUser.setValue(Data.error(exception.getMsg()));
                    }

                    @Override
                    public void onSuccess(GithubUser githubUser) {
                        liveDataUser.setValue(Data.success(githubUser));
                    }
                });
        return liveDataUser;
    }
}
