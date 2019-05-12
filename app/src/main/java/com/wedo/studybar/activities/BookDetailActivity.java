package com.wedo.studybar.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.wedo.studybar.Adapter.DiscussionAdapter;
import com.wedo.studybar.R;
import com.wedo.studybar.loader.bookDetailLoader;
import com.wedo.studybar.util.Book;
import com.wedo.studybar.util.Discussion;

import java.util.ArrayList;
import java.util.List;

public class BookDetailActivity extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<List<Discussion>>{

    private TextView emptyStateTextView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private FloatingActionButton fab;

    private ImageView bookCoverView;
    private TextView bookTitleView;
    private TextView bookCommentNumView;
    private ToggleButton buttonFollow;

    private String bookId;
    private String bookName;
    private Bitmap bookCover;
    private String bookCommentsNum;

    private DiscussionAdapter discussionAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        emptyStateTextView = findViewById(R.id.book_detail_empty_view);
        progressBar = findViewById(R.id.book_detail_load_progress);
        swipeRefreshLayout = findViewById(R.id.discussion_books_refresh_layout);
        listView = findViewById(R.id.discussion_of_books);
        fab = findViewById(R.id.button_book_comment);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(true);
                }
                progressBar.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
                loadBookDetail();
            }
        });
        bookId = getIntent().getStringExtra("BOOK_ID");
        bookName = getIntent().getStringExtra("BOOK_NAME");
        bookCover = BitmapFactory.decodeByteArray(
                getIntent().getByteArrayExtra("BOOK_COVER"),0,getIntent().getByteArrayExtra("BOOK_COVER").length);
        //bookCover = getIntent().getStringExtra("BOOK_COVER");
        bookCommentsNum = getIntent().getStringExtra("BOOK_COMMENT_COUNT");

        loadBookDetail();

        final ArrayList<Discussion> discussions = new ArrayList<>();
        discussionAdapter = new DiscussionAdapter(this,discussions);
        //listView.setEmptyView(emptyStateTextView);
        toggleEmptyView(discussionAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position--;
                Intent intent = new Intent(getApplicationContext(),DiscussionDetailActivity.class);
                intent.putExtra("DISCUSSION_ID",discussions.get(position).getDiscussionId());
                intent.putExtra("DISCUSSION_AUTHOR",discussions.get(position).getDiscussionAuthor());
                intent.putExtra("DISCUSSION_TITLE",discussions.get(position).getDiscussionTitle());
                intent.putExtra("DISCUSSION_CONTENT",discussions.get(position).getDiscussionContent());
                startActivity(intent);
            }
        });
        listView.setAdapter(discussionAdapter);

        LayoutInflater mInflater = getLayoutInflater();
        ViewGroup bookHeader = (ViewGroup)mInflater.inflate(R.layout.vertical_list_book_item,listView,false);
        listView.addHeaderView(bookHeader);
        listView.setHeaderDividersEnabled(true);

        bookCoverView = findViewById(R.id.book_search_cover);
        bookTitleView = findViewById(R.id.book_search_title);
        bookCommentNumView = findViewById(R.id.search_num_of_discuss);

        //byte[] coverBytesArray = Base64.decode(bookCover,Base64.DEFAULT);
        //Bitmap bitmap = BitmapFactory.decodeByteArray(coverBytesArray,0,coverBytesArray.length);
        bookCoverView.setImageBitmap(bookCover);
        //bookCoverView.setImageResource(R.drawable.test);
        bookTitleView.setText(bookName);
        bookCommentNumView.setText(bookCommentsNum);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),BookCommentActivity.class);
                intent.putExtra("BOOK_ID",bookId);
                startActivity(intent);
            }
        });
    }

    private void loadBookDetail() {
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
                loaderManager.restartLoader(7, null, this);
            }else {
                // 初始化 loader。传递上面定义的整数 ID 常量并为为捆绑
                // 传递 null。为 LoaderCallbacks 参数（由于
                // 此活动实现了 LoaderCallbacks 接口而有效）传递此活动。
                loaderManager.initLoader(7, null, this);
            }
        }
        else{
            progressBar.setVisibility(View.GONE);
            emptyStateTextView.setText(R.string.no_internet);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.report:
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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.book_menu,menu);
        return true;
    }

    @NonNull
    @Override
    public Loader<List<Discussion>> onCreateLoader(int id, @Nullable Bundle args) {
        return new bookDetailLoader(this,bookId);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Discussion>> loader, List<Discussion> data) {
        progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        emptyStateTextView.setText(R.string.no_discussion);
        if(discussionAdapter != null){
            discussionAdapter.clear();
        }
        if (data != null && !data.isEmpty()){
            discussionAdapter.addAll(data);
        }
        swipeRefreshLayout.setRefreshing(false);
    }


    @Override
    public void onLoaderReset(@NonNull Loader<List<Discussion>> loader) {
        discussionAdapter.clear();
    }

    /**
     * Custom empty view handling because we don't want the
     * list to be hidden when the empty view is displayed,
     * since the list must always display the header.
     */
    private void toggleEmptyView(final DiscussionAdapter adapter)
    {
        //final View emptyView = findViewById(R.id.empty_view);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                emptyStateTextView.setVisibility(adapter.getCount() == 0 ? View.VISIBLE : View.GONE);
            }
        });
    }
}
