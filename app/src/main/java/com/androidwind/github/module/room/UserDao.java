package com.androidwind.github.module.room;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Observable;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

    @Query("DELETE FROM user_table")
    void deleteAll();

    @Query("SELECT * from user_table ORDER BY user_login_time")
    LiveData<List<User>> getAllUser();

    @Query("SELECT * from user_table where user_name = :name")
    User getUser(String name);

    @Query("SELECT * from user_table ORDER BY user_login_time DESC LIMIT 1")
    LiveData<List<User>> getLoginUserByLiveData();

    @Query("SELECT * from user_table ORDER BY user_login_time DESC LIMIT 1")
    Observable<List<User>> getLoginUserByRxJava();

    @Query("SELECT * from user_table ORDER BY user_login_time DESC LIMIT 1")
    List<User> getLoginUser();

    //自动判断插入或更新
    default void insertOrUpdate(User user) {
        User userFromDB = getUser(user.getName());
        if (userFromDB == null) {
            insert(user);
        } else {
            update(user);
        }
    }
}
