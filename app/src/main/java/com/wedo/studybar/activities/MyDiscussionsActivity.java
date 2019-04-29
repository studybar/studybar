package com.wedo.studybar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wedo.studybar.Adapter.DiscussionAdapter;
import com.wedo.studybar.R;
import com.wedo.studybar.util.Discussion;
import com.wedo.studybar.util.QueryUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MyDiscussionsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_discussions);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView listView = findViewById(R.id.my_discussion_list);

        String json = getIntent().getStringExtra("DISCUSS");

        final ArrayList<Discussion> discussions = (ArrayList<Discussion>) QueryUtils.extractTopicsFromJson(json);

        DiscussionAdapter discussionAdapter = new DiscussionAdapter(this,discussions);

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
}
