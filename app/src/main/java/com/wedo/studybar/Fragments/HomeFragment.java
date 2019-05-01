package com.wedo.studybar.Fragments;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wedo.studybar.Adapter.CategoryAdapter;
import com.wedo.studybar.Adapter.HorizontalBookAdapter;
import com.wedo.studybar.R;
import com.wedo.studybar.activities.BookDetailActivity;
import com.wedo.studybar.activities.CategoryDetailActivity;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private CategoryAdapter mCategoryAdapter;
    private HorizontalBookAdapter mHorizontalBookAdapter;
    private HorizontalBookAdapter mHorizontalBookAdapter_for_one;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home,container,false);

        swipeRefreshLayout = rootView.findViewById(R.id.home_refresh_layout);
        /**
         * to show category
         * */
        ArrayList<String> categoryIds = new ArrayList<>();
        categoryIds.add("1");
        categoryIds.add("2");
        categoryIds.add("3");
        categoryIds.add("4");
        categoryIds.add("5");
        categoryIds.add("6");
        categoryIds.add("7");
        categoryIds.add("8");
        categoryIds.add("9");
        categoryIds.add("10");


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
        categoryNames.add(R.string.Medicine);
        categoryNames.add(R.string.Management);


        RecyclerView categoryRecyclerView = (RecyclerView) rootView.findViewById(R.id.categories_recycler_view);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        categoryRecyclerView.setLayoutManager(horizontalLayoutManager);
        mCategoryAdapter = new CategoryAdapter(getActivity(),categoryIds,categoryNames);
        mCategoryAdapter.setClickListener(new CategoryAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), CategoryDetailActivity.class);
                intent.putExtra("CATEGORY_ID",mCategoryAdapter.getCategoryId(position));
                intent.putExtra("CATEGORY_NAME",mCategoryAdapter.getCategoryName(position));
                startActivity(intent);
            }
        });
        categoryRecyclerView.setAdapter(mCategoryAdapter);

        /**
         * to show books
         * */
        ArrayList<String> horizontalBookIds = new ArrayList<>();
        horizontalBookIds.add("8378001");
        horizontalBookIds.add("8378002");
        horizontalBookIds.add("8378003");
        horizontalBookIds.add("8378004");
        horizontalBookIds.add("8378005");
        horizontalBookIds.add("8378006");

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

        RecyclerView horizontalBooksRecyclerView = rootView.findViewById(R.id.top_books_recycler_view);
        LinearLayoutManager horizontalLayoutManager_forTop = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        horizontalBooksRecyclerView.setLayoutManager(horizontalLayoutManager_forTop);
        mHorizontalBookAdapter = new HorizontalBookAdapter(getActivity(),horizontalBookIds,horizontalBookCovers,horizontalBookNames);
        mHorizontalBookAdapter.setClickListener(new HorizontalBookAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                intent.putExtra("BOOK_ID",mHorizontalBookAdapter.getBookId(position));
                intent.putExtra("BOOK_NAME",mHorizontalBookAdapter.getBookName(position));

                startActivity(intent);
            }
        });
        horizontalBooksRecyclerView.setAdapter(mHorizontalBookAdapter);

        RecyclerView bookRecommendationOne = rootView.findViewById(R.id.recommendation_one_recycler_view);
        LinearLayoutManager horizontalLayoutManager_for_One = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        bookRecommendationOne.setLayoutManager(horizontalLayoutManager_for_One);
        mHorizontalBookAdapter_for_one = new HorizontalBookAdapter(getActivity(),horizontalBookIds,horizontalBookCovers,horizontalBookNames);
        mHorizontalBookAdapter_for_one.setClickListener(new HorizontalBookAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                intent.putExtra("BOOK_ID",mHorizontalBookAdapter.getBookId(position));
                intent.putExtra("BOOK_NAME",mHorizontalBookAdapter.getBookName(position));

                startActivity(intent);
            }
        });
        bookRecommendationOne.setAdapter(mHorizontalBookAdapter_for_one);

        Resources resources = getContext().getResources();
        int sixteenDp = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                16,
                resources.getDisplayMetrics()
        );
        int eightDp = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                8,
                resources.getDisplayMetrics()
        );

        LinearLayout.LayoutParams paramsTextView = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        paramsTextView.setMargins(sixteenDp,sixteenDp,sixteenDp,sixteenDp);

        LinearLayout.LayoutParams paramsRecyclerView = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        paramsRecyclerView.setMargins(sixteenDp,0,0,0);

        LinearLayout homeLayout = (LinearLayout)rootView.findViewById(R.id.home_layout);

        TextView bookRecommendationEconomics = new TextView(getActivity());
            bookRecommendationEconomics.setText(R.string.Economics);
            bookRecommendationEconomics.setTextAppearance(R.style.TextAppearance_AppCompat_Large);
            bookRecommendationEconomics.setTypeface(Typeface.DEFAULT_BOLD);
            bookRecommendationEconomics.setPadding(eightDp,0,0,0);
            bookRecommendationEconomics.setLayoutParams(paramsTextView);

        RecyclerView bookRecommendationEconomics_RecyclerView = new RecyclerView(getActivity());
            LinearLayoutManager horizontalLayoutManager_forEconomics = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
            bookRecommendationEconomics_RecyclerView.setLayoutManager(horizontalLayoutManager_forEconomics);
            bookRecommendationEconomics_RecyclerView.setAdapter(mHorizontalBookAdapter);
            bookRecommendationEconomics_RecyclerView.setLayoutParams(paramsRecyclerView);


        homeLayout.addView(bookRecommendationEconomics);
        homeLayout.addView(bookRecommendationEconomics_RecyclerView);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(true);
                }

                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    public HomeFragment(){

    }

}
