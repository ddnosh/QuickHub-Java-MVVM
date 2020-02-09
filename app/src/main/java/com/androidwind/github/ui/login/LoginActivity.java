package com.androidwind.github.ui.login;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.androidwind.androidquick.util.StringUtil;
import com.androidwind.github.R;
import com.androidwind.github.ui.base.MVVMActivity;
import com.androidwind.github.ui.main.MainActivity;
import com.blankj.utilcode.util.ToastUtils;

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

        //init viewmodel
        getViewModel().getLiveDataLogin()
                .observe(this, result -> {
                    if (result.showLoading()) {
                        showLoadingDialog();
                    }
                    if (result.showSuccess()) {
                        dismissLoadingDialog();
                        readyGo(MainActivity.class);
                        finish();
                    }
                    if (result.showError()) {
                        dismissLoadingDialog();
                        ToastUtils.showShort(result.msg);
                    }
                });
    }

    @OnClick({R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (StringUtil.isEmpty(mName.getText().toString()) || StringUtil.isEmpty(mPassword.getText().toString())) {
                    ToastUtils.showShort(getResources().getString(R.string.account_password_not_null));
                    return;
                }
                getViewModel().login(mName.getText().toString(), mPassword.getText().toString());
                break;
        }
    }
}
