package com.wedo.studybar.util;

public class Discussion {
    private String mDiscussionId;               //com_id        or //com_multi_id       //id
    private String mDiscussionAuthor;           //com_user_id   or //com_multi_user_id
    private String mDiscussionTitle;                                                    //title
    private String mDiscussionContent;          //com_content   or //com_multi_content  //content
    private String mDiscussionNumberOfLikes;
    private String mDiscussionNumberOfComments;                                         //countComment

    /**
     * Usr for the topic of discussion
     * */
    public Discussion(String discussionId,String discussionAuthor,String discussionTitle,String discussionContent,String discussionNumberOfLikes,String discussionNumberOfComments){
        mDiscussionId = discussionId;
        mDiscussionAuthor = discussionAuthor;
        mDiscussionTitle = discussionTitle;
        mDiscussionContent = discussionContent;
        mDiscussionNumberOfLikes = discussionNumberOfLikes;
        mDiscussionNumberOfComments = discussionNumberOfComments;
    }

    /**
     * Use for comment
     * @param discussionAuthor means the user who wrote this comment
     * @param discussionContent means the content of the comment
     * */
    public Discussion(String discussionId,String discussionAuthor,String discussionContent,String discussionNumberOfLikes,String discussionNumberOfComments){
        mDiscussionId = discussionId;
        mDiscussionAuthor = discussionAuthor;
        mDiscussionContent = discussionContent;
        mDiscussionNumberOfLikes = discussionNumberOfLikes;
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

    public String getNumOfLikes(){
        return mDiscussionNumberOfLikes;
    }

    public String getNumOfComments(){
        return mDiscussionNumberOfComments;
    }
}
