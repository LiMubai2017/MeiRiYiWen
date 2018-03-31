package com.example.meiriyiwen.model;

/**
 * Created by 李木白 on 2018/3/24.
 */

public class Tag {
    private String title;
    private String author;
    private String anchor;
    private int issuse;
    private String imageURL;
    private String bid;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAnchor() {
        return anchor;
    }

    public void setAnchor(String anchor) {
        this.anchor = anchor;
    }

    public int getIssuse() {
        return issuse;
    }

    public void setIssuse(int issuse) {
        this.issuse = issuse;
    }
}

