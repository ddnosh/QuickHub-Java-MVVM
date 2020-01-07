package com.androidwind.github.module.room;

import android.content.Context;

import com.androidwind.github.common.Constant;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */

@Database(entities = {User.class, History.class}, version = 1, exportSchema = false)
public abstract class AppRoomDatabase extends RoomDatabase {

    private static volatile AppRoomDatabase sInstance;

    public static AppRoomDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (AppRoomDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(), AppRoomDatabase.class, Constant.DATABASE_NAME).build();
                }
            }
        }
        return sInstance;
    }

    public abstract UserDao userDao();
    public abstract HistoryDao historyDao();
}
