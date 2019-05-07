package com.wedo.studybar.util;

public class Book {
    private String mBookId;         //book_id
    private String mBookName;       //book_name
    private String mBookPublisher;
    private String mBookAuthor;
    private int mBookCoverId;
    private String mCoverId;        //book_image
    private String mNumOfComments;

    public Book(String bookId,String bookName,int bookCoverId,String bookAuthor){
        mBookId = bookId;
        mBookName = bookName;
        mBookCoverId = bookCoverId;
        mBookAuthor = bookAuthor;
    }

    public Book(String bookId,String bookName,int bookCoverId,String bookAuthor,String bookPublisher,String numOfComments){
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

    public int getBookCoverId(){
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
