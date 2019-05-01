package com.wedo.studybar.util;

import android.content.Context;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class myDiscussionsLoader extends AsyncTaskLoader<List<Discussion>> {
    public myDiscussionsLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Discussion> loadInBackground() {
        List<Discussion> discussions = QueryUtils.extractDiscussionsByUser(getContext());
        return discussions;
    }
}
