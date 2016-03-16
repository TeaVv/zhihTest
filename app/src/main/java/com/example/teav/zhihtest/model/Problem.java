package com.example.teav.zhihtest.model;

import java.util.List;

/**
 * Created by TeaV on 2016/2/2.
 */
public class Problem {
    private int ProblemId;
    private int askUserId;
    private String ProblemText;
    private List<CommentType> Comments;

    public Problem () {

    }

    public Problem(int askUserId, String problemText) {
        this.askUserId = askUserId;
        ProblemText = problemText;
    }

    public int getId() {
        return ProblemId;
    }

    public void setId(int id) {
        ProblemId = id;
    }

    public int getAskUserId() {
        return askUserId;
    }

    public void setAskUserId(int askUserId) {
        this.askUserId = askUserId;
    }

    public String getProblemText() {
        return ProblemText;
    }

    public void setProblemText(String problemText) {
        ProblemText = problemText;
    }

    public List<CommentType> getComments() {
        return Comments;
    }

    public void setComments(List<CommentType> comments) {
        Comments = comments;
    }

    public void AddComment(CommentType comment) {
        try {
            Comments.add(comment);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
