package com.wedo.studybar.util;

public class Book {
    private String mBookId;         //book_id
    private String mBookName;       //book_name
    private int mBookCoverId;       //book_image
    private String mNumOfComments;

    public Book(String bookId,String bookName,int bookCoverId){
        mBookId = bookId;
        mBookName = bookName;
        mBookCoverId = bookCoverId;
    }

    public Book(String bookId,String bookName,int bookCoverId,String numOfComments){
        mBookId = bookId;
        mBookName = bookName;
        mBookCoverId = bookCoverId;
        mNumOfComments = numOfComments;
    }

    public String getBookId(){
        return mBookId;
    }

    public String getBookName(){
        return mBookName;
    }

    public int getBookCoverId(){
        return mBookCoverId;
    }

    public String getmNumOfComments(){
        return mNumOfComments;
    }
}
