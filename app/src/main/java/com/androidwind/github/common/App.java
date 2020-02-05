package com.androidwind.github.common;

import com.androidwind.github.BuildConfig;
import com.androidwind.github.bean.GithubUser;
import com.androidwind.github.module.retrofit.RetrofitManager;
import com.androidwind.github.module.room.User;

/**
 * 变量
 *
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class App {

    public final static String QUICKHUB_CLIENT_ID = BuildConfig.QUICKHUB_CLIENT_ID;

    public final static String QUICKHUB_CLIENT_SECRET = BuildConfig.QUICKHUB_CLIENT_SECRET;

    public final static String REDIRECT_URL = "https://github.com/ddnosh";

    public static String sCurrentUserName;
    public static User sLastLoginUser;
    public static GithubUser sGithubUser;

    public static void reset() {
        sCurrentUserName = null;
        sLastLoginUser = null;
        sGithubUser = null;
        RetrofitManager.INSTANCE.reset();
    }
}
