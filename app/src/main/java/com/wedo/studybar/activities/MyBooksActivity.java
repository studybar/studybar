package com.wedo.studybar.activities;

import android.os.Bundle;
import android.view.MenuItem;

import com.wedo.studybar.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MyBooksActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybooks);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
}
