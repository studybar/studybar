package com.wedo.studybar.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wedo.studybar.Adapter.CommentAdapter;
import com.wedo.studybar.Adapter.DiscussionAdapter;
import com.wedo.studybar.R;
import com.wedo.studybar.util.Discussion;

import java.util.ArrayList;

public class DiscussionDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_detail);
        String discussionTitle = getIntent().getStringExtra("DISCUSSION_TITLE");

        final ArrayList<Discussion> comments = new ArrayList<Discussion>();

        comments.add(new Discussion("26663680001",getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
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
        ListView listView = (ListView)findViewById(R.id.comments_of_discussion);

        LayoutInflater mInflater = getLayoutInflater();
        ViewGroup bookHeader = (ViewGroup)mInflater.inflate(R.layout.comment_topic_header_view,listView,false);
        listView.addHeaderView(bookHeader);

        listView.setAdapter(itemsAdapter);

        //Discussion topic = new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the title of this discussion.Here is the title of this discussion.","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","64");
        TextView commentTopicAuthor = (TextView)findViewById(R.id.comment_topic_author);
        TextView commentTopicTitle = (TextView)findViewById(R.id.comment_topic_title);
        TextView commentTopicContent = (TextView)findViewById(R.id.comment_topic_content);
        TextView commentTopicNumOfLikes = (TextView)findViewById(R.id.comment_topic_num_of_likes);
        TextView commentTopicNumOfComments = (TextView)findViewById(R.id.comment_topic_num_of_comments);

        commentTopicAuthor.setText(getString(R.string.discussion_author_pre)+"nobody");
        commentTopicTitle.setText("Here is the title of this discussion.Here is the title of this discussion.");
        commentTopicContent.setText("Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.");
        commentTopicNumOfComments.setText("89");
        commentTopicNumOfLikes.setText("64");

        FloatingActionButton fab = findViewById(R.id.comment_floating_action_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"test",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.discussion_menu,menu);
        return true;
    }
}
