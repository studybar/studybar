package com.wedo.studybar.activities;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wedo.studybar.Adapter.VerticalBookAdapter;
import com.wedo.studybar.R;
import com.wedo.studybar.loader.BooksLoader;
import com.wedo.studybar.util.Book;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class CategoryDetailActivity extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<List<Book>> {

    private SwipeRefreshLayout swipeRefreshLayout;
    String category;
    private VerticalBookAdapter itemsAdapter;
    private ProgressBar progressBar;
    private TextView emptyStateTextView;
    private ListView listView;

    private String categoryId;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Integer categoryName = getIntent().getIntExtra("CATEGORY_NAME",R.string.blank);
        this.setTitle(categoryName);

        progressBar = findViewById(R.id.list_progress_bar);
        emptyStateTextView = findViewById(R.id.empty_view);
        swipeRefreshLayout = findViewById(R.id.refresh_layout);
        listView = (ListView)findViewById(R.id.list_view);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(true);
                }
                listView.setVisibility(View.GONE);
                loadBooks();
            }
        });

        categoryId = getIntent().getStringExtra("CATEGORY_ID");

        loadBooks();

        final ArrayList<Book> books = new ArrayList<>();
        itemsAdapter = new VerticalBookAdapter(this,books);
        listView.setEmptyView(emptyStateTextView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),BookDetailActivity.class);
                intent.putExtra("BOOK_ID",books.get(position).getBookId());
                intent.putExtra("BOOK_NAME",books.get(position).getBookName());
                intent.putExtra("BOOK_COVER", books.get(position).getCoverUrl());
                intent.putExtra("BOOK_COMMENT_COUNT",books.get(position).getNumOfComments());
                startActivity(intent);
            }
        });
        listView.setAdapter(itemsAdapter);
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


    @NonNull
    @Override
    public Loader<List<Book>> onCreateLoader(int id, @Nullable Bundle args) {
        return new BooksLoader(this,categoryId);
    }

    @Override
    public void onLoadFinished(@NonNull androidx.loader.content.Loader<List<Book>> loader, List<Book> books) {
        progressBar.setVisibility(View.GONE);
        emptyStateTextView.setText(R.string.no_books);
        swipeRefreshLayout.setRefreshing(false);
        if(itemsAdapter != null){
            itemsAdapter.clear();
        }

        if(books != null && !books.isEmpty()){
            itemsAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(@NonNull androidx.loader.content.Loader<List<Book>> loader) {
        itemsAdapter.clear();
    }

    private void loadBooks(){
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // 引用 LoaderManager，以便与 loader 进行交互。
            LoaderManager loaderManager = getSupportLoaderManager();

            if (swipeRefreshLayout.isRefreshing()){
                loaderManager.restartLoader(6, null, this);
            }else {
                // 初始化 loader。传递上面定义的整数 ID 常量并为为捆绑
                // 传递 null。为 LoaderCallbacks 参数（由于
                // 此活动实现了 LoaderCallbacks 接口而有效）传递此活动。
                loaderManager.initLoader(6, null, this);
            }
        }
        else{
            progressBar.setVisibility(View.GONE);
            emptyStateTextView.setText(R.string.no_internet);
        }
    }

}
