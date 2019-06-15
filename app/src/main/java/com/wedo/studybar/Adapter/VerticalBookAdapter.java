package com.wedo.studybar.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wedo.studybar.R;
import com.wedo.studybar.util.Book;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class VerticalBookAdapter extends ArrayAdapter<Book> {

    private String coverUrl = "http://39.97.181.175/study/image/";

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

        ImageView verticalBookCover = bookItemView.findViewById(R.id.book_search_cover);
        //byte[] coverBytesArray = currentAndroidAdapter.getBookCoverId();
        //Bitmap bitmap = BitmapFactory.decodeByteArray(coverBytesArray,0,coverBytesArray.length);
        //verticalBookCover.setImageBitmap(bitmap);
        Glide.with(parent)
                .load(coverUrl + currentAndroidAdapter.getCoverUrl())
                .into(verticalBookCover);

        TextView verticalBookTitle = (TextView)bookItemView.findViewById(R.id.book_search_title);
        verticalBookTitle.setText(currentAndroidAdapter.getBookName());

        TextView verticalBookPress = bookItemView.findViewById(R.id.book_press);
        verticalBookPress.setText(currentAndroidAdapter.getBookPublisher());

        TextView verticalBookAuthor = bookItemView.findViewById(R.id.book_search_author);
        verticalBookAuthor.setText(currentAndroidAdapter.getBookAuthor());

        TextView verticalBookComments = (TextView)bookItemView.findViewById(R.id.search_num_of_discuss);
        verticalBookComments.setText(currentAndroidAdapter.getNumOfComments());

        return bookItemView;
    }
}
