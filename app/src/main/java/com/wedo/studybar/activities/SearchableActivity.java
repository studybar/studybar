package com.wedo.studybar.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wedo.studybar.R;

public class SearchableActivity extends Activity {

    private ListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        listView = (ListView)findViewById(R.id.list_view);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) { }
        });

        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }

    private void doSearch(String query) {
        //Todo: Searching data
    }
}
