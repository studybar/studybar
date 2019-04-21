package com.wedo.studybar.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.wedo.studybar.R;

public class BookDetailActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;

    private String bookId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_deatil);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        swipeRefreshLayout = findViewById(R.id.discussion_books_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(true);
                }

                swipeRefreshLayout.setRefreshing(false);
            }
        });
        bookId = getIntent().getStringExtra("BOOK_ID");
        Toast.makeText(this,bookId,Toast.LENGTH_SHORT).show();

        FloatingActionButton fab = findViewById(R.id.button_book_comment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),BookCommentActivity.class);
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
            case R.id.correct_mistakes:
                new AlertDialog.Builder(this)
                        .setTitle(R.string.pick_mistake_item)
                        .setItems(R.array.mistakes_list, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getApplicationContext(),AddBookActivity.class);
                                intent.putExtra("BOOK_ID",bookId);
                                //todo:pass book item

                                switch (which){
                                    case 0:
                                        intent.putExtra("MISTAKE_ITEM","COVER");
                                        startActivity(intent);
                                        break;
                                    case 1:
                                        intent.putExtra("MISTAKE_ITEM","TITLE");
                                        startActivity(intent);
                                        break;
                                    case 2:
                                        intent.putExtra("MISTAKE_ITEM","AUTHOR");
                                        startActivity(intent);
                                        break;
                                    case 3:
                                        intent.putExtra("MISTAKE_ITEM","PUBLISHER");
                                        startActivity(intent);
                                        break;
                                    case 4:
                                        intent.putExtra("MISTAKE_ITEM","CATEGORY");
                                        startActivity(intent);
                                        break;                                }
                            }
                        })
                        .show();
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
}
