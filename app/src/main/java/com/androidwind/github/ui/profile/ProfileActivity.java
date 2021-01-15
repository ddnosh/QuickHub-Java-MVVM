package com.androidwind.github.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidwind.github.R;
import com.androidwind.github.common.App;
import com.androidwind.github.mvvm.AppRepository;
import com.androidwind.github.module.QuickModule;
import com.androidwind.github.ui.base.BaseActivity;
import com.androidwind.github.ui.splash.SplashActivity;

import androidx.constraintlayout.widget.Group;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class ProfileActivity extends BaseActivity {
    @BindView(R.id.iv_avatar)
    ImageView mAvatar;
    @BindView(R.id.tv_name)
    TextView mName;
    @BindView(R.id.tv_location)
    TextView mLocation;
    @BindView(R.id.tv_email)
    TextView mEmail;
    @BindView(R.id.btn_logout)
    Button mLogout;
    @BindView(R.id.group)
    Group mGroup;
    @BindView(R.id.layout_main_loading_failure)
    View mErrorView;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_profile;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        if (App.sGithubUser != null) {
            mGroup.setVisibility(View.VISIBLE);
            mErrorView.setVisibility(View.GONE);
            QuickModule.imageProcessor().loadNet(App.sGithubUser.getAvatar_url(), mAvatar);
            mName.setText(App.sGithubUser.getName());
            mLocation.setText(App.sGithubUser.getLocation());
            mEmail.setText(App.sGithubUser.getEmail());
        } else {
            mGroup.setVisibility(View.GONE);
            mErrorView.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.btn_logout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_logout:
                logout();
                Intent intent = new Intent(this, SplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }
    }

    private void logout() {
        AppRepository.logout(App.sLastLoginUser);
        App.sLastLoginUser = null;
        App.sGithubUser = null;
//        App.sGithubAuth = null;
    }
}
