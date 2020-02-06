package com.androidwind.github.ui.history;

import com.androidwind.github.MyApplication;
import com.androidwind.github.module.room.AppRoomDatabase;
import com.androidwind.github.module.room.History;
import com.androidwind.github.mvvm.BaseRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class HistoryRepository extends BaseRepository {

    private LiveData<List<History>> liveDataHistory =  new MutableLiveData<>();;

    public LiveData<List<History>> getLiveDataHistory() {
        return liveDataHistory;
    }

    public LiveData<List<History>> getHistory(int count, int offset) {
        liveDataHistory = AppRoomDatabase.getInstance(MyApplication.getInstance()).historyDao().getHistory(count, offset);
        return liveDataHistory;
    }
}
