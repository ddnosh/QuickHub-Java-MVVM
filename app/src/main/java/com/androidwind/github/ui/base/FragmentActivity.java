package com.androidwind.github.ui.base;

import android.os.Bundle;

import com.androidwind.base.ui.QuickFragment;
import com.androidwind.base.util.ReflectUtil;
import com.androidwind.github.R;
import com.blankj.utilcode.util.LogUtils;

import androidx.appcompat.widget.Toolbar;

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class FragmentActivity extends BaseActivity {

    protected static String TAG = "FrameActivity";
    private Bundle bundle;

    @Override
    protected boolean isLoadDefaultTitleBar() {
        return true;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_fragment;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        bundle = extras;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        String className = bundle.getString("fragmentName");
        LogUtils.i(TAG, "the fragment class name is->" + className);
        if (className != null) {
            Object object = ReflectUtil.getObject(className);
            if (object instanceof QuickFragment) {
                QuickFragment fragment = (QuickFragment) object;
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commitAllowingStateLoss();
                }
            } else {
                LogUtils.e(TAG, " the fragment class is not exist!!!");
            }
        }
    }

    public Toolbar getToolbar() {
        return super.getToolbar();
    }

}
