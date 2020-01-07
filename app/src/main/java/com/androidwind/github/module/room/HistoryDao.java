package com.androidwind.github.module.room;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
@Dao
public interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(History history);

    @Delete
    void delete(History history);

    @Update
    void update(History history);

    @Query("SELECT * from history_table where history_id = :repoId")
    History getHistory(String repoId);

    //分页查询
    @Query("SELECT * FROM history_table ORDER BY history_add_time DESC LIMIT :count OFFSET :offset")
    LiveData<List<History>> getHistory(int count, int offset);

    //自动判断插入或更新
    default void insertOrUpdate(History history) {
        History historyFromDB = getHistory(history.getRepoId());
        if (historyFromDB == null) {
            insert(history);
        } else {
            update(history);
        }
    }
}
