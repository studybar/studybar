package com.wedo.studybar.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.wedo.studybar.R;

public class BookDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_deatil_activity);

        String bookId = getIntent().getStringExtra("bookId");
        Toast.makeText(this,bookId,Toast.LENGTH_SHORT).show();
    }
}
