package com.wedo.studybar.util;

public class Discussion {
    private String mDiscussionId;
    private String mDiscussionAuthor;
    private String mDiscussionTitle;
    private String mDiscussionContent;
    private String mDiscussionNumberOfComments;

    private String mCommentFloor;

    private int mStatus;

    private Discussion topic;

    /**
     * Usr for the topic of discussion
     * */
    public Discussion(String discussionId,String discussionAuthor,String discussionTitle,String discussionContent,String discussionNumberOfComments){
        mDiscussionId = discussionId;
        mDiscussionAuthor = discussionAuthor;
        mDiscussionTitle = discussionTitle;
        mDiscussionContent = discussionContent;
        mDiscussionNumberOfComments = discussionNumberOfComments;
    }

    /**
     * Use for comment
     * @param discussionAuthor means the user who wrote this comment
     * @param discussionContent means the content of the comment
     * */
    public Discussion(String discussionId,String discussionAuthor,String discussionContent,String commentFloor,int status){
        mDiscussionId = discussionId;
        mDiscussionAuthor = discussionAuthor;
        mDiscussionContent = discussionContent;
        mCommentFloor = commentFloor;
        mStatus = status;
    }

    public Discussion(String discussionId,String discussionAuthor,String discussionContent,String commentFloor,int status,Discussion topic){
        mDiscussionId = discussionId;
        mDiscussionAuthor = discussionAuthor;
        mDiscussionContent = discussionContent;
        mCommentFloor = commentFloor;
        mStatus = status;
        this.topic = topic;
    }

    public String getDiscussionId(){
        return mDiscussionId;
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

    public String getNumOfComments(){
        return mDiscussionNumberOfComments;
    }

    public String getCommentFloor(){
        return mCommentFloor;
    }

    public Discussion getTopic(){
        return topic;
    }

    public int getStatus(){
        return mStatus;
    }
}
