package com.wedo.studybar.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wedo.studybar.R;
import com.wedo.studybar.util.Book;
import com.wedo.studybar.util.Discussion;

import java.util.ArrayList;
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
        int horizontalBookCover = books.get(position).getBookCoverId();
        String horizontalBookName = books.get(position).getBookName();
        horizontalBookViewHolder.horizontalBookCoverView.setImageResource(horizontalBookCover);
        horizontalBookViewHolder.horizontalBookNameView.setText(horizontalBookName);
    }

    public String getBookName(int position) {
        return books.get(position).getBookName();
    }

    public class horizontalBookViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        ImageView horizontalBookCoverView;
        TextView horizontalBookNameView;

        horizontalBookViewHolder(View itemView){
            super(itemView);
            horizontalBookCoverView = itemView.findViewById(R.id.book_cover);
            horizontalBookNameView = itemView.findViewById(R.id.book_name);
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
