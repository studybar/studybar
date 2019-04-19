package com.wedo.studybar.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wedo.studybar.Adapter.NotificationAdapter;
import com.wedo.studybar.activities.DiscussionDetailActivity;
import com.wedo.studybar.util.Notification;
import com.wedo.studybar.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView listView;
    private NotificationAdapter itemsAdapter;

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

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Log.i(LOG_TAG,"onRefresh called from SwipeRefreshLayout");

                // Make sure that the SwipeRefreshLayout is displaying it's refreshing indicator
                if (!mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(true);
                }

                //todo:start background task

                /*
                final int TASK_DURATION = 3 * 1000; // 3 seconds


                try {
                    Thread.sleep(TASK_DURATION);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                */

                mSwipeRefreshLayout.setRefreshing(false);

            }
        });
    }
}
