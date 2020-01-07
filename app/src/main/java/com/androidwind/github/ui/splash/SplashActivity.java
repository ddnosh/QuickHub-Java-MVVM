package com.androidwind.github.ui.splash;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Message;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.androidwind.base.base.SafeHandler;
import com.androidwind.base.module.BaseObserver;
import com.androidwind.base.module.exeception.ApiException;
import com.androidwind.github.R;
import com.androidwind.github.common.App;
import com.androidwind.github.mvvm.AppRepository;
import com.androidwind.github.module.room.User;
import com.androidwind.github.ui.base.BaseActivity;
import com.androidwind.github.ui.login.LoginActivity;
import com.androidwind.github.ui.main.MainActivity;
import com.blankj.utilcode.util.LogUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class SplashActivity extends BaseActivity {
    private LottieAnimationView animationView;
    private SafeHandler safeHandler = new MyHandler(this);

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        //lottie动画
        animationView = findViewById(R.id.animation_view);
        animationView.setProgress(0f);
        animationView.playAnimation();
        //延时2.5秒
        safeHandler.sendEmptyMessageDelayed(0, 2500);
    }

    @Override
    public void onStop() {
        super.onStop();
        animationView.cancelAnimation();
    }

    @Override
    protected void onDestroy() {
        safeHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_splash;
    }

    //防内存泄漏handler
    private static final class MyHandler extends SafeHandler<SplashActivity> {

        public MyHandler(SplashActivity splashActivity) {
            super(splashActivity);
        }

        @SuppressLint("CheckResult")
        @Override
        public void disposeMessage(SplashActivity splashActivity, Message msg) {
            //权限判断
            RxPermissions rxPermissions = new RxPermissions(splashActivity);
            rxPermissions
                    .request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(granted -> {
                        if (granted) {
                            //数据库查询在子线程中完成
                            AppRepository.queryLastUser()
                                    .subscribe(new BaseObserver<List<User>>() {
                                        @Override
                                        public void onError(ApiException exception) {
                                            LogUtils.eTag(TAG, exception.toString());//打印错误日志
                                        }

                                        @Override
                                        public void onSuccess(List<User> userList) {
                                            if (userList == null || userList.size() == 0) {
                                                splashActivity.readyGo(LoginActivity.class);
                                            } else {
                                                User user = userList.get(0);
                                                App.sLastLoginUser = user;
                                                splashActivity.readyGo(MainActivity.class);
                                            }
                                            splashActivity.finish();
                                        }
                                    });
                        } else {
                            Toast.makeText(splashActivity, splashActivity.getResources().getString(R.string.permission_fail), Toast.LENGTH_SHORT).show();
                            splashActivity.finish();
                        }
                    });
        }

    }
}
