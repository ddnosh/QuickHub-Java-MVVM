package com.androidwind.github.mvvm;

import com.androidwind.base.util.RxUtil;
import com.androidwind.github.MyApplication;
import com.androidwind.github.module.room.AppRoomDatabase;
import com.androidwind.github.module.room.History;
import com.androidwind.github.module.room.User;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class AppRepository {

    private static final String TAG = "AppRepository";

    //确保数据库操作在子线程
    public static void insertUser(User user) {
        Observable.just(user)
                .subscribeOn(Schedulers.io())
                .subscribe(s -> AppRoomDatabase.getInstance(MyApplication.getInstance()).userDao().insertOrUpdate(user));
    }

    public static Observable<List<User>> queryLastUser() {
        return Observable.create((ObservableOnSubscribe<List<User>>) emitter -> {
                    List<User> userList =
                            AppRoomDatabase.getInstance(MyApplication.getInstance()).userDao().getLoginUser();
                    emitter.onNext(userList);
                }
        ).compose(RxUtil.applySchedulers());//数据库操作默认在子线程中完成, 然后通过UI线程返回下一步处理
    }

    public static void logout(User user) {
        Observable.just(user)
                .subscribeOn(Schedulers.io())
                .subscribe(s -> AppRoomDatabase.getInstance(MyApplication.getInstance()).userDao().delete(user));
    }

    public static void insertHistory(History history) {
        Observable.just(history)
                .subscribeOn(Schedulers.io())
                .subscribe(s -> AppRoomDatabase.getInstance(MyApplication.getInstance()).historyDao().insertOrUpdate(history));
    }

    public static void deleteHistory(History history) {
        Observable.just(history)
                .subscribeOn(Schedulers.io())
                .subscribe(s -> AppRoomDatabase.getInstance(MyApplication.getInstance()).historyDao().delete(history));
    }
}
