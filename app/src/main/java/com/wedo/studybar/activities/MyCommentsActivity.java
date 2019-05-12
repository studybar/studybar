package com.wedo.studybar.activities;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wedo.studybar.Adapter.CommentAdapter;
import com.wedo.studybar.Adapter.MyCommentAdapter;
import com.wedo.studybar.R;
import com.wedo.studybar.loader.myCommentsLoader;
import com.wedo.studybar.util.Discussion;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MyCommentsActivity extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<List<Discussion>> {

    private TextView emptyStateTextView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;

    private MyCommentAdapter commentAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comments);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        emptyStateTextView = findViewById(R.id.my_comments_empty_view);
        progressBar = findViewById(R.id.my_comments_load_progress);
        swipeRefreshLayout = findViewById(R.id.my_comments_refresh_layout);
        listView = findViewById(R.id.my_comments_list);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(true);
                }
                listView.setVisibility(View.GONE);
                loadMyComments();
            }
        });

        loadMyComments();

        final ArrayList<Discussion> comments = new ArrayList<>();
        commentAdapter = new MyCommentAdapter(this,0,comments);
        toggleEmptyView(commentAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("CLICK",String.valueOf(position));
                Intent intent = new Intent(getApplicationContext(),DiscussionDetailActivity.class);
                intent.putExtra("DISCUSSION_ID",comments.get(position).getTopic().getDiscussionId());
                intent.putExtra("DISCUSSION_AUTHOR",comments.get(position).getTopic().getDiscussionAuthor());
                intent.putExtra("DISCUSSION_TITLE",comments.get(position).getTopic().getDiscussionTitle());
                intent.putExtra("DISCUSSION_CONTENT",comments.get(position).getTopic().getDiscussionContent());
                startActivity(intent);
            }
        });

        listView.setAdapter(commentAdapter);
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

    private void loadMyComments() {
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
                loaderManager.restartLoader(11, null, this);
            }else {
                // 初始化 loader。传递上面定义的整数 ID 常量并为为捆绑
                // 传递 null。为 LoaderCallbacks 参数（由于
                // 此活动实现了 LoaderCallbacks 接口而有效）传递此活动。
                loaderManager.initLoader(11, null, this);
            }
        }
        else{
            progressBar.setVisibility(View.GONE);
            emptyStateTextView.setText(R.string.no_internet);
        }
    }

    /**
     * Custom empty view handling because we don't want the
     * list to be hidden when the empty view is displayed,
     * since the list must always display the header.
     */
    private void toggleEmptyView(final Adapter adapter)
    {
        //final View emptyView = findViewById(R.id.empty_view);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                emptyStateTextView.setVisibility(adapter.getCount() == 0 ? View.VISIBLE : View.GONE);
            }
        });
    }

    @NonNull
    @Override
    public Loader<List<Discussion>> onCreateLoader(int id, @Nullable Bundle args) {
        return new myCommentsLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Discussion>> loader, List<Discussion> comments) {
        progressBar.setVisibility(View.GONE);
        emptyStateTextView.setText(R.string.no_discussion);
        listView.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);
        if(commentAdapter!=null){
            commentAdapter.clear();
        }
        if(comments != null && !comments.isEmpty()){
            commentAdapter.addAll(comments);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Discussion>> loader) {
        commentAdapter.clear();
    }
}
