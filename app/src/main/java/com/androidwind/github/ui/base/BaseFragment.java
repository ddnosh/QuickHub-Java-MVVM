package com.androidwind.github.ui.base;

import com.androidwind.base.module.EventCenter;
import com.androidwind.base.ui.QuickFragment;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class BaseFragment extends QuickFragment {

    @Override
    protected boolean isBindEventBus() {
        return false;
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void onEventComing(EventCenter eventCenter) {

    }

}
