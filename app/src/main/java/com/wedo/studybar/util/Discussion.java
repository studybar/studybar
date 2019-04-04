package com.wedo.studybar.util;

public class Discussion {
    private String mDiscussionAuthor;
    private String mDiscussionTitle;
    private String mDiscussionContent;
    private String mDiscussionNumberOfLikes;
    private String mDiscussionNumberOfComments;

    public Discussion(String discussionAuthor,String discussionTitle,String discussionContent,String discussionNumberOfLikes,String discussionNumberOfComments){
        mDiscussionAuthor = discussionAuthor;
        mDiscussionTitle = discussionTitle;
        mDiscussionContent = discussionContent;
        mDiscussionNumberOfLikes = discussionNumberOfLikes;
        mDiscussionNumberOfComments = discussionNumberOfComments;
    }

    public String getDiscussionAuthor(){
        return mDiscussionAuthor;
    }

    public String getDiscussionTitle(){
        return mDiscussionTitle;
    }

    public String getDiscussionContent(){
        return mDiscussionContent;
    }

    public String getNumOfLikes(){
        return mDiscussionNumberOfLikes;
    }

    public String getNumOfComments(){
        return mDiscussionNumberOfComments;
    }
}
