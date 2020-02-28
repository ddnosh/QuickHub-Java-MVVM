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

    public HistoryViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<History>> getHistory(int count, int offset) {
        return repository.getHistory(count, offset);
    }
}
