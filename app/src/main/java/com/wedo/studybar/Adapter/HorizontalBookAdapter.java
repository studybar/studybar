package com.wedo.studybar.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wedo.studybar.R;

import java.util.List;

public class HorizontalBookAdapter extends RecyclerView.Adapter<HorizontalBookAdapter.horizontalBookViewHolder> {

    private List<String> mHorizontalBookIds;
    private List<String> mHorizontalBookNames;
    private List<String> mHorizontalBookAuthors;
    private List<Integer> mHorizontalBookCovers;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public HorizontalBookAdapter(Context context,List<String> horizontalBookIds,List<Integer> horizontalBookCovers, List<String> horizontalBookNames, List<String> horizontalBookAuthors){
        this.mInflater = LayoutInflater.from(context);
        this.mHorizontalBookIds = horizontalBookIds;
        this.mHorizontalBookCovers = horizontalBookCovers;
        this.mHorizontalBookNames = horizontalBookNames;
        this.mHorizontalBookAuthors = horizontalBookAuthors;
    }

    @NonNull
    @Override
    public horizontalBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.horizontal_list_book,parent,false);
        return  new horizontalBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalBookAdapter.horizontalBookViewHolder horizontalBookViewHolder, int position) {
        String horizontalBookId = mHorizontalBookIds.get(position);
        int horizontalBookCover = mHorizontalBookCovers.get(position);
        String horizontalBookName = mHorizontalBookNames.get(position);
        String horizontalBookAuthor = mHorizontalBookAuthors.get(position);
        horizontalBookViewHolder.horizontalBookCoverView.setImageResource(horizontalBookCover);
        horizontalBookViewHolder.horizontalBookNameView.setText(horizontalBookName);
        horizontalBookViewHolder.horizontalBookAuthorView.setText(horizontalBookAuthor);
    }

    public String getBookName(int position) {
        return mHorizontalBookNames.get(position);
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
        return mHorizontalBookNames.size();
    }

    public String getBookId(int id){
        return mHorizontalBookIds.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener){
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
