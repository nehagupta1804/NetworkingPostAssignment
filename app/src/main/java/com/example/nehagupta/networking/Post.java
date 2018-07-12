package com.example.nehagupta.networking;

public class Post {

    public int postid;
    public int userid;
    public String post;
    public String title;
    public String body;
    public Post(int postid, int userid, String post, String title, String body) {
        this.postid = postid;
        this.userid = userid;
        this.post = post;
        this.title = title;
        this.body = body;
    }

    public int getPostid() {
        return postid;
    }

    public void setPostid(int postid) {
        this.postid = postid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


}
