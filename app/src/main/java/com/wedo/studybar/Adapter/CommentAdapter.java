package com.wedo.studybar.Adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import katex.hourglass.in.mathlib.MathView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wedo.studybar.R;
import com.wedo.studybar.activities.DiscussionCommentActivity;
import com.wedo.studybar.util.Discussion;

import java.util.ArrayList;

public class CommentAdapter extends ArrayAdapter<Discussion> {

    Context context;
    String topicId;

    public CommentAdapter(Activity context, ArrayList<Discussion> commentArrayList,String topicId){
        super(context,0,commentArrayList);
        this.context=context;
        this.topicId = topicId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View commentItemView = convertView;
        if(commentItemView == null){
            commentItemView = LayoutInflater.from((getContext())).inflate(
                    R.layout.comment_list_item,parent,false
            );
        }
        final Discussion currentAndroidAdapter = getItem(position);

        TextView commentAuthor = (TextView)commentItemView.findViewById(R.id.comment_user_name);
        commentAuthor.setText(currentAndroidAdapter.getDiscussionAuthor());

        MathView discussionContent = (MathView)commentItemView.findViewById(R.id.comment_content);
        discussionContent.setDisplayText(currentAndroidAdapter.getDiscussionContent());
        discussionContent.setTextSize(14);

        //TextView discussionNumOfLikes = (TextView)commentItemView.findViewById(R.id.comment_num_likes);
        //discussionNumOfLikes.setText(currentAndroidAdapter.getNumOfLikes());

        ImageButton commentButton = commentItemView.findViewById(R.id.comment_reply_button);
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DiscussionCommentActivity.class);
                intent.putExtra("DISCUSSION_COMMENT",false);
                intent.putExtra("DISCUSSION_ID",topicId);
                intent.putExtra("COMMENT_FLOOR",currentAndroidAdapter.getCommentFloor());
                intent.putExtra("COMMENT_AUTHOR",currentAndroidAdapter.getDiscussionAuthor());
                intent.putExtra("COMMENT_CONTENT",currentAndroidAdapter.getDiscussionContent());
                context.startActivity(intent);
            }
        });

        TextView discussionNumOfComments = (TextView)commentItemView.findViewById(R.id.comment_num_replies);
        discussionNumOfComments.setText(currentAndroidAdapter.getNumOfComments());

        return commentItemView;
    }
}
