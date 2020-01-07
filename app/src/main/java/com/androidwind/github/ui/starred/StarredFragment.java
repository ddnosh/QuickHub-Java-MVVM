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
        FragmentActivity fragmentActivity = (FragmentActivity)getActivity();
        fragmentActivity.getToolbar().setTitle(getResources().getString(R.string.my_star));
        fragmentActivity.getToolbar().setTitleTextColor(Color.parseColor("#ffffff"));
    }

    @Override
    protected void loadData() {
        mViewModel.getStarred(page, Constant.PER_PAGE)
                .observe(this, observer);
    }
}
