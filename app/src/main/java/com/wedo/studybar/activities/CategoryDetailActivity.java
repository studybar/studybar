package com.wedo.studybar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.wedo.studybar.Adapter.VerticalBookAdapter;
import com.wedo.studybar.R;
import com.wedo.studybar.util.Book;

import java.util.ArrayList;

public class CategoryDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        Integer categoryName = getIntent().getIntExtra("CATEGORY_NAME",R.string.blank);
        this.setTitle(categoryName);

        String categoryId = getIntent().getStringExtra("CATEGORY_ID");
        Toast.makeText(this,categoryId,Toast.LENGTH_SHORT).show();

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

        VerticalBookAdapter itemsAdapter = new VerticalBookAdapter(this,books);
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
}
