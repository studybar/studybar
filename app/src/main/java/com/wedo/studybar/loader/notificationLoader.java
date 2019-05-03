package com.wedo.studybar.loader;

import android.content.Context;

import com.wedo.studybar.util.Notification;
import com.wedo.studybar.util.QueryUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class notificationLoader extends AsyncTaskLoader<List<Notification>> {

    public notificationLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Notification> loadInBackground() {
        return QueryUtils.extractNotifications(getContext());
    }
}
