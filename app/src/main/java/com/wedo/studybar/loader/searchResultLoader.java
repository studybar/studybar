package com.wedo.studybar.loader;

import android.content.Context;

import com.wedo.studybar.util.Book;
import com.wedo.studybar.util.Discussion;
import com.wedo.studybar.util.QueryUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class searchResultLoader extends AsyncTaskLoader<List<Discussion>> {

    private String content;

    public searchResultLoader(@NonNull Context context, String content) {
        super(context);
        this.content = content;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Discussion> loadInBackground() {
        return QueryUtils.extractSearchResult(getContext(),content);
    }
}
