package com.wedo.studybar.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wedo.studybar.R;
import com.wedo.studybar.util.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private ItemClickListener mClickListener;
    private LayoutInflater mInflater;
    private List<Category> categories;

    public CategoryAdapter(Context context, List<Category> categories){
        this.mInflater = LayoutInflater.from(context);
        this.categories = categories;
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
        Integer categoryName = categories.get(position).getCategoryName();
        categoryViewHolder.categoryNameTextView.setText(categoryName);
    }

    @Override
    public int getItemCount() {
        return categories.size();
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

    public Integer getCategoryName(int id){
        return categories.get(id).getCategoryName();
    }

    public String getCategoryId(int id){
        return categories.get(id).getCategoryId();
    }

    public void setClickListener(ItemClickListener itemClickListener){
            this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener{
        void onItemClick(View view,int position);
    }
}
