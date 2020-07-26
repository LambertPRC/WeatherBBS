package com.example.weather.bean;

public class User {

    private Integer userid;
    private  String password;
    private String username;
    private int usertype;
    private String phone;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUsertype() {
        return usertype;
    }

    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "userid=" + userid +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", usertype=" + usertype +
                ", phone=" + phone +
                '}';
    }
}
