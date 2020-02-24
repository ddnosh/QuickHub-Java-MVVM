package com.androidwind.github.ui.base;

import android.os.Bundle;

import com.androidwind.github.R;
import com.androidwind.github.bean.Data;
import com.androidwind.github.bean.GithubRepository;
import com.androidwind.github.common.Constant;
import com.androidwind.github.module.room.History;
import com.androidwind.github.mvvm.AppRepository;
import com.androidwind.github.mvvm.BaseViewModel;
import com.androidwind.github.ui.base.adapter.RepoAdapter;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import androidx.lifecycle.Observer;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class BaseRepoFragment<T extends BaseViewModel> extends BaseListFragment<T> {
    @Override
    protected BaseQuickAdapter getRepoAdapter() {
        return new RepoAdapter(R.layout.item_repo);
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        loadData();
    }

    protected Observer observer = (Observer<Data<List<GithubRepository>>>) result -> {
        if (result.showLoading()) {
            BaseRepoFragment.this.showLoadingDialog();
        } else {
            if (isReload) {
                mSmartRefreshLayout.finishRefresh();
            } else {
                mSmartRefreshLayout.finishLoadMore();
            }
            //
            if (result.showSuccess()) {
                BaseRepoFragment.this.updateData(result.data);
            }
            if (result.showError()) {
                BaseRepoFragment.this.dismissLoadingDialog();
                ToastUtils.showShort(result.msg);
            }
        }
    };

    private void updateData(List<GithubRepository> list) {
        dismissLoadingDialog();
        if (isReload) {
            mAdapter.setNewData(list);
        } else {
            mAdapter.addData(list);
        }
    }

    @Override
    protected void clickRepoItem(int position) {
        GithubRepository githubRepository = (GithubRepository)mAdapter.getData().get(position);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.QUICKHUB_WEB_URL, githubRepository.getHtmlUrl());
        readyGo(WebViewActivity.class, bundle);
        //添加访问记录
        History history = new History();
        history.setRepoId(githubRepository.getId());
        history.setRepoName(githubRepository.getName());
        history.setRepoAuth(githubRepository.getFullName().split("/")[0]);
        history.setRepoUrl(githubRepository.getHtmlUrl());
        history.setAddTime(System.currentTimeMillis());
        AppRepository.insertHistory(history);
    }
}
