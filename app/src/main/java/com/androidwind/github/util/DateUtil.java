package com.androidwind.github.util;

import android.content.Context;

import com.androidwind.github.R;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class DateUtil {

    public static String convertTimeToFormat(Context context, long timeStamp) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long time = curTime - timeStamp / 1000;

        if (time < 60 && time >= 0) {
            return context.getResources().getString(R.string.just_now);
        } else if (time >= 60 && time < 3600) {
            return time / 60 + context.getResources().getString(R.string.minute);
        } else if (time >= 3600 && time < 3600 * 24) {
            return time / 3600 + context.getResources().getString(R.string.hour);
        } else if (time >= 3600 * 24 && time < 3600 * 24 * 30) {
            return time / 3600 / 24 + context.getResources().getString(R.string.day);
        } else if (time >= 3600 * 24 * 30 && time < 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 + context.getResources().getString(R.string.month);
        } else if (time >= 3600 * 24 * 30 * 12) {
            return time / 3600 / 24 / 30 / 12 + context.getResources().getString(R.string.year);
        } else {
            return context.getResources().getString(R.string.just_now);
        }
    }
}
