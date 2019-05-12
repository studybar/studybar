package com.wedo.studybar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wedo.studybar.R;
import com.wedo.studybar.activities.DiscussionCommentActivity;
import com.wedo.studybar.activities.DiscussionDetailActivity;
import com.wedo.studybar.util.Discussion;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import katex.hourglass.in.mathlib.MathView;

public class MyCommentAdapter extends ArrayAdapter<Discussion> {

    Context context;

    public MyCommentAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Discussion> comments) {
        super(context, 0, comments);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View commentItemView = convertView;
        if(commentItemView == null){
            commentItemView = LayoutInflater.from((getContext())).inflate(
                    R.layout.my_comment_list_item,parent,false
            );
        }
        final Discussion currentAndroidAdapter = getItem(position);

        TextView commentAuthor = (TextView)commentItemView.findViewById(R.id.my_comment_user_name);
        commentAuthor.setText(currentAndroidAdapter.getDiscussionAuthor());

        MathView discussionContent = (MathView)commentItemView.findViewById(R.id.my_comment_content);
        discussionContent.setDisplayText(currentAndroidAdapter.getDiscussionContent());
        discussionContent.setTextSize(14);
        discussionContent.setClickable(true);

        return commentItemView;
    }
}
