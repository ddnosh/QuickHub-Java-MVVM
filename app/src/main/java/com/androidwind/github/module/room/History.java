package com.androidwind.github.module.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
@Entity(tableName = "history_table")
public class History {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "history_id")
    private String repoId;

    @NonNull
    @ColumnInfo(name = "history_repo")
    private String repoName;

    @NonNull
    @ColumnInfo(name = "history_url")
    private String repoUrl;

    @NonNull
    @ColumnInfo(name = "history_name")
    private String repoAuth;

    @NonNull
    @ColumnInfo(name = "history_add_time")
    private long addTime;

    @NonNull
    public String getRepoId() {
        return repoId;
    }

    public void setRepoId(@NonNull String repoId) {
        this.repoId = repoId;
    }

    @NonNull
    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(@NonNull String repoName) {
        this.repoName = repoName;
    }

    @NonNull
    public String getRepoUrl() {
        return repoUrl;
    }

    public void setRepoUrl(@NonNull String repoUrl) {
        this.repoUrl = repoUrl;
    }

    @NonNull
    public String getRepoAuth() {
        return repoAuth;
    }

    public void setRepoAuth(@NonNull String repoAuth) {
        this.repoAuth = repoAuth;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }
}
