package com.wedo.studybar.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wedo.studybar.R;
import com.wedo.studybar.util.Book;

import java.util.List;

public class HorizontalBookAdapter extends RecyclerView.Adapter<HorizontalBookAdapter.horizontalBookViewHolder> {

    private List<Book> books;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public HorizontalBookAdapter(Context context, List<Book> books){
        this.mInflater = LayoutInflater.from(context);
        this.books = books;
    }

    @NonNull
    @Override
    public horizontalBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.horizontal_list_book,parent,false);
        return  new horizontalBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalBookAdapter.horizontalBookViewHolder horizontalBookViewHolder, int position) {
        String horizontalBookId = books.get(position).getBookId();
        //int horizontalBookCover = books.get(position).getBookCoverId();
        String horizontalBookCover = books.get(position).getBookCoverId();
        String horizontalBookName = books.get(position).getBookName();
        String horizontalBookAuthor = books.get(position).getBookAuthor();
        //horizontalBookViewHolder.horizontalBookCoverView.setImageResource(horizontalBookCover);
        byte[] coverBytesArray = Base64.decode(books.get(position).getBookCoverId(),Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(coverBytesArray,0,coverBytesArray.length);
        horizontalBookViewHolder.horizontalBookCoverView.setImageBitmap(bitmap);
        horizontalBookViewHolder.horizontalBookNameView.setText(horizontalBookName);
        horizontalBookViewHolder.horizontalBookAuthorView.setText(horizontalBookAuthor);
    }

    public String getBookName(int position) {
        return books.get(position).getBookName();
    }

    public class horizontalBookViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        ImageView horizontalBookCoverView;
        TextView horizontalBookNameView;
        TextView horizontalBookAuthorView;

        horizontalBookViewHolder(View itemView){
            super(itemView);
            horizontalBookCoverView = itemView.findViewById(R.id.book_cover);
            horizontalBookNameView = itemView.findViewById(R.id.book_name);
            horizontalBookAuthorView = itemView.findViewById(R.id.book_author);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mClickListener != null)
                mClickListener.onItemClick(view,getAdapterPosition());
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public String getBookId(int id){
        return books.get(id).getBookId();
    }

    public String getBookCover(int id){
        return books.get(id).getBookCoverId();
    }

    public String getBookCountNum(int id){
        return books.get(id).getNumOfComments();
    }

    public void setClickListener(ItemClickListener itemClickListener){
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void clear(){
        int size = this.books.size();
        books.clear();
        this.notifyItemRangeRemoved(0,size);
    }

    public void addAll(List<Book> books){
        this.books=books;
        this.notifyDataSetChanged();
    }
}
