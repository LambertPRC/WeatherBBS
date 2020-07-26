package com.example.weather.bean;

public class View {

    Integer viewid;

    String belongcity;
    String viewname;
    String correweather;//适应的天气
    String introduction;
    String viewimg;

    @Override
    public String toString() {
        return "View{" +
                "viewid=" + viewid +
                ", belongcity='" + belongcity + '\'' +
                ", viewname='" + viewname + '\'' +
                ", correweather='" + correweather + '\'' +
                ", introduction='" + introduction + '\'' +
                ", viewimg='" + viewimg + '\'' +
                '}';
    }

    public Integer getViewid() {
        return viewid;
    }

    public void setViewid(Integer viewid) {
        this.viewid = viewid;
    }

    public String getBelongcity() {
        return belongcity;
    }

    public void setBelongcity(String belongcity) {
        this.belongcity = belongcity;
    }

    public String getViewimg() {
        return viewimg;
    }

    public void setViewimg(String viewimg) {
        this.viewimg = viewimg;
    }

    public String getViewname() {
        return viewname;
    }

    public void setViewname(String viewname) {
        this.viewname = viewname;
    }

    public String getCorreweather() {
        return correweather;
    }

    public void setCorreweather(String correweather) {
        this.correweather = correweather;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
}
