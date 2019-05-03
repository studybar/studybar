package com.wedo.studybar.loader;

import android.content.Context;

import com.wedo.studybar.util.Discussion;
import com.wedo.studybar.util.QueryUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class bookDetailLoader extends AsyncTaskLoader<List<Discussion>> {

    private String bookId;

    public bookDetailLoader(@NonNull Context context,String bookId) {
        super(context);
        this.bookId = bookId;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Discussion> loadInBackground() {
        return QueryUtils.extractBookDetail(getContext(),bookId);
    }
}
