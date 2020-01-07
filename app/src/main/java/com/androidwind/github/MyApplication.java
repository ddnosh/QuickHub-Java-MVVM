package com.androidwind.github;

import com.androidwind.github.module.QuickModule;
import com.androidwind.github.module.glide.GlideProcessor;
import com.facebook.stetho.Stetho;
import com.flurry.android.FlurryAgent;
import com.squareup.leakcanary.LeakCanary;

import androidx.multidex.MultiDexApplication;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class MyApplication extends MultiDexApplication {

    private static MyApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        if (INSTANCE == null) {
            INSTANCE = this;
        }
        //init flurry
        new FlurryAgent.Builder()
                .withLogEnabled(true)
                .build(this, "2ZKYQG8VDPS442TF4XWT");
        //init leakCanary
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        //init stetho
        Stetho.initializeWithDefaults(this);
        //init 图片加载模块
        QuickModule.configImage(new GlideProcessor())
                .setLoadingResId(R.mipmap.ic_loading)//设置“加载中”状态时显示的图片
                .setErrorResId(R.mipmap.ic_error)//设置“加载失败”状态时显示的图片
                .setIsUseMemoryCache(true);
        QuickModule.launch();
    }

    public static synchronized MyApplication getInstance() {
        return INSTANCE;
    }
}
