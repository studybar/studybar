package com.wedo.studybar.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wedo.studybar.R;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<String> mCategoryNames;
    private ItemClickListener mClickListener;
    private LayoutInflater mInflater;

    public CategoryAdapter(Context context,List<String> categoryNames){
        this.mInflater = LayoutInflater.from(context);
        this.mCategoryNames = categoryNames;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = mInflater.inflate(
                R.layout.category_list_item,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int position) {
        String categoryNames = mCategoryNames.get(position);
        categoryViewHolder.categoryNameTextView.setText(categoryNames);
    }

    @Override
    public int getItemCount() {
        return mCategoryNames.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
         TextView categoryNameTextView;

         CategoryViewHolder(View itemView){
            super(itemView);
            categoryNameTextView = itemView.findViewById(R.id.category_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mClickListener != null)
                mClickListener.onItemClick(v,getAdapterPosition());
        }
    }

    public String getItem(int id){
        return mCategoryNames.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener){
            this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener{
        void onItemClick(View view,int position);
    }
}
