package com.wedo.studybar.util;

public class Discussion {
    private String mDiscussionId;
    private String mDiscussionAuthor;
    private String mDiscussionTitle;
    private String mDiscussionContent;
    private String mDiscussionNumberOfComments;

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
    public Discussion(String discussionId,String discussionAuthor,String discussionContent,String discussionNumberOfComments){
        mDiscussionId = discussionId;
        mDiscussionAuthor = discussionAuthor;
        mDiscussionContent = discussionContent;
        //mDiscussionNumberOfLikes = discussionNumberOfLikes;
        mDiscussionNumberOfComments = discussionNumberOfComments;
    }

    public String getmDiscussionId(){
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
}
