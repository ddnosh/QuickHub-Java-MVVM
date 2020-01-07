package com.androidwind.github.ui.trends.adapter;

import com.androidwind.github.MyApplication;
import com.androidwind.github.R;
import com.androidwind.github.bean.GithubEvent;
import com.androidwind.github.module.QuickModule;
import com.androidwind.github.util.DateUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class TrendsAdapter extends BaseQuickAdapter<GithubEvent, BaseViewHolder> {
    public TrendsAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder holder, GithubEvent githubEvent) {
        holder.setText(R.id.tv_name, githubEvent.getActor().getLogin());
        QuickModule.imageProcessor().loadNet(githubEvent.getActor().getAvatarUrl(), holder.getView(R.id.iv_user));
        String timeTip = DateUtil.convertTimeToFormat(MyApplication.getInstance().getApplicationContext(), githubEvent.getCreatedAt().getTime());
        holder.setText(R.id.tv_date, getContext().getResources().getString(R.string.just_now).equals(timeTip) ? timeTip : timeTip + getContext().getResources().getString(R.string.before));
        holder.setText(R.id.tv_type, githubEvent.getType().name());
        holder.setText(R.id.tv_repo, githubEvent.getRepo().getName());
    }
}
