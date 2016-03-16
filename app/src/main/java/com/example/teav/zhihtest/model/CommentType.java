package com.example.teav.zhihtest.model;

/**
 * Created by TeaV on 2016/2/2.
 */
public class CommentType {
    private int CommentUseIid;
    private String CommentText;

    public CommentType(int commentUseIid, String commentText) {
        CommentUseIid = commentUseIid;
        CommentText = commentText;
    }

    public int getCommentUseIid() {
        return CommentUseIid;
    }

    public void setCommentUseIid(int commentUseIid) {
        CommentUseIid = commentUseIid;
    }

    public String getCommentText() {
        return CommentText;
    }

    public void setCommentText(String commentText) {
        CommentText = commentText;
    }
}
