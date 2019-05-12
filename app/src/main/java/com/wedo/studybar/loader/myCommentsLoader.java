package com.wedo.studybar.loader;

import android.content.Context;

import com.wedo.studybar.util.Discussion;
import com.wedo.studybar.util.QueryUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class myCommentsLoader extends AsyncTaskLoader<List<Discussion>> {
    public myCommentsLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Discussion> loadInBackground() {
        return QueryUtils.extractCommentsByUser(getContext());
    }
}
