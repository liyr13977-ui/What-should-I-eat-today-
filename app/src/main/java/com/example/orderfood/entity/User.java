package com.example.orderfood.entity;

public class User {
    private String id;
    private String account;
    private String pwd;
    private String nickName;
    private String gender;
    private String address;
    private String phone;
    private String img;

    public User() {
    }

    public User(String id, String account, String pwd, String nickName, String gender, String address, String phone, String img) {
        this.id = id;
        this.account = account;
        this.pwd = pwd;
        this.nickName = nickName;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
