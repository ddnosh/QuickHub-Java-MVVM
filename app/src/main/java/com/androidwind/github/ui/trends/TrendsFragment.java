package com.androidwind.github.ui.trends;

import android.os.Bundle;

import com.androidwind.androidquick.module.asynchronize.eventbus.EventCenter;
import com.androidwind.github.R;
import com.androidwind.github.bean.GithubEvent;
import com.androidwind.github.common.App;
import com.androidwind.github.common.Constant;
import com.androidwind.github.module.room.History;
import com.androidwind.github.mvvm.AppRepository;
import com.androidwind.github.ui.base.BaseListFragment;
import com.androidwind.github.ui.base.WebViewActivity;
import com.androidwind.github.ui.trends.adapter.TrendsAdapter;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class TrendsFragment extends BaseListFragment<TrendsViewModel> {

    @Override
    protected boolean isBindEventBus() {
        return true;
    }

    @Override
    protected void initLiveData() {
        getViewModel().getLiveDataGithubEvent().observe(this, result -> {
            if (result.showLoading()) {
                showLoadingDialog();
            } else {
                if (isReload) {
                    mSmartRefreshLayout.finishRefresh();
                } else {
                    mSmartRefreshLayout.finishLoadMore();
                }
                //
                if (result.showSuccess()) {
                    updateData(result.data);

                }
                if (result.showError()) {
                    dismissLoadingDialog();
                    ToastUtils.showShort(result.msg);
                }
            }
        });
    }

    @Override
    protected void loadData() {
        getViewModel().getTrends(App.sLastLoginUser.getName(), page, Constant.PER_PAGE);
    }

    private void updateData(List<GithubEvent> list) {
        dismissLoadingDialog();
        if (isReload) {
            mAdapter.setNewData(list);
        } else {
            mAdapter.addData(list);
        }
    }

    @Override
    protected void clickRepoItem(int position) {
        GithubEvent githubEvent = (GithubEvent) mAdapter.getData().get(position);
        String url = "https://github.com/" + githubEvent.getRepo().getName();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.QUICKHUB_WEB_URL, url);
        readyGo(WebViewActivity.class, bundle);
        //添加访问记录
        History history = new History();
        history.setRepoId(githubEvent.getRepo().getId());
        history.setRepoName(githubEvent.getRepo().getName().split("/")[1]);
        history.setRepoAuth(githubEvent.getRepo().getName().split("/")[0]);
        history.setRepoUrl(url);
        history.setAddTime(System.currentTimeMillis());
        AppRepository.insertHistory(history);
    }

    @Override
    public BaseQuickAdapter getRepoAdapter() {
        return new TrendsAdapter(R.layout.item_trends);
    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {
        if (eventCenter != null && eventCenter.getData() != null) {
            if (eventCenter.getEventCode() == Constant.EVENTBUS_EVENTCODE) {
                loadData();
            }
        }
    }
}
