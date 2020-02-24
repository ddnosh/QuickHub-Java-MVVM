package com.androidwind.github.ui.history;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.androidwind.androidquick.ui.dialog.dialogactivity.ADialog;
import com.androidwind.androidquick.ui.dialog.dialogactivity.BaseDialog;
import com.androidwind.github.R;
import com.androidwind.github.common.Constant;
import com.androidwind.github.module.room.History;
import com.androidwind.github.mvvm.AppRepository;
import com.androidwind.github.ui.base.BaseListFragment;
import com.androidwind.github.ui.base.FragmentActivity;
import com.androidwind.github.ui.base.WebViewActivity;
import com.androidwind.github.ui.history.adapter.HistoryAdapter;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;

import java.util.List;

import androidx.lifecycle.LiveData;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class HistoryFragment extends BaseListFragment<HistoryViewModel> {

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        FragmentActivity fragmentActivity = (FragmentActivity) getActivity();
        fragmentActivity.getTheToolbar().setTitle(getResources().getString(R.string.action_history));
        fragmentActivity.getTheToolbar().setTitleTextColor(Color.parseColor("#ffffff"));
        fragmentActivity.getTheToolbar().setBackgroundColor(Color.parseColor("#708090"));

        mAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                new ADialog(mContext)
                        .setConvertListener((BaseDialog.ViewConvertListener) (holder, dialog) -> {
                            holder.setText(R.id.dialog_title, getResources().getString(R.string.app_name));
                            holder.setText(R.id.dialog_info, getResources().getString(R.string.delete_hint));
                            holder.setText(R.id.dialog_confirm, getResources().getString(R.string.confirm));
                            holder.setText(R.id.dialog_cancel, getResources().getString(R.string.cancel));
                            holder.setOnClickListener(R.id.dialog_confirm, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    History history = (History) mAdapter.getData().get(position);
                                    AppRepository.deleteHistory(history);
                                    ToastUtils.showShort(getResources().getString(R.string.delete_success));
                                    dialog.dismiss();
                                }
                            });
                        }).show();
                return false;
            }
        });
        loadData();
    }

    @Override
    protected BaseQuickAdapter getRepoAdapter() {
        return new HistoryAdapter(R.layout.item_history);
    }

    @Override
    protected LiveData initLiveData() {
        return getViewModel().getLiveDataHistory();
    }

    @Override
    protected void loadData() {
        mViewModel.getHistory(Constant.PER_PAGE, Constant.PER_PAGE * (page - 1)).observe(this, histories -> {
            updateData(histories);
            if (isReload) {
                mSmartRefreshLayout.finishRefresh();
            } else {
                mSmartRefreshLayout.finishLoadMore();
            }
        });
    }

    private void updateData(List<History> list) {
        dismissLoadingDialog();
        if (isReload) {
            mAdapter.setNewData(list);
        } else {
            mAdapter.addData(list);
        }
        if (list != null && list.size() > 0) {
            if (!SPUtils.getInstance().getBoolean("HAS_SHOW_HINT")) {
                new ADialog(mContext)
                    .setDialogLayout(R.layout.dialog_alert)
                    .setConvertListener((BaseDialog.ViewConvertListener) (holder, dialog) -> {
                        holder.setText(R.id.dialog_title, getResources().getString(R.string.app_name));
                        holder.setText(R.id.dialog_info, getResources().getString(R.string.long_press_to_delete));
                        holder.setText(R.id.dialog_confirm, getResources().getString(R.string.confirm));
                        holder.setOnClickListener(R.id.dialog_confirm, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                    }).show();
                SPUtils.getInstance().put("HAS_SHOW_HINT", true);
            }
        }
    }

    @Override
    protected void clickRepoItem(int position) {
        History history = (History) mAdapter.getData().get(position);
        Bundle bundle = new Bundle();
        bundle.putString(Constant.QUICKHUB_WEB_URL, history.getRepoUrl());
        readyGo(WebViewActivity.class, bundle);
    }
}
