package com.wedo.studybar.util;

import android.content.Context;

import com.wedo.studybar.R;

public class Notification {
    private Context context;
    private String notificationId;
    private String notificationCommentUser;
    private Discussion topic;


    public Notification(Context context, String notificationId,String notificationCommentUser,Discussion topic){
        this.context = context;
        this.notificationId = notificationId;
        this.notificationCommentUser = notificationCommentUser;
        this.topic = topic;
    }

    public String getNotificationId(){
        return notificationId;
    }

    public Discussion getNotificationTopic(){
        return topic;
    }

    public String getNotificationContent(){
        return context.getString(R.string.notification_message,notificationCommentUser,topic.getDiscussionTitle());
    }
}
