package com.wedo.studybar.Adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import katex.hourglass.in.mathlib.MathView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wedo.studybar.R;
import com.wedo.studybar.util.Discussion;

import java.util.ArrayList;

public class CommentAdapter extends ArrayAdapter<Discussion> {
    public CommentAdapter(Activity context, ArrayList<Discussion> commentArrayList){
        super(context,0,commentArrayList);
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
        Discussion currentAndroidAdapter = getItem(position);

        TextView commentAuthor = (TextView)commentItemView.findViewById(R.id.comment_user_name);
        commentAuthor.setText(currentAndroidAdapter.getDiscussionAuthor());

        MathView discussionContent = (MathView)commentItemView.findViewById(R.id.comment_content);
        discussionContent.setDisplayText(currentAndroidAdapter.getDiscussionContent());
        discussionContent.setTextSize(14);

        TextView discussionNumOfLikes = (TextView)commentItemView.findViewById(R.id.comment_num_likes);
        discussionNumOfLikes.setText(currentAndroidAdapter.getNumOfLikes());

        TextView discussionNumOfComments = (TextView)commentItemView.findViewById(R.id.comment_num_replies);
        discussionNumOfComments.setText(currentAndroidAdapter.getNumOfComments());

        return commentItemView;
    }
}
