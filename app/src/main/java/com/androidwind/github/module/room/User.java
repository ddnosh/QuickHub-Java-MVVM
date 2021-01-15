package com.androidwind.github.module.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
@Entity(tableName = "user_table")
public class User {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "user_name")
    private String name;

    @NonNull
    @ColumnInfo(name = "user_token")
    private String token;

    @ColumnInfo(name = "user_login_time")
    private long loginTime;

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getToken() {
        return token;
    }

    public void setToken(@NonNull String token) {
        this.token = token;
    }

    public long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(@NonNull long loginTime) {
        this.loginTime = loginTime;
    }
}
