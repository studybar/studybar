package com.wedo.studybar.Adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
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

import org.w3c.dom.Text;

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
        //verticalBookCover.setImageResource(currentAndroidAdapter.getBookCoverId());
        byte[] coverBytesArray = Base64.decode(currentAndroidAdapter.getBookCoverId(),Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(coverBytesArray,0,coverBytesArray.length);
        verticalBookCover.setImageBitmap(bitmap);

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
