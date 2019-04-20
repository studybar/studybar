package com.wedo.studybar.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.wedo.studybar.Adapter.NotificationAdapter;
import com.wedo.studybar.activities.DiscussionDetailActivity;
import com.wedo.studybar.util.Notification;
import com.wedo.studybar.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class NotificationsFragment extends Fragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView listView;
    private ProgressBar listFooterView;
    private NotificationAdapter itemsAdapter;
    private Boolean flag_loading = false;

    private int pageCount = 1;

    private NotificationAsyncTaskWait asyncTaskWait;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            listViewLoadMore();
        }
    };

    @Override
    public void onResume(){
        super.onResume();
        IntentFilter intentFilter = new IntentFilter("NOTIFICATION_LOAD_MORE");
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onPause(){
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
        super.onPause();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notifications,container,false);

        mSwipeRefreshLayout = rootView.findViewById(R.id.notification_refresh_layout);
        listView = (ListView)rootView.findViewById(R.id.notification_list);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ArrayList<Notification> notifications = new ArrayList<Notification>();

        notifications.add(new Notification("You have a new message","Search your feelings, Lord Vader. ",R.drawable.avatar_example));
        notifications.add(new Notification("You have a new message","Search your feelings, Lord Vader. ",R.drawable.avatar_example));
        notifications.add(new Notification("You have a new message","Search your feelings, Lord Vader. ",R.drawable.avatar_example));
        notifications.add(new Notification("You have a new message","Search your feelings, Lord Vader. ",R.drawable.avatar_example));
        notifications.add(new Notification("You have a new message","Search your feelings, Lord Vader. ",R.drawable.avatar_example));
        notifications.add(new Notification("You have a new message","Search your feelings, Lord Vader. ",R.drawable.avatar_example));
        notifications.add(new Notification("You have a new message","Search your feelings, Lord Vader. ",R.drawable.avatar_example));
        notifications.add(new Notification("You have a new message","Search your feelings, Lord Vader. ",R.drawable.avatar_example));
        notifications.add(new Notification("You have a new message","Search your feelings, Lord Vader. ",R.drawable.avatar_example));
        notifications.add(new Notification("You have a new message","Search your feelings, Lord Vader. ",R.drawable.avatar_example));
        notifications.add(new Notification("You have a new message","Search your feelings, Lord Vader. ",R.drawable.avatar_example));
        notifications.add(new Notification("You have a new message","Search your feelings, Lord Vader. ",R.drawable.avatar_example));
        notifications.add(new Notification("You have a new message","Search your feelings, Lord Vader. ",R.drawable.avatar_example));
        notifications.add(new Notification("You have a new message","Search your feelings, Lord Vader. ",R.drawable.avatar_example));
        notifications.add(new Notification("You have a new message","Search your feelings, Lord Vader. ",R.drawable.avatar_example));

        itemsAdapter = new NotificationAdapter(getActivity(),notifications);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //todo:passing values
                Intent intent = new Intent(getActivity(), DiscussionDetailActivity.class);
                startActivity(intent);
            }
        });
        listView.setAdapter(itemsAdapter);
        setListViewFooter();
        listView.setFooterDividersEnabled(false);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == SCROLL_STATE_IDLE){
                    if(listView.getLastVisiblePosition() >= listView.getCount() - 1){
                        if(!flag_loading){
                            flag_loading = true;
                            listFooterView.setVisibility(View.VISIBLE);
                            //todo:add items
                            asyncTaskWait = new NotificationAsyncTaskWait(new WeakReference<Context>(getContext()));
                            asyncTaskWait.execute();
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Make sure that the SwipeRefreshLayout is displaying it's refreshing indicator
                if (!mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
                //todo:start background task
                mSwipeRefreshLayout.setRefreshing(false);

            }
        });
    }

    /**
     * load more content
     * */
    private void listViewLoadMore(){
        itemsAdapter.add(new Notification("You have a new message","Roll it again ",R.drawable.avatar_example));
        itemsAdapter.add(new Notification("You have a new message","Roll it again ",R.drawable.avatar_example));
        itemsAdapter.add(new Notification("You have a new message","Roll it again ",R.drawable.avatar_example));
        itemsAdapter.add(new Notification("You have a new message","Roll it again ",R.drawable.avatar_example));
        itemsAdapter.add(new Notification("You have a new message","Roll it again ",R.drawable.avatar_example));
        itemsAdapter.notifyDataSetChanged();
        flag_loading = false;
        listFooterView.setVisibility(View.GONE);
    }

    /**
     * set the FooterView of listview
     * use to show the progress bar
     * */
    private void setListViewFooter(){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.footer_view_load_animation,null);
        listFooterView = view.findViewById(R.id.footer_view_progressbar);
        listView.addFooterView(listFooterView);
    }

    /**
     * doInBackground: make the thread pause for 3 seconds
     * onPostExecute: make a broadcast to load new content
     * */
    public static class NotificationAsyncTaskWait extends AsyncTask<Void, Void, Void> {

        private WeakReference<Context> context;

        private NotificationAsyncTaskWait(WeakReference<Context> context){
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void...params){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void nothing){
            Intent intent = new Intent("NOTIFICATION_LOAD_MORE");
            LocalBroadcastManager.getInstance(context.get().getApplicationContext()).sendBroadcast
                    (intent);
        }
    }
}
