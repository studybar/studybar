package com.wedo.studybar.util;

public class Category {
    private String mCategoryId;
    private String mCategoryName;

    public Category(String categoryId,String categoryName){
        mCategoryId = categoryId;
        mCategoryName = categoryName;
    }

    public String getmCategoryId(){
        return mCategoryId;
    }
    public String getCategoryName(){
        return mCategoryName;
    }
}
