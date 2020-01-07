package com.androidwind.github.ui.history.adapter;

import com.androidwind.github.MyApplication;
import com.androidwind.github.R;
import com.androidwind.github.module.room.History;
import com.androidwind.github.util.DateUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class HistoryAdapter extends BaseQuickAdapter<History, BaseViewHolder> {
    public HistoryAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder holder, History history) {
        holder.setText(R.id.tv_repo, history.getRepoName());
        holder.setText(R.id.tv_auth, history.getRepoAuth());
        String timeTip = DateUtil.convertTimeToFormat(MyApplication.getInstance().getApplicationContext(), history.getAddTime());
        holder.setText(R.id.tv_date,
                getContext().getResources().getString(R.string.just_now).equals(timeTip)?timeTip + getContext().getResources().getString(R.string.visit):timeTip
                        + getContext().getResources().getString(R.string.visit_before));
    }
}
