package com.wedo.studybar.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.wedo.studybar.Adapter.NotificationAdapter;
import com.wedo.studybar.util.Notification;
import com.wedo.studybar.R;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notifications,container,false);

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

        NotificationAdapter itemsAdapter = new NotificationAdapter(getActivity(),notifications);
        ListView listView = (ListView)rootView.findViewById(R.id.notification_list);
        listView.setAdapter(itemsAdapter);
        return rootView;
    }

    public NotificationsFragment(){

    }
}
