package com.wedo.studybar.Adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wedo.studybar.util.Notification;
import com.wedo.studybar.R;

import java.util.ArrayList;

public class NotificationAdapter extends ArrayAdapter<Notification> {

    public NotificationAdapter(Activity context, ArrayList<Notification> notificationArrayList){
        super(context,0,notificationArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View notificationItemView = convertView;
        if(notificationItemView == null){
            notificationItemView = LayoutInflater.from((getContext())).inflate(
                    R.layout.notification_list_item,parent,false);
        }
        Notification currentAndroidAdapter = getItem(position);

        TextView notificationTitle = (TextView)notificationItemView.findViewById(R.id.notification_title);
        notificationTitle.setText(currentAndroidAdapter.getNotificationContent());

        return notificationItemView;
    }
}
