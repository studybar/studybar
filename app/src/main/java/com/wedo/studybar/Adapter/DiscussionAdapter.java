package com.wedo.studybar.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wedo.studybar.R;
import com.wedo.studybar.util.Discussion;

import java.util.ArrayList;

public class DiscussionAdapter extends ArrayAdapter<Discussion> {

    public DiscussionAdapter(Activity context, ArrayList<Discussion> discussionArrayList){
        super(context,0,discussionArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View discussionItemView = convertView;
        if(discussionItemView == null){
            discussionItemView = LayoutInflater.from((getContext())).inflate(
                    R.layout.discussion_fragment_list_item,parent,false
            );
        }
        Discussion currentAndroidAdapter = getItem(position);

        TextView discussionAuthor = (TextView)discussionItemView.findViewById(R.id.discussion_author);
        discussionAuthor.setText(currentAndroidAdapter.getDiscussionAuthor());

        TextView discussionTitle = (TextView)discussionItemView.findViewById(R.id.discussion_title);
        discussionTitle.setText(currentAndroidAdapter.getDiscussionTitle());

        TextView discussionContent = (TextView)discussionItemView.findViewById(R.id.discussion_content);
        discussionContent.setText(currentAndroidAdapter.getDiscussionContent());

        TextView discussionNumOfLikes = (TextView)discussionItemView.findViewById(R.id.discussion_num_of_likes);
        discussionNumOfLikes.setText(currentAndroidAdapter.getNumOfLikes());

        TextView discussionNumOfComments = (TextView)discussionItemView.findViewById(R.id.discussion_num_of_comments);
        discussionNumOfComments.setText(currentAndroidAdapter.getNumOfComments());

        return discussionItemView;
    }
}
