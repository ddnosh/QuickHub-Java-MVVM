package com.androidwind.github.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.browser.customtabs.CustomTabsIntent;

import com.androidwind.base.util.StringUtil;
import com.androidwind.github.BuildConfig;
import com.androidwind.github.R;
import com.androidwind.github.ui.base.MVVMActivity;
import com.androidwind.github.ui.main.MainActivity;
import com.androidwind.github.util.PackageUtil;
import com.blankj.utilcode.util.ToastUtils;

import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class LoginActivity extends MVVMActivity<LoginViewModel> {

    @BindView(R.id.et_login_name)
    EditText mName;

    @BindView(R.id.et_login_password)
    EditText mPassword;

    @Override
    protected boolean isLoadDefaultTitleBar() {
        return true;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        super.initViewsAndEvents(savedInstanceState);
        getToolbar().setTitle(getResources().getString(R.string.login));
        getToolbar().setTitleTextColor(Color.parseColor("#ffffff"));
    }

    /**
     * 注意：在GitHub的Settings --> Developer settings中，
     * 设置Authorization callback URL为如下格式：quickhub://login/ddnosh
     * 其中，quickhub字段对应AndroidManifest.xml中LoginActivity中intent-filter下data的scheme, login字段对应host字段
     * 如果不按照如此设置，则onNewIntent不会被调用
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Uri uri = intent.getData();
        if (uri != null) {
            String code = uri.getQueryParameter("code");
            String state = uri.getQueryParameter("state");
            if (!StringUtil.isEmpty(code) && !StringUtil.isEmpty(state)) {
                mViewModel
                        .login(code, state)
                        .observe(this, result -> {
                            if (result.showLoading()) {
                                showLoadingDialog();
                            }
                            if (result.showSuccess()) {
                                getUserInfo();
                            }
                            if (result.showError()) {
                                dismissLoadingDialog();
                                ToastUtils.showShort(result.msg);
                            }
                        });
            }
        }
        setIntent(null);
    }

    private void getUserInfo() {
        mViewModel
                .getUserInfo()
                .observe(this, result -> {
                    dismissLoadingDialog();
                    if (result.showSuccess()) {
                        readyGo(MainActivity.class);
                        finish();
                    }
                    if (result.showError()) {
                        ToastUtils.showShort(result.msg);
                    }
                });
    }

    @OnClick({R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:

                String randomState = UUID.randomUUID().toString();
                // some params such as scope can be defined in local.properties like client_id
                String url = "https://github.com/login/oauth/authorize?client_id=" + BuildConfig.CLIENT_ID + "&scope=user,repo,gist,notifications&state=" + randomState;

                CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().build();
                customTabsIntent.intent.setPackage(PackageUtil.INSTANCE.getBestPackageName(getApplicationContext()));
                customTabsIntent.launchUrl(LoginActivity.this, Uri.parse(url));
        }
    }
}
