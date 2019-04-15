package com.wedo.studybar.util;

public class Book {
    private String mBookId;         //book_id
    private String mBookName;       //book_name
    private String mBookAuthor;     //book_editor
    private int mBookCoverId;       //book_image
    private String mBookPress;      //book_press
    private String mNumOfLikes;     //book_col_num
    private String mNumOfComments;
    //book_user_id
    //book_type_id
    //book_comment
    //book_comment_id

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
