package com.wedo.studybar.util;

public class Category {
    private String mCategoryId;
    private Integer mCategoryName;

    public Category(String categoryId,Integer categoryName){
        mCategoryId = categoryId;
        mCategoryName = categoryName;
    }

    public String getCategoryId(){
        return mCategoryId;
    }
    public Integer getCategoryName(){
        return mCategoryName;
    }
}
