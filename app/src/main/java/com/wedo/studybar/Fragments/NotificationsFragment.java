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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wedo.studybar.Adapter.NotificationAdapter;
import com.wedo.studybar.activities.DiscussionDetailActivity;
import com.wedo.studybar.loader.notificationLoader;
import com.wedo.studybar.util.Notification;
import com.wedo.studybar.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment implements androidx.loader.app.LoaderManager.LoaderCallbacks<List<Notification>> {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private TextView emptyStateTextView;
    private ProgressBar progressBar;

    private NotificationAdapter itemsAdapter;
    private Boolean isRefreshing = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notifications,container,false);

        swipeRefreshLayout = rootView.findViewById(R.id.notification_refresh_layout);
        listView = rootView.findViewById(R.id.notification_list);
        emptyStateTextView = rootView.findViewById(R.id.notification_empty_view);
        progressBar = rootView.findViewById(R.id.notification_load_progress);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadNotifications();

        final ArrayList<Notification> notifications = new ArrayList<Notification>();
        itemsAdapter = new NotificationAdapter(getActivity(),notifications);
        listView.setEmptyView(emptyStateTextView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DiscussionDetailActivity.class);
                intent.putExtra("DISCUSSION_ID",notifications.get(position).getNotificationTopic().getDiscussionId());
                intent.putExtra("DISCUSSION_AUTHOR",notifications.get(position).getNotificationTopic().getDiscussionAuthor());
                intent.putExtra("DISCUSSION_TITLE",notifications.get(position).getNotificationTopic().getDiscussionTitle());
                intent.putExtra("DISCUSSION_CONTENT",notifications.get(position).getNotificationTopic().getDiscussionContent());
                startActivity(intent);
            }
        });
        listView.setAdapter(itemsAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Make sure that the SwipeRefreshLayout is displaying it's refreshing indicator
                if(!swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(true);
                    isRefreshing = true;
                    progressBar.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);
                    loadNotifications();
                    Log.e("REFRESH","START");
                }
            }
        });
    }

    private void loadNotifications() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        if(sharedPreferences.getBoolean("LoginState",false)){
            // Get a reference to the ConnectivityManager to check state of network connectivity
            ConnectivityManager connMgr = (ConnectivityManager)
                    getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

            // Get details on the currently active default data network
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            // If there is a network connection, fetch data
            if (networkInfo != null && networkInfo.isConnected()) {
                // 引用 LoaderManager，以便与 loader 进行交互。
                LoaderManager loaderManager = getLoaderManager();

                if(isRefreshing){
                    Log.e("REFRESH","RELOAD");
                    loaderManager.restartLoader(1,null,this);
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
    public Loader<List<Notification>> onCreateLoader(int id, @Nullable Bundle args) {
        return new notificationLoader(getActivity());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Notification>> loader, List<Notification> notifications){
        progressBar.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        emptyStateTextView.setText(R.string.no_notifications);
        swipeRefreshLayout.setRefreshing(false);
        isRefreshing = false;
        if(itemsAdapter != null){
            itemsAdapter.clear();
        }
        if(notifications != null && !notifications.isEmpty()){
            itemsAdapter.addAll(notifications);
        }
        Log.e("REFRESH","LOAD FINISHED");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Notification>> loader) {
        itemsAdapter.clear();
    }
}
