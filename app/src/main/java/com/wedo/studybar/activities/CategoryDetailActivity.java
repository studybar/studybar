package com.wedo.studybar.activities;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.Loader;
import androidx.loader.app.LoaderManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.wedo.studybar.Adapter.VerticalBookAdapter;
import com.wedo.studybar.R;
import com.wedo.studybar.util.Book;
import com.wedo.studybar.util.BooksLoader;

import java.util.ArrayList;
import java.util.List;

public class CategoryDetailActivity extends AppCompatActivity implements androidx.loader.app.LoaderManager.LoaderCallbacks<List<Book>> {

    private SwipeRefreshLayout swipeRefreshLayout;
    String category;
    private VerticalBookAdapter itemsAdapter;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Integer categoryName = getIntent().getIntExtra("CATEGORY_NAME",R.string.blank);
        this.setTitle(categoryName);

        swipeRefreshLayout = findViewById(R.id.category_detail_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(true);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        String categoryId = getIntent().getStringExtra("CATEGORY_ID");
        category = "";
        switch (categoryId){
            case "1": category = "哲学";break;
            case "2": category = "经济学";break;
            case "3": category = "法学";break;
            case "4": category = "教育学";break;
            case "5": category = "文学";break;
            case "6": category = "历史学";break;
            case "7": category = "理学";break;
            case "8": category = "工学";break;
            case "9": category = "医学";break;
            case "10": category = "管理学";break;
        }

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
            // TODO: 提示 no internet
        }
        /*
        final ArrayList<Book> books = new ArrayList<Book>();

        books.add(new Book("8378001","习近平谈治国理政","习近平",R.drawable.test,"外文出版社","89","64"));
        books.add(new Book("8378002","习近平谈治国理政","习近平",R.drawable.test,"外文出版社","89","64"));
        books.add(new Book("8378003","习近平谈治国理政","习近平",R.drawable.test,"外文出版社","89","64"));
        books.add(new Book("8378004","习近平谈治国理政","习近平",R.drawable.test,"外文出版社","89","64"));
        books.add(new Book("8378005","习近平谈治国理政","习近平",R.drawable.test,"外文出版社","89","64"));
        books.add(new Book("8378006","习近平谈治国理政","习近平",R.drawable.test,"外文出版社","89","64"));
        books.add(new Book("8378007","习近平谈治国理政","习近平",R.drawable.test,"外文出版社","89","64"));
        books.add(new Book("8378008","习近平谈治国理政","习近平",R.drawable.test,"外文出版社","89","64"));
        books.add(new Book("8378009","习近平谈治国理政","习近平",R.drawable.test,"外文出版社","89","64"));
        */

        final ArrayList<Book> books = new ArrayList<>();
        itemsAdapter = new VerticalBookAdapter(this,books);
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),BookDetailActivity.class);
                intent.putExtra("BOOK_ID",books.get(position).getBookId());
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
        return new BooksLoader(this,category);
    }

    @Override
    public void onLoadFinished(@NonNull androidx.loader.content.Loader<List<Book>> loader, List<Book> books) {
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

}
