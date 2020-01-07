package com.androidwind.github.ui.base.adapter;

import com.androidwind.github.R;
import com.androidwind.github.bean.GithubRepository;
import com.androidwind.github.module.QuickModule;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class RepoAdapter extends BaseQuickAdapter<GithubRepository, BaseViewHolder> {
    public RepoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder holder, GithubRepository githubRepository) {
        holder.setText(R.id.tv_project, githubRepository.getFullName());
        holder.setText(R.id.tv_desc, githubRepository.getDescription());
        QuickModule.imageProcessor().loadNet(githubRepository.getOwner().getAvatar_url(), holder.getView(R.id.iv_avatar));
        holder.setText(R.id.tv_name, githubRepository.getOwner().getLogin());
        holder.setText(R.id.tv_stars, githubRepository.getStargazersCount() + " Stars");
    }
}
