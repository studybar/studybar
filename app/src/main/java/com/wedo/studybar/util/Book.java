package com.wedo.studybar.util;

import android.graphics.Bitmap;

public class Book {
    private String mBookId;         //book_id
    private String mBookName;       //book_name
    private String mBookPublisher;
    private String mBookAuthor;
    //private int mBookCoverId;
    private byte[] mBookCoverId;        //book_image
    private String mNumOfComments;

    public Book(String bookId, String bookName, byte[] bookCoverId, String bookAuthor){
        mBookId = bookId;
        mBookName = bookName;
        mBookCoverId = bookCoverId;
        mBookAuthor = bookAuthor;
    }

    public Book(String bookId, String bookName, byte[] bookCoverId, String bookAuthor, String bookPublisher, String numOfComments){
        mBookId = bookId;
        mBookName = bookName;
        mBookCoverId = bookCoverId;
        mBookAuthor = bookAuthor;
        mBookPublisher = bookPublisher;
        mNumOfComments = numOfComments;
    }

    public String getBookId(){
        return mBookId;
    }

    public String getBookName(){
        return mBookName;
    }

    public byte[] getBookCoverId(){
        return mBookCoverId;
    }

    public String getNumOfComments(){
        return mNumOfComments;
    }

    public String getBookAuthor(){
        return mBookAuthor;
    }

    public String getBookPublisher(){
        return mBookPublisher;
    }
}
