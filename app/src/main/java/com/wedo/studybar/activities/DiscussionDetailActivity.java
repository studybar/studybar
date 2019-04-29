package com.wedo.studybar.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wedo.studybar.Adapter.CommentAdapter;
import com.wedo.studybar.R;
import com.wedo.studybar.util.Discussion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DiscussionDetailActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_detail);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        swipeRefreshLayout = findViewById(R.id.discussion_detail_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(true);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        ListView listView = (ListView)findViewById(R.id.comments_of_discussion);

        String discussionId = getIntent().getStringExtra("DISCUSSION_ID");
        String discussionAuthor = getIntent().getStringExtra("DISCUSSION_AUTHOR");
        String discussionTitle = getIntent().getStringExtra("DISCUSSION_TITLE");
        String discussionContent = getIntent().getStringExtra("DISCUSSION_CONTENT");
        String discussionLikesNum = getIntent().getStringExtra("DISCUSSION_LIKES_NUM");
        String discussionCommentsNum = getIntent().getStringExtra("DISCUSSION_COMMENTS_NUM");

        String commentJson = getIntent().getStringExtra("COMMENT_JSON");

        try {
            JSONArray commentsArray = new JSONArray(commentJson);

        }catch (Exception e){
            e.printStackTrace();
        }

        /*
        final ArrayList<Discussion> comments = new ArrayList<Discussion>();

        comments.add(new Discussion("26663680001",getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.$x=\\frac{1+y}{1+2z^2}$.$\\vec{F}=\\frac{d\\vec{p}}{dt}=m\\frac{d\\vec{v}}{dt}=m\\vec{a}$Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion("26663680002",getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion("26663680003",getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.","64","89"));
        comments.add(new Discussion("26663680004",getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion("26663680005",getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion("26663680006",getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion("26663680007",getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion("26663680008",getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion("26663680009",getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion("26663680010",getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion("26663680011",getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion("26663680012",getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion("26663680013",getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion("26663680014",getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion("26663680015",getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion("26663680016",getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion("26663680017",getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion("26663680018",getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion("26663680019",getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));

        CommentAdapter itemsAdapter = new CommentAdapter(this,comments);
        */


        LayoutInflater mInflater = getLayoutInflater();
        ViewGroup bookHeader = (ViewGroup)mInflater.inflate(R.layout.comment_topic_header_view,listView,false);
        listView.addHeaderView(bookHeader);

        //listView.setAdapter(itemsAdapter);

        //Discussion topic = new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the title of this discussion.Here is the title of this discussion.","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","64");
        TextView commentTopicAuthor = (TextView)findViewById(R.id.comment_topic_author);
        TextView commentTopicTitle = (TextView)findViewById(R.id.comment_topic_title);
        TextView commentTopicContent = (TextView)findViewById(R.id.comment_topic_content);
        TextView commentTopicNumOfLikes = (TextView)findViewById(R.id.comment_topic_num_of_likes);
        TextView commentTopicNumOfComments = (TextView)findViewById(R.id.comment_topic_num_of_comments);

        commentTopicAuthor.setText(getString(R.string.discussion_author_pre)+discussionAuthor);
        commentTopicTitle.setText(discussionContent);
        commentTopicContent.setText(discussionContent);
        commentTopicNumOfComments.setText(discussionCommentsNum);
        //commentTopicNumOfLikes.setText();


        FloatingActionButton fab = findViewById(R.id.comment_floating_action_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DiscussionCommentActivity.class);
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
            case R.id.discussion_report:
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.discussion_menu,menu);
        return true;
    }
}
