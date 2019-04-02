package com.wedo.studybar.util;

public class Book {
    private String mBookName;
    private String mBookAuthor;
    private int mBookCoverId;

    public Book(String bookName,String bookAuthor,int boookCoverId){
        mBookName = bookName;
        mBookAuthor = bookAuthor;
        mBookCoverId = boookCoverId;
    }

    public String getBookName(){
        return mBookName;
    }

    public String getBookAuthor(){
        return mBookAuthor;
    }

    public int getBookCoverId(){
        return mBookCoverId;
    }
}
