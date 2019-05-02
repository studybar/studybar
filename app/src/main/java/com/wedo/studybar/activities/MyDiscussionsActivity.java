package com.wedo.studybar.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wedo.studybar.Adapter.DiscussionAdapter;
import com.wedo.studybar.R;
import com.wedo.studybar.util.Discussion;
import com.wedo.studybar.loader.myDiscussionsLoader;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MyDiscussionsActivity extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<List<Discussion>> {

    private DiscussionAdapter discussionAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private ProgressBar progressBar;
    private TextView emptyStateTextView;
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_discussions);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        swipeRefreshLayout = findViewById(R.id.my_discussions_refresh_layout);
        listView = findViewById(R.id.my_discussions_list);
        progressBar = findViewById(R.id.my_discussions_load_progress);
        emptyStateTextView = findViewById(R.id.my_discussions_empty_view);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(true);
                    progressBar.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    loadMyDiscussions();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        loadMyDiscussions();

        final ArrayList<Discussion> discussions = new ArrayList<>();
        discussionAdapter = new DiscussionAdapter(this,discussions);
        listView.setEmptyView(emptyStateTextView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: passing values
                Intent intent = new Intent(getApplicationContext(),DiscussionDetailActivity.class);
                Log.e("POSITION",String.valueOf(position));

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadMyDiscussions(){
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // 引用 LoaderManager，以便与 loader 进行交互。
            LoaderManager loaderManager = getSupportLoaderManager();

            // 初始化 loader。传递上面定义的整数 ID 常量并为为捆绑
            // 传递 null。为 LoaderCallbacks 参数（由于
            // 此活动实现了 LoaderCallbacks 接口而有效）传递此活动。
            loaderManager.initLoader(1, null, this);
        }
        else{
            progressBar.setVisibility(View.GONE);
            emptyStateTextView.setText(R.string.no_internet);
        }
    }

    @NonNull
    @Override
    public Loader<List<Discussion>> onCreateLoader(int id, @Nullable Bundle args) {
        return new myDiscussionsLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Discussion>> loader, List<Discussion> discussions) {
        progressBar.setVisibility(View.GONE);
        emptyStateTextView.setText(R.string.no_discussion);
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
