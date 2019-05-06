package com.wedo.studybar.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import katex.hourglass.in.mathlib.MathView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wedo.studybar.Adapter.CommentAdapter;
import com.wedo.studybar.R;
import com.wedo.studybar.loader.discussionDetailLoader;
import com.wedo.studybar.util.Discussion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DiscussionDetailActivity extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<List<Discussion>> {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private FloatingActionButton floatingActionButton;
    private TextView emptyStateTextView;
    private ProgressBar progressBar;
    private CommentAdapter commentAdapter;
    private String discussionId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_detail);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        swipeRefreshLayout = findViewById(R.id.discussion_detail_refresh_layout);
        listView = findViewById(R.id.comments_of_discussion);
        floatingActionButton = findViewById(R.id.comment_floating_action_button);
        emptyStateTextView = findViewById(R.id.discussions_detail_empty_view);
        progressBar = findViewById(R.id.discussions_detail_load_progress);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(true);
                }
                listView.setVisibility(View.GONE);
                floatingActionButton.setVisibility(View.GONE);
                loadDiscussionDetail();
            }
        });

        discussionId = getIntent().getStringExtra("DISCUSSION_ID");

        String discussionAuthor = getIntent().getStringExtra("DISCUSSION_AUTHOR");
        String discussionTitle = getIntent().getStringExtra("DISCUSSION_TITLE");
        String discussionContent = getIntent().getStringExtra("DISCUSSION_CONTENT");

        loadDiscussionDetail();

        final ArrayList<Discussion> comments = new ArrayList<>();
        commentAdapter = new CommentAdapter(this,comments);
        listView.setEmptyView(emptyStateTextView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //todo:to comment detail
            }
        });
        listView.setAdapter(commentAdapter);

        LayoutInflater mInflater = getLayoutInflater();
        ViewGroup bookHeader = (ViewGroup)mInflater.inflate(R.layout.comment_topic_header_view,listView,false);
        listView.addHeaderView(bookHeader);
        listView.setHeaderDividersEnabled(true);

        TextView commentTopicAuthor = (TextView)findViewById(R.id.comment_topic_author);
        TextView commentTopicTitle = (TextView)findViewById(R.id.comment_topic_title);
        MathView commentTopicContent = findViewById(R.id.comment_topic_content);

        commentTopicAuthor.setText(discussionAuthor);
        commentTopicTitle.setText(discussionTitle);
        commentTopicContent.setDisplayText(discussionContent);
        commentTopicContent.setTextSize(14);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DiscussionCommentActivity.class);
                intent.putExtra("DISCUSSION_ID",discussionId);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.discussion_report:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.report)
                        .setMessage(R.string.report_confirmation)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //todo:report operation
                                Toast.makeText(getApplicationContext(),R.string.report_thanks,Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel,null)
                        .show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadDiscussionDetail(){
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.discussion_menu,menu);
        return true;
    }

    @NonNull
    @Override
    public Loader<List<Discussion>> onCreateLoader(int id, @Nullable Bundle args) {
        return new discussionDetailLoader(this,discussionId);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Discussion>> loader, List<Discussion> comments) {
        progressBar.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        emptyStateTextView.setText(R.string.no_discussion);
        if(commentAdapter !=null){
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
