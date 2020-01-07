package com.androidwind.base.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidwind.base.module.EventCenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author  ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class QuickFragment extends Fragment {

    protected static String TAG = "QuickFragment";

    protected Context mContext = null;

    private boolean isFirstResume = true;
    private boolean isFirstVisible = true;
    private boolean isFirstInvisible = true;
    private boolean isPrepared;
    /**
     * butterknife 8+ support
     */
    private Unbinder mUnbinder;

    private View mainLayout;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isBindEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = getContentViewLayoutID();
        if (layoutId != 0) {
            try {
                mainLayout=inflater.inflate(layoutId, container, false);
            }catch (NoClassDefFoundError e){
                mainLayout=inflater.inflate(layoutId, container, false);
            }
            mainLayout.setClickable(true);
            return mainLayout;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        initViewsAndEvents(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        if (isBindEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // for bug ---> java.lang.IllegalStateException: Activity has been destroyed
        // not necessary for androidx any more
        // try {
        //     Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
        //     childFragmentManager.setAccessible(true);
        //     childFragmentManager.set(this, null);
        //
        // } catch (NoSuchFieldException e) {
        //     throw new RuntimeException(e);
        // } catch (IllegalAccessException e) {
        //     throw new RuntimeException(e);
        // }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstResume) {
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint()) {
            onUserVisible();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            onUserInvisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
            } else {
                onUserInvisible();
            }
        }
    }

    private synchronized void initPrepare() {
        if (isPrepared) {
            onFirstUserVisible();
        } else {
            isPrepared = true;
        }
    }

    /**
     * bind eventbus
     *
     * @return
     */
    protected abstract boolean isBindEventBus();

    /**
     * when fragment is visible for the first time, here we can do some initialized work or refresh data only once
     */
    protected abstract void onFirstUserVisible();

    /**
     * this method like the fragment's lifecycle method onResume()
     */
    protected abstract void onUserVisible();

    /**
     * when fragment is invisible for the first time
     */
    private void onFirstUserInvisible() {
        // here we do not recommend do something
    }

    /**
     * this method like the fragment's lifecycle method onPause()
     */
    protected abstract void onUserInvisible();

    /**
     * init all views and add events
     */
    protected abstract void initViewsAndEvents(Bundle savedInstanceState);

    /**
     * bind layout resource file
     *
     * @return id of layout resource
     */
    protected abstract int getContentViewLayoutID();

    /**
     * when event comming
     *
     * @param eventCenter
     */
    protected abstract void onEventComing(EventCenter eventCenter);

    /**
     * get the support fragment manager
     *
     * @return
     */
    protected FragmentManager getSupportFragmentManager() {
        return getActivity().getSupportFragmentManager();
    }

    protected Intent getGoIntent(Class<?> clazz) {
        return new Intent(getActivity(), clazz);
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
     * startActivityForResult
     *
     * @param clazz         类名.class，获取类型类
     * @param requestCode   请求码
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
        Intent intent = getGoIntent(clazz);
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

    /**
     * dialog
     */
    public void showLoadingDialog() {
        if (getActivity() != null && getActivity() instanceof QuickActivity) {
            ((QuickActivity) getActivity()).showLoadingDialog();
        }
    }

    public void showLoadingDialog(String tips) {
        if (getActivity() != null && getActivity() instanceof QuickActivity) {
            ((QuickActivity) getActivity()).showLoadingDialog(tips);
        }
    }

    public void dismissLoadingDialog() {
        if (getActivity() != null && getActivity() instanceof QuickActivity) {
            ((QuickActivity) getActivity()).dismissLoadingDialog();
        }
    }

    public CommonDialog.Builder getDialogBuilder(Context context) {
        if (getActivity() != null && getActivity() instanceof QuickActivity) {
            return ((QuickActivity) getActivity()).getDialogBuilder(context);
        }
        throw new IllegalStateException("CommonDialog can only be used in a class which extends QuickActivity!");
    }

}
