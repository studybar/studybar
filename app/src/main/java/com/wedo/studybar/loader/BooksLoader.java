package com.wedo.studybar.loader;

import android.content.Context;
import android.util.Log;

import com.wedo.studybar.util.Book;
import com.wedo.studybar.util.QueryUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class BooksLoader extends AsyncTaskLoader<List<Book>> {

    private String categoryId;

    public BooksLoader(@NonNull Context context, String categoryId) {
        super(context);
        this.categoryId = categoryId;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Book> loadInBackground() {
        return QueryUtils.extractCategoryDetail(categoryId);
    }
}
