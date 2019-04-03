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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.wedo.studybar.Adapter.CategoryAdapter;
import com.wedo.studybar.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements CategoryAdapter.ItemClickListener{

    private CategoryAdapter mCategoryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home,container,false);

        //data to populate the category RecyclerView with
        ArrayList<String> categoryNames = new ArrayList<>();
        categoryNames.add("Philosophy");
        categoryNames.add("Economics");
        categoryNames.add("Jurisprudence");
        categoryNames.add("Pedagogy");
        categoryNames.add("Literature");
        categoryNames.add("History");
        categoryNames.add("Formal science");
        categoryNames.add("Engineering");
        categoryNames.add("Agronomy");
        categoryNames.add("Medicine");
        categoryNames.add("Military science");
        categoryNames.add("Management");
        categoryNames.add("Art Theory");

        RecyclerView categoryRecyclerView = (RecyclerView) rootView.findViewById(R.id.categories_recycler_view);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        categoryRecyclerView.setLayoutManager(horizontalLayoutManager);
        mCategoryAdapter = new CategoryAdapter(getActivity(),categoryNames);
        mCategoryAdapter.setClickListener(this);
        categoryRecyclerView.setAdapter(mCategoryAdapter);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        //data to populate the category RecyclerView with
        ArrayList<String> categoryNames = new ArrayList<>();
        categoryNames.add("Philosophy");
        categoryNames.add("Economics");
        categoryNames.add("Jurisprudence");
        categoryNames.add("Pedagogy");
        categoryNames.add("Literature");
        categoryNames.add("History");
        categoryNames.add("Formal science");
        categoryNames.add("Engineering");
        categoryNames.add("Agronomy");
        categoryNames.add("Medicine");
        categoryNames.add("Military science");
        categoryNames.add("Management");
        categoryNames.add("Art Theory");

        RecyclerView categoryRecyclerView = (RecyclerView) getActivity().findViewById(R.id.categories_recycler_view);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        categoryRecyclerView.setLayoutManager(horizontalLayoutManager);
        mCategoryAdapter = new CategoryAdapter(getActivity(),categoryNames);
        mCategoryAdapter.setClickListener(this);
        categoryRecyclerView.setAdapter(mCategoryAdapter);
        */

    }



    public HomeFragment(){

    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(getActivity(),mCategoryAdapter.getItem(position),Toast.LENGTH_SHORT).show();
    }
}
