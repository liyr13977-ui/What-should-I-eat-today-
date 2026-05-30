package com.example.orderfood.entity;

import java.io.Serializable;

public class Business implements Serializable {
    private String id;
    private String account;
    private String pwd;
    private String name;
    private String describe;
    private String img;
    private String delivery;
    private String mark;
    private String phone;

    public Business() {
    }

    public Business(String id, String account, String pwd, String name, String describe, String img, String delivery, String mark,String phone) {
        this.id = id;
        this.account = account;
        this.pwd = pwd;
        this.name = name;
        this.describe = describe;
        this.img = img;
        this.delivery = delivery;
        this.mark = mark;
        this.phone=phone;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getPhone(){return phone;}

    public void setPhone(String phone){this.phone=phone;}
}
