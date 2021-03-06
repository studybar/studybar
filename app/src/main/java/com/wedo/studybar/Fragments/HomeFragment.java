package com.wedo.studybar.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wedo.studybar.Adapter.CategoryAdapter;
import com.wedo.studybar.Adapter.HorizontalBookAdapter;
import com.wedo.studybar.R;
import com.wedo.studybar.activities.BookDetailActivity;
import com.wedo.studybar.activities.CategoryDetailActivity;
import com.wedo.studybar.loader.BooksLoader;
import com.wedo.studybar.util.Book;
import com.wedo.studybar.util.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class HomeFragment extends Fragment implements androidx.loader.app.LoaderManager.LoaderCallbacks<List<Book>>{

    private CategoryAdapter mCategoryAdapter;
    private HorizontalBookAdapter horizontalBookAdapter_one;
    private HorizontalBookAdapter horizontalBookAdapter_two;
    private HorizontalBookAdapter horizontalBookAdapter_three;

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView categoryRecyclerView;
    private ProgressBar progressBar;
    private TextView emptyStateTextView;
    private LinearLayout linearLayout;
    private TextView row_one;
    private RecyclerView recyclerViewOne;
    private TextView row_two;
    private RecyclerView recyclerViewTwo;
    private TextView row_three;
    private RecyclerView recyclerViewThree;

    private int category_one;
    private int category_two;
    private int category_three;

    private String id_one;
    private String id_two;
    private String id_three;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home,container,false);

        swipeRefreshLayout = rootView.findViewById(R.id.home_refresh_layout);
        categoryRecyclerView = rootView.findViewById(R.id.categories_recycler_view);
        progressBar = rootView.findViewById(R.id.home_load_progress);
        emptyStateTextView = rootView.findViewById(R.id.home_empty_view);
        linearLayout = rootView.findViewById(R.id.home_books);

        row_one = rootView.findViewById(R.id.book_row_one);
        row_two = rootView.findViewById(R.id.book_row_two);
        row_three = rootView.findViewById(R.id.book_row_three);
        recyclerViewOne = rootView.findViewById(R.id.books_recycler_view_one);
        recyclerViewTwo = rootView.findViewById(R.id.books_recycler_view_two);
        recyclerViewThree = rootView.findViewById(R.id.books_recycler_view_three);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(true);
                }
                progressBar.setVisibility(View.GONE);
                emptyStateTextView.setVisibility(View.GONE);
                linearLayout.setVisibility(View.GONE);
                loadHomeBooks();
            }
        });
        /**
         * to show category
         * */
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category("1",R.string.Philosophy));
        categories.add(new Category("2",R.string.Economics));
        categories.add(new Category("3",R.string.Jurisprudence));
        categories.add(new Category("4",R.string.Pedagogy));
        categories.add(new Category("5",R.string.Literature));
        categories.add(new Category("6",R.string.History));
        categories.add(new Category("7",R.string.formalScience));
        categories.add(new Category("8",R.string.Engineering));
        categories.add(new Category("9",R.string.Medicine));
        categories.add(new Category("10",R.string.Management));

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        categoryRecyclerView.setLayoutManager(horizontalLayoutManager);
        mCategoryAdapter = new CategoryAdapter(getActivity(),categories);
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

        loadHomeBooks();

        final ArrayList<Book> books = new ArrayList<>();
        horizontalBookAdapter_one = new HorizontalBookAdapter(getActivity(),books);
        horizontalBookAdapter_one.setClickListener(new HorizontalBookAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                intent.putExtra("BOOK_ID", horizontalBookAdapter_one.getBookId(position));
                intent.putExtra("BOOK_NAME", horizontalBookAdapter_one.getBookName(position));
                intent.putExtra("BOOK_COVER", horizontalBookAdapter_one.getCoverUrl(position));
                intent.putExtra("BOOK_COMMENT_COUNT", horizontalBookAdapter_one.getBookCountNum(position));
                startActivity(intent);
            }
        });
        LinearLayoutManager linearLayoutManager_one = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewOne.setLayoutManager(linearLayoutManager_one);
        recyclerViewOne.setAdapter(horizontalBookAdapter_one);

        horizontalBookAdapter_two = new HorizontalBookAdapter(getActivity(),books);
        horizontalBookAdapter_two.setClickListener(new HorizontalBookAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                intent.putExtra("BOOK_ID", horizontalBookAdapter_two.getBookId(position));
                intent.putExtra("BOOK_NAME", horizontalBookAdapter_two.getBookName(position));
                intent.putExtra("BOOK_COVER", horizontalBookAdapter_two.getCoverUrl(position));
                intent.putExtra("BOOK_COMMENT_COUNT", horizontalBookAdapter_two.getBookCountNum(position));
                startActivity(intent);
            }
        });
        LinearLayoutManager linearLayoutManager_two = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewTwo.setLayoutManager(linearLayoutManager_two);
        recyclerViewTwo.setAdapter(horizontalBookAdapter_two);

        horizontalBookAdapter_three = new HorizontalBookAdapter(getActivity(),books);
        horizontalBookAdapter_three.setClickListener(new HorizontalBookAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), BookDetailActivity.class);
                intent.putExtra("BOOK_ID", horizontalBookAdapter_three.getBookId(position));
                intent.putExtra("BOOK_NAME", horizontalBookAdapter_three.getBookName(position));
                intent.putExtra("BOOK_COVER", horizontalBookAdapter_three.getCoverUrl(position));
                intent.putExtra("BOOK_COMMENT_COUNT", horizontalBookAdapter_three.getBookCountNum(position));
                startActivity(intent);
            }
        });
        LinearLayoutManager linearLayoutManager_three = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewThree.setLayoutManager(linearLayoutManager_three);
        recyclerViewThree.setAdapter(horizontalBookAdapter_three);
        return rootView;
    }

    private void loadHomeBooks() {
        emptyStateTextView.setVisibility(View.GONE);

        int one = new Random().nextInt(10)+1;
        int two = 0,three = 0;
        if (one < 9 ){
            two = one+1;
            three = two+1;
        }else if(one == 9){
            two = one +1;
            three = 1;
        }else if (one == 10){
            two = 1;
            three = 2;
        }


        id_one = String.valueOf(one);
        id_two = String.valueOf(two);
        id_three = String.valueOf(three);

        category_one = matchCategory(one);
        category_two = matchCategory(two);
        category_three = matchCategory(three);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // 引用 LoaderManager，以便与 loader 进行交互。
            LoaderManager loaderManager = getLoaderManager();

            if(swipeRefreshLayout.isRefreshing()){
                loaderManager.restartLoader(1,null,this);
                loaderManager.restartLoader(2,null,this);
                loaderManager.restartLoader(3,null,this);
            }else {
                // 初始化 loader。传递上面定义的整数 ID 常量并为为捆绑
                // 传递 null。为 LoaderCallbacks 参数（由于
                // 此活动实现了 LoaderCallbacks 接口而有效）传递此活动。
                loaderManager.initLoader(1, null, this);
                loaderManager.initLoader(2, null, this);
                loaderManager.initLoader(3, null, this);
            }
        }
        else{
            progressBar.setVisibility(View.GONE);
            emptyStateTextView.setText(R.string.no_internet);
        }
    }

    private int matchCategory(int id){
        int category;
        switch (id){
            case 1: category = R.string.Philosophy; break;
            case 2: category = R.string.Economics;break;
            case 3: category = R.string.Jurisprudence;break;
            case 4: category = R.string.Pedagogy;break;
            case 5: category = R.string.Literature;break;
            case 6: category = R.string.History;break;
            case 7: category = R.string.formalScience;break;
            case 8: category = R.string.Engineering;break;
            case 9: category = R.string.Medicine;break;
            case 10: category = R.string.Management;break;
            default: category = R.string.blank;break;
        }
        return category;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public HomeFragment(){

    }

    @NonNull
    @Override
    public Loader<List<Book>> onCreateLoader(int id, @Nullable Bundle args) {
        if(id==1){
            return new BooksLoader(getActivity(),id_one);
        }
        else if(id==2){
            return new BooksLoader(getActivity(),id_two);
        }
        else if(id==3){
            return new BooksLoader(getActivity(),id_three);
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Book>> loader, List<Book> books) {
        emptyStateTextView.setVisibility(View.GONE);
        row_one.setText(category_one);
        row_two.setText(category_two);
        row_three.setText(category_three);
        linearLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);


        int id = loader.getId();
        if(id == 1){
            if(horizontalBookAdapter_one!=null){
                horizontalBookAdapter_one.clear();
            }
            if(books != null && !books.isEmpty()){
                horizontalBookAdapter_one.addAll(books);
                horizontalBookAdapter_one.notifyDataSetChanged();
            }
        }
        else if(id == 2){
            if(horizontalBookAdapter_two!=null){
                horizontalBookAdapter_two.clear();
            }
            if(books != null && !books.isEmpty()){
                horizontalBookAdapter_two.addAll(books);
                horizontalBookAdapter_two.notifyDataSetChanged();
            }
        }
        else if(id == 3){
            if(horizontalBookAdapter_three!=null){
                horizontalBookAdapter_three.clear();
            }
            if(books != null && !books.isEmpty()){
                horizontalBookAdapter_three.addAll(books);
                horizontalBookAdapter_three.notifyDataSetChanged();
            }
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Book>> loader) {
        int id = loader.getId();
        if(id == 1){
            horizontalBookAdapter_one.clear();
        }
        else if(id == 2){

            horizontalBookAdapter_two.clear();
        }
        else if(id == 3){
            horizontalBookAdapter_three.clear();
        }
    }
}
