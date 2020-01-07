package com.androidwind.github.ui.about;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.androidwind.github.BuildConfig;
import com.androidwind.github.R;
import com.androidwind.github.common.Constant;
import com.androidwind.github.ui.base.BaseActivity;
import com.androidwind.github.ui.base.WebViewActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class AboutActivity extends BaseActivity {
    @BindView(R.id.tv_version)
    TextView mVersion;
    @BindView(R.id.tv_url)
    TextView mUrl;

    @Override
    protected boolean isLoadDefaultTitleBar() {
        return true;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_about;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        mVersion.setText(BuildConfig.VERSION_NAME);
        getToolbar().setTitle(getResources().getString(R.string.about));
        getToolbar().setTitleTextColor(Color.parseColor("#ffffff"));
    }

    @OnClick({R.id.tv_url})
    public void onClick(View view) {
        if (view.getId() == R.id.tv_url) {
            Bundle bundle = new Bundle();
            bundle.putString(Constant.QUICKHUB_WEB_URL, mUrl.getText().toString());
            readyGo(WebViewActivity.class, bundle);
        }
    }
}
