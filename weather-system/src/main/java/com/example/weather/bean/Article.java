package com.example.weather.bean;

import java.util.Date;

public class Article {


    Integer articleid;
    String title;
    String author;
    Integer userid;
    String content;
    int clicknum;
    Date buildtime;

    @Override
    public String toString() {
        return "Article{" +
                "articleid=" + articleid +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", userid=" + userid +
                ", content='" + content + '\'' +
                ", clicknum=" + clicknum +
                ", buildtime=" + buildtime +
                ", status='" + status + '\'' +
                ", img='" + img + '\'' +
                '}';
    }

    public Date getBuildtime() {
        return buildtime;
    }

    public void setBuildtime(Date buildtime) {
        this.buildtime = buildtime;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    String status;
    String img;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getArticleid() {
        return articleid;
    }

    public void setArticleid(Integer articleid) {
        this.articleid = articleid;
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

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getClicknum() {
        return clicknum;
    }

    public void setClicknum(int clicknum) {
        this.clicknum = clicknum;
    }

}
