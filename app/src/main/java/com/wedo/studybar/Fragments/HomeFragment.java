package com.wedo.studybar.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wedo.studybar.Adapter.CategoryAdapter;
import com.wedo.studybar.Adapter.HorizontalBookAdapter;
import com.wedo.studybar.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private CategoryAdapter mCategoryAdapter;
    private HorizontalBookAdapter mHorizontalBookAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home,container,false);

        /**
         * to show category
         * */
        //data to populate the category RecyclerView with
        ArrayList<Integer> categoryNames = new ArrayList<>();
        categoryNames.add(R.string.Philosophy);
        categoryNames.add(R.string.Economics);
        categoryNames.add(R.string.Jurisprudence);
        categoryNames.add(R.string.Pedagogy);
        categoryNames.add(R.string.Literature);
        categoryNames.add(R.string.History);
        categoryNames.add(R.string.formalScience);
        categoryNames.add(R.string.Engineering);
        categoryNames.add(R.string.Agronomy);
        categoryNames.add(R.string.Medicine);
        categoryNames.add(R.string.militaryScience);
        categoryNames.add(R.string.Management);
        categoryNames.add(R.string.artTheory);

        RecyclerView categoryRecyclerView = (RecyclerView) rootView.findViewById(R.id.categories_recycler_view);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        categoryRecyclerView.setLayoutManager(horizontalLayoutManager);
        mCategoryAdapter = new CategoryAdapter(getActivity(),categoryNames);
        mCategoryAdapter.setClickListener(new CategoryAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(),mCategoryAdapter.getItem(position),Toast.LENGTH_SHORT).show();
            }
        });
        categoryRecyclerView.setAdapter(mCategoryAdapter);

        /**
         * to show books
         * */
        ArrayList<Integer> horizontalBookCovers = new ArrayList<>();
        horizontalBookCovers.add(R.drawable.test);
        horizontalBookCovers.add(R.drawable.test);
        horizontalBookCovers.add(R.drawable.test);
        horizontalBookCovers.add(R.drawable.test);
        horizontalBookCovers.add(R.drawable.test);
        horizontalBookCovers.add(R.drawable.test);

        ArrayList<String> horizontalBookNames = new ArrayList<>();
        horizontalBookNames.add("习近平谈治国理政");
        horizontalBookNames.add("习近平谈治国理政");
        horizontalBookNames.add("习近平谈治国理政");
        horizontalBookNames.add("习近平谈治国理政");
        horizontalBookNames.add("习近平谈治国理政");
        horizontalBookNames.add("习近平谈治国理政");

        ArrayList<String> horizontalBookAuthors = new ArrayList<>();
        horizontalBookAuthors.add("习近平");
        horizontalBookAuthors.add("习近平");
        horizontalBookAuthors.add("习近平");
        horizontalBookAuthors.add("习近平");
        horizontalBookAuthors.add("习近平");
        horizontalBookAuthors.add("习近平");

        RecyclerView horizontalBooksRecyclerView = rootView.findViewById(R.id.top_books_recycler_view);
        LinearLayoutManager horizontalLayoutManager_forTop = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        horizontalBooksRecyclerView.setLayoutManager(horizontalLayoutManager_forTop);
        mHorizontalBookAdapter = new HorizontalBookAdapter(getActivity(),horizontalBookCovers,horizontalBookNames,horizontalBookAuthors);
        mHorizontalBookAdapter.setClickListener(new HorizontalBookAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(),mHorizontalBookAdapter.getItem(position),Toast.LENGTH_SHORT).show();
            }
        });
        horizontalBooksRecyclerView.setAdapter(mHorizontalBookAdapter);



        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    public HomeFragment(){

    }

}
