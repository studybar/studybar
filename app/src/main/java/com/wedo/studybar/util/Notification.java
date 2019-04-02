package com.wedo.studybar.util;

public class Notification {
    private String mTitle;
    private String mDetail;
    private int mImageResourceId;

    public Notification(String title,String detail,int imageResourceId){
        mTitle = title;
        mDetail = detail;
        mImageResourceId = imageResourceId;
    }

    public String getTitle(){ return mTitle; }
    public String getDetail(){ return mDetail; }
    public int getImageResourceId() { return mImageResourceId; }
}
