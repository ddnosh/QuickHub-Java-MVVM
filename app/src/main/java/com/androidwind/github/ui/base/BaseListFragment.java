package com.androidwind.github.ui.base;

import android.os.Bundle;

import com.androidwind.github.R;
import com.androidwind.github.mvvm.BaseViewModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class BaseListFragment<T extends BaseViewModel> extends MVVMFragment<T> {

    @BindView(R.id.rv_base_repo)
    protected RecyclerView mRecyclerView;
    @BindView(R.id.srl_base_repo)
    protected SmartRefreshLayout mSmartRefreshLayout;
    protected BaseQuickAdapter mAdapter;
    protected int page = 1;
    protected boolean isReload = true;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_base_repo;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = getRepoAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> clickRepoItem(position));
        mRecyclerView.setAdapter(mAdapter);
        mSmartRefreshLayout.setOnRefreshListener(refreshlayout -> {
            isReload = true;
            mAdapter.getData().clear();
            page = 1;
            loadData();
        });
        mSmartRefreshLayout.setOnLoadMoreListener(refreshlayout -> {
            isReload = false;
            ++page;
            loadData();
        });
        //init viewmodel
        initLiveData();
    }

    protected abstract BaseQuickAdapter getRepoAdapter();

    protected abstract void initLiveData();

    protected abstract void loadData();

    protected abstract void clickRepoItem(int position);
}
