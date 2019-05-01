package com.wedo.studybar.loader;

import android.content.Context;

import com.wedo.studybar.util.Book;
import com.wedo.studybar.util.QueryUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class BooksLoader extends AsyncTaskLoader<List<Book>> {

    private String categoryName;

    public BooksLoader(@NonNull Context context, String categoryName) {
        super(context);
        this.categoryName = categoryName;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Book> loadInBackground() {
        List<Book> books = QueryUtils.extractBooks(categoryName);
        return books;
    }
}
