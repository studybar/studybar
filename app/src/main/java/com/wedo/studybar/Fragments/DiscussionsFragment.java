package com.wedo.studybar.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wedo.studybar.Adapter.DiscussionAdapter;
import com.wedo.studybar.Adapter.HorizontalBookAdapter;
import com.wedo.studybar.R;
import com.wedo.studybar.activities.BookDetailActivity;
import com.wedo.studybar.activities.DiscussionDetailActivity;
import com.wedo.studybar.loader.discussionsLoader;
import com.wedo.studybar.util.Book;
import com.wedo.studybar.util.Discussion;

import java.util.ArrayList;
import java.util.List;

public class DiscussionsFragment extends Fragment implements androidx.loader.app.LoaderManager.LoaderCallbacks<List<Discussion>>{

    private SwipeRefreshLayout swipeRefreshLayout;

    private DiscussionAdapter itemsAdapter;

    private ListView listView;
    private ProgressBar progressBar;
    private TextView emptyStateTextView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_discussions,container,false);

        swipeRefreshLayout = rootView.findViewById(R.id.discussion_refresh_layout);
        emptyStateTextView = rootView.findViewById(R.id.discussion_fragment_empty_view);
        progressBar = rootView.findViewById(R.id.discussion_load_progress);
        listView = rootView.findViewById(R.id.my_discussion_list);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(true);
                }
                progressBar.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
                loadDiscussions();
            }
        });

        /**
         * to show list of discussions
         * */

        loadDiscussions();

        final ArrayList<Discussion> discussions = new ArrayList<>();
        itemsAdapter = new DiscussionAdapter(getActivity(),discussions);
        listView.setEmptyView(emptyStateTextView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                position--;
                Log.e("POSITION",String.valueOf(position));
                Intent intent = new Intent(getActivity(), DiscussionDetailActivity.class);
                intent.putExtra("DISCUSSION_ID",discussions.get(position).getDiscussionId());
                intent.putExtra("DISCUSSION_AUTHOR",discussions.get(position).getDiscussionAuthor());
                intent.putExtra("DISCUSSION_TITLE",discussions.get(position).getDiscussionTitle());
                intent.putExtra("DISCUSSION_CONTENT",discussions.get(position).getDiscussionContent());
                startActivity(intent);
            }
        });
        listView.setAdapter(itemsAdapter);

        return rootView;
    }

    public DiscussionsFragment(){

    }

    private void loadDiscussions(){

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("LoginState",false)){
            emptyStateTextView.setVisibility(View.GONE);

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
                    loaderManager.restartLoader(1, null, this);
                }else {
                    // 初始化 loader。传递上面定义的整数 ID 常量并为为捆绑
                    // 传递 null。为 LoaderCallbacks 参数（由于
                    // 此活动实现了 LoaderCallbacks 接口而有效）传递此活动。
                    loaderManager.initLoader(1, null, this);
                }
            }
            else{
                progressBar.setVisibility(View.GONE);
                emptyStateTextView.setText(R.string.no_internet);
            }
        }else {
            emptyStateTextView.setText(R.string.plz_login_first);
            emptyStateTextView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

    @NonNull
    @Override
    public Loader<List<Discussion>> onCreateLoader(int id, @Nullable Bundle args) {
        return new discussionsLoader(getContext());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Discussion>> loader, List<Discussion> discussions) {
        progressBar.setVisibility(View.GONE);
        emptyStateTextView.setText(R.string.no_discussion);
        swipeRefreshLayout.setRefreshing(false);
        if(itemsAdapter!=null){
            itemsAdapter.clear();
        }
        if(discussions != null && !discussions.isEmpty()){
            itemsAdapter.addAll(discussions);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Discussion>> loader) {
        itemsAdapter.clear();
    }
}
