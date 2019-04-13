package com.wedo.studybar.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

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

        comments.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.","64","89"));
        comments.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));
        comments.add(new Discussion(getString(R.string.discussion_author_pre)+"nobody","Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.Here is the content of this discussion.","64","89"));

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
