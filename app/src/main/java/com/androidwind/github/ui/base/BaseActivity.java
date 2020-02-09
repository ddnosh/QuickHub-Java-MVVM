package com.androidwind.github.ui.base;

import android.content.Intent;
import android.os.Bundle;

import com.androidwind.androidquick.module.asynchronize.eventbus.EventCenter;
import com.androidwind.androidquick.ui.base.QuickActivity;

import org.jetbrains.annotations.NotNull;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class BaseActivity extends QuickActivity {

    @NotNull
    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.LEFT;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    //默认不使用自带titlebar
    @Override
    protected boolean isLoadDefaultTitleBar() {
        return false;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    //默认不开启eventbus
    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

    @Override
    protected Intent getGoIntent(Class<?> clazz) {
        if (BaseFragment.class.isAssignableFrom(clazz)) {
            Intent intent = new Intent(this, FragmentActivity.class);
            intent.putExtra("fragmentName", clazz.getName());
            return intent;
        } else {
            return super.getGoIntent(clazz);
        }
    }
}
