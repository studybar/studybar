package com.wedo.studybar.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wedo.studybar.Adapter.DiscussionAdapter;
import com.wedo.studybar.R;
import com.wedo.studybar.loader.searchResultLoader;
import com.wedo.studybar.util.Discussion;

import java.util.ArrayList;
import java.util.List;

public class SearchableActivity extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<List<Discussion>> {

    private ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView emptyStateTextView;

    private DiscussionAdapter discussionAdapter;

    private String query;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        swipeRefreshLayout = findViewById(R.id.refresh_layout);
        listView = findViewById(R.id.list_view);
        progressBar = findViewById(R.id.list_progress_bar);
        emptyStateTextView = findViewById(R.id.empty_view);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(true);
                }
                progressBar.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
                doSearch();
            }
        });

        handleIntent(getIntent());
        this.getSupportActionBar().setTitle(query);

        final ArrayList<Discussion> discussions = new ArrayList<>();
        discussionAdapter = new DiscussionAdapter(this,discussions);
        listView.setEmptyView(emptyStateTextView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),DiscussionDetailActivity.class);
                intent.putExtra("DISCUSSION_ID",discussions.get(position).getDiscussionId());
                intent.putExtra("DISCUSSION_AUTHOR",discussions.get(position).getDiscussionAuthor());
                intent.putExtra("DISCUSSION_TITLE",discussions.get(position).getDiscussionTitle());
                intent.putExtra("DISCUSSION_CONTENT",discussions.get(position).getDiscussionContent());
                startActivity(intent);
            }
        });
        listView.setAdapter(discussionAdapter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
            doSearch();
        }
    }

    private void doSearch() {
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // 引用 LoaderManager，以便与 loader 进行交互。
            LoaderManager loaderManager = getSupportLoaderManager();

            if(swipeRefreshLayout.isRefreshing()){
                loaderManager.restartLoader(1, null, this);
            }else {
                // 初始化 loader。传递上面定义的整数 ID 常量并为为捆绑
                // 传递 null。为 LoaderCallbacks 参数（由于
                // 此活动实现了 LoaderCallbacks 接口而有效）传递此活动。
                loaderManager.initLoader(1, null, this);
            }
        }
        else{
            progressBar.setVisibility(View.GONE);
            emptyStateTextView.setText(R.string.no_internet);
        }
    }

    @NonNull
    @Override
    public Loader<List<Discussion>> onCreateLoader(int id, @Nullable Bundle args) {
        return new searchResultLoader(this,query);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Discussion>> loader, List<Discussion> discussions) {
        progressBar.setVisibility(View.GONE);
        emptyStateTextView.setText(R.string.no_discussion);
        swipeRefreshLayout.setRefreshing(false);
        if(discussionAdapter!=null){
            discussionAdapter.clear();
        }
        if(discussions != null && !discussions.isEmpty()){
            discussionAdapter.addAll(discussions);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Discussion>> loader) {
        discussionAdapter.clear();
    }
}
