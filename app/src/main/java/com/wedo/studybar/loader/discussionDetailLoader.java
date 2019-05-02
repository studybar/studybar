package com.wedo.studybar.loader;

import android.content.Context;

import com.wedo.studybar.util.Discussion;
import com.wedo.studybar.util.QueryUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class discussionDetailLoader extends AsyncTaskLoader<List<Discussion>> {

    private String discussionId;
    public discussionDetailLoader(@NonNull Context context, String discussionId) {
        super(context);
        this.discussionId = discussionId;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Discussion> loadInBackground() {
        List<Discussion> comments = QueryUtils.extractDiscussionDetail(getContext(),discussionId);
        return comments;
    }
}
