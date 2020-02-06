package com.androidwind.github.ui.history;

import android.app.Application;

import com.androidwind.github.module.room.History;
import com.androidwind.github.mvvm.BaseViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public class HistoryViewModel extends BaseViewModel<HistoryRepository> {

    private LiveData<List<History>> liveDataHistory;

    public LiveData<List<History>> getLiveDataHistory() {
        return liveDataHistory;
    }

    public HistoryViewModel(@NonNull Application application) {
        super(application);
        liveDataHistory = repository.getLiveDataHistory();
    }

    public LiveData<List<History>> getHistory(int count, int offset) {
        return repository.getHistory(count, offset);
    }
}
