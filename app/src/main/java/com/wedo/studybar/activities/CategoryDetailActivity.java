package com.wedo.studybar.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.wedo.studybar.R;

public class CategoryDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        Integer categoryName = getIntent().getIntExtra("CATEGORY_NAME",R.string.blank);
        this.setTitle(categoryName);

        String categoryId = getIntent().getStringExtra("CATEGORY_ID");
        Toast.makeText(this,categoryId,Toast.LENGTH_SHORT).show();
    }
}
