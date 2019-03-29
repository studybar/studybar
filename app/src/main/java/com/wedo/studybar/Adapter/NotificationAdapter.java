package com.wedo.studybar.Adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wedo.studybar.Notification;
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
        notificationTitle.setText(currentAndroidAdapter.getTitle());

        TextView notificationDetail = (TextView)notificationItemView.findViewById(R.id.notification_detail);
        notificationDetail.setText(currentAndroidAdapter.getDetail());

        ImageView image = (ImageView)notificationItemView.findViewById(R.id.notification_avatar);
        image.setImageResource(currentAndroidAdapter.getImageResourceId());

        return notificationItemView;
    }
}
