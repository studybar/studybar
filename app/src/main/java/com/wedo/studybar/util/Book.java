package com.wedo.studybar.util;

public class Book {
    private String mBookId;
    private String mBookName;
    private String mBookAuthor;
    private int mBookCoverId;
    private String mBookPress;
    private String mNumOfLikes;
    private String mNumOfComments;

    public Book(String bookId,String bookName,String bookAuthor,int bookCoverId){
        mBookId = bookId;
        mBookName = bookName;
        mBookAuthor = bookAuthor;
        mBookCoverId = bookCoverId;
    }

    public Book(String bookId,String bookName,String bookAuthor,int bookCoverId,String bookPress,String numOfLikes,String numOfComments){
        mBookId = bookId;
        mBookName = bookName;
        mBookAuthor = bookAuthor;
        mBookCoverId = bookCoverId;
        mBookPress = bookPress;
        mNumOfLikes = numOfLikes;
        mNumOfComments = numOfComments;
    }

    public String getBookId(){
        return mBookId;
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

    public String getBookPress(){
        return mBookPress;
    }

    public String getNumOfLikes(){
        return mNumOfLikes;
    }

    public String getmNumOfComments(){
        return mNumOfComments;
    }
}
