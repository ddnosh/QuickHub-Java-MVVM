package com.androidwind.github.ui.starred;

import android.graphics.Color;
import android.os.Bundle;

import com.androidwind.github.R;
import com.androidwind.github.common.Constant;
import com.androidwind.github.ui.base.BaseRepoFragment;
import com.androidwind.github.ui.base.FragmentActivity;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class StarredFragment extends BaseRepoFragment<StarredViewModel> {

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        FragmentActivity fragmentActivity = (FragmentActivity) getActivity();
        fragmentActivity.getTheToolbar().setTitle(getResources().getString(R.string.my_star));
        fragmentActivity.getTheToolbar().setTitleTextColor(Color.parseColor("#ffffff"));
        fragmentActivity.getTheToolbar().setBackgroundColor(Color.parseColor("#708090"));
    }

    @Override
    protected void initLiveData() {
        getViewModel().getLiveDataGithubRepository().observe(this, observer);
    }

    @Override
    protected void loadData() {
        mViewModel.getStarred(page, Constant.PER_PAGE);
    }
}
