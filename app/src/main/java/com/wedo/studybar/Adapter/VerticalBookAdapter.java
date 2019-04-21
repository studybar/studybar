package com.wedo.studybar.Adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.wedo.studybar.R;
import com.wedo.studybar.util.Book;

import java.util.ArrayList;

public class VerticalBookAdapter extends ArrayAdapter<Book> {

    public VerticalBookAdapter(Activity context, ArrayList<Book> bookArrayList){
        super(context, 0, bookArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View bookItemView = convertView;
        if(bookItemView == null){
            bookItemView = LayoutInflater.from((getContext())).inflate(
                    R.layout.vertical_list_book_item,parent,false
            );
        }
        Book  currentAndroidAdapter = getItem(position);

        ImageView verticalBookCover = (ImageView)bookItemView.findViewById(R.id.book_search_cover);
        verticalBookCover.setImageResource(currentAndroidAdapter.getBookCoverId());

        TextView verticalBookTitle = (TextView)bookItemView.findViewById(R.id.book_search_title);
        verticalBookTitle.setText(currentAndroidAdapter.getBookName());

        TextView verticalBookAuthor = (TextView)bookItemView.findViewById(R.id.book_search_author);
        verticalBookAuthor.setText(currentAndroidAdapter.getBookAuthor());

        TextView verticalBookPublisher = (TextView)bookItemView.findViewById(R.id.book_press);
        verticalBookPublisher.setText(currentAndroidAdapter.getBookPress());

        TextView verticalBookLikes = (TextView)bookItemView.findViewById(R.id.search_num_of_likes);
        verticalBookLikes.setText(currentAndroidAdapter.getNumOfLikes());

        TextView verticalBookComments = (TextView)bookItemView.findViewById(R.id.search_num_of_discuss);
        verticalBookComments.setText(currentAndroidAdapter.getmNumOfComments());


        ToggleButton followButton = (ToggleButton)bookItemView.findViewById(R.id.book_follow_button);
        followButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(getContext(),R.string.followed,Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),R.string.unfollowed,Toast.LENGTH_SHORT).show();
                }
            }
        });
        return bookItemView;
    }
}
