package com.wedo.studybar.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
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
import android.widget.Adapter;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notifications,container,false);

        swipeRefreshLayout = rootView.findViewById(R.id.notification_refresh_layout);
        listView = rootView.findViewById(R.id.notification_list);
        emptyStateTextView = rootView.findViewById(R.id.notification_empty_view);
        progressBar = rootView.findViewById(R.id.notification_load_progress);

        loadNotifications();

        final ArrayList<Notification> notifications = new ArrayList<Notification>();
        itemsAdapter = new NotificationAdapter(getActivity(),notifications);
        //listView.setEmptyView(emptyStateTextView);
        toggleEmptyView(itemsAdapter);
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
                }
                progressBar.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
                loadNotifications();
            }
        });

        return rootView;
    }

    private void loadNotifications() {
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
                    loaderManager.restartLoader(5,null,this);
                }else {
                    // 初始化 loader。传递上面定义的整数 ID 常量并为为捆绑
                    // 传递 null。为 LoaderCallbacks 参数（由于
                    // 此活动实现了 LoaderCallbacks 接口而有效）传递此活动。
                    loaderManager.initLoader(5, null, this);
                }
            }
            else{
                progressBar.setVisibility(View.GONE);
                emptyStateTextView.setText(R.string.no_internet);
            }
        }else {
            emptyStateTextView.setText(R.string.plz_login_first);
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
        swipeRefreshLayout.setRefreshing(false);
        if(itemsAdapter != null){
            itemsAdapter.clear();
        }
        if(notifications != null && !notifications.isEmpty()){
            itemsAdapter.addAll(notifications);
        }
        emptyStateTextView.setText(R.string.no_notifications);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Notification>> loader) {
        itemsAdapter.clear();
    }

    /**
     * Custom empty view handling because we don't want the
     * list to be hidden when the empty view is displayed,
     * since the list must always display the header.
     */
    private void toggleEmptyView(final Adapter adapter)
    {
        //final View emptyView = findViewById(R.id.empty_view);
        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                emptyStateTextView.setVisibility(adapter.getCount() == 0 ? View.VISIBLE : View.GONE);
            }
        });
    }
}
