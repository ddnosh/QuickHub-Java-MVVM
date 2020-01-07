/**
 * @Description: Activity的一个基类, 提供丰富的功能, 旨在开发一款新APP应用的时候直接继承使用
 * @Detail: 1.   默认提供六种转场动画, 如需自定义, 请在子类调用父类的onCreate后添加overridePendingTransition
 * 2.   封装页面跳转传参
 * 3.   EventBus事件总线
 * 4.   管理所有启动的activity
 * 5.   设备屏幕信息
 * 6.   监听网络状态变化
 * 7.   支持butterknife 8+, databinding
 * 8.   页面跳转: readyGo, readyGoThenKill, readyGoForResult
 * 9.   提供页面状态展示: loading, network error, error, empty
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */

package com.androidwind.base.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidwind.base.R;
import com.androidwind.base.module.EventCenter;
import com.androidwind.base.util.StringUtil;
import com.androidwind.base.util.immersion.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class QuickActivity extends AppCompatActivity {

    protected static String TAG = "QuickActivity";

    protected Context mContext = null;

    /**
     * butterknife 8+ support
     */
    private Unbinder mUnbinder;

    /**
     * default toolbar
     */
    protected TextView tvTitle;
    protected TextView tvRight;
    protected Toolbar toolbar;
    protected FrameLayout toolbarLayout;
    protected LinearLayout contentView;
    /**
     * dialog
     */
    protected LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        // extras
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }
        // eventbus
        if (isBindEventBus()) {
            EventBus.getDefault().register(this);
        }
        // layout
        int layoutId = getContentViewLayoutID();
        if (layoutId != 0) {
            setContentView(layoutId);
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
        // system status bar immersion
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        StatusBarUtil.setTranslucentStatus(this);
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            StatusBarUtil.setStatusBarColor(this, 0x55000000);
        }
        // network status
        NetStateReceiver.registerNetworkStateReceiver(mContext);
        initViewsAndEvents(savedInstanceState);
    }

    public void setContentView(int layoutResID) {
        // 添加toolbar布局
        if (isLoadDefaultTitleBar()) {
            initToolBarView();
            initContentView();
            contentView.addView(toolbarLayout, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            LayoutInflater.from(this).inflate(layoutResID, contentView, true);
            super.setContentView(contentView, new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            super.setContentView(layoutResID);
        }
        mUnbinder = ButterKnife.bind(this);
    }

    protected void initContentView() {
        contentView = new LinearLayout(this);
        contentView.setOrientation(LinearLayout.VERTICAL);
    }

    private void initToolBarView() {
        toolbarLayout = new FrameLayout(this);
        LayoutInflater.from(this).inflate(R.layout.layout_toolbar, toolbarLayout, true);
        tvTitle = (TextView) toolbarLayout.findViewById(R.id.tv_title);
        tvRight = (TextView) toolbarLayout.findViewById(R.id.tv_right);
        toolbar = (Toolbar) toolbarLayout.findViewById(R.id.toolbar);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        initToolBarSet(actionBar);
    }

    protected void initToolBarSet(ActionBar actionBar) {
        actionBar.setDisplayShowTitleEnabled(false);

        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeAsUpIndicator(R.mipmap.icon_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    protected Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        if (isBindEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        dismissLoadingDialog();
    }

    /**
     * default title bar
     *
     * @return
     */
    protected abstract boolean isLoadDefaultTitleBar();

    /**
     * bind eventbus
     *
     * @return
     */
    protected abstract boolean isBindEventBus();

    /**
     * get bundle data
     *
     * @param extras
     */
    protected abstract void getBundleExtras(Bundle extras);

    /**
     * bind layout resource file
     *
     * @return id of layout resource
     */
    protected abstract int getContentViewLayoutID();

    /**
     * when event coming
     *
     * @param eventCenter
     */
    protected abstract void onEventComing(EventCenter eventCenter);

    /**
     * init all views and add events
     */
    protected abstract void initViewsAndEvents(Bundle savedInstanceState);

    /**
     * 会被子类覆盖去封装 跳转到不同的activity 或者fragment的页面
     *
     * @param clazz
     * @return
     */
    protected Intent getGoIntent(Class<?> clazz) {
        return new Intent(this, clazz);
    }


    /**
     * startActivity
     *
     * @param clazz
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = getGoIntent(clazz);
        startActivity(intent);
    }

    /**
     * startActivity with  flag
     *
     * @param clazz
     * @param flag
     */
    protected void readyGo(Class<?> clazz, int flag) {
        Intent intent = getGoIntent(clazz);
        intent.setFlags(flag);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = getGoIntent(clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity with bundle and flag
     *
     * @param clazz
     * @param bundle
     * @param flag
     */
    protected void readyGo(Class<?> clazz, Bundle bundle, int flag) {
        Intent intent = getGoIntent(clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        intent.setFlags(flag);
        startActivity(intent);
    }


    /**
     * startActivity then finish
     *
     * @param clazz
     */
    protected void readyGoThenKill(Class<?> clazz) {
        Intent intent = getGoIntent(clazz);
        startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = getGoIntent(clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = getGoIntent(clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(EventCenter eventCenter) {
        if (null != eventCenter) {
            onEventComing(eventCenter);
        }
    }

    public void showLoadingDialog() {
        showLoadingDialog(null);
    }

    public void showLoadingDialog(String tips) {
        if (!isFinishing()) {
            try {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog(this);
                    if (!StringUtil.isEmpty(tips)) {
                        loadingDialog.setTip(tips);
                    }
                    loadingDialog.show();
                } else {
                    //相同的上下文，无需重新创建
                    if (loadingDialog.getLoadingDialogContext() == this) {
                        loadingDialog.show();
                    } else {
                        loadingDialog.dismiss();
                        loadingDialog = new LoadingDialog(this);
                        if (!StringUtil.isEmpty(tips)) {
                            loadingDialog.setTip(tips);
                        }
                        loadingDialog.show();
                    }
                }

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public void dismissLoadingDialog() {
        try {
            if (!isFinishing() && loadingDialog != null && loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public CommonDialog.Builder getDialogBuilder(Context context) {
        return new CommonDialog.Builder(context);
    }

}
