package com.example.teav.zhihtest.model;

import java.util.List;

/**
 * Created by TeaV on 2016/2/2.
 */
public class User {
    private int UserId;
    private String username;
    private String password;
    private List<Integer> AskedList;
    private List<Integer> CommentList;

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void addAskedList(int ProblemId) {
        try {
            AskedList.add(ProblemId);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void addCommentList(int ProblemId) {
        try {
            CommentList.add(ProblemId);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
