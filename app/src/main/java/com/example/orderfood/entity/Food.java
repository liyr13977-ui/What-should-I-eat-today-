package com.example.orderfood.entity;

import java.io.Serializable;

public class Food implements Serializable {
    private String id;
    private String businessId;
    private String name;
    private String describe;
    private String number;
    private String price;
    private String img;
    private String mark;

    public Food(){

    }
    public Food(String id, String businessId, String name, String describe, String number, String price, String img,String mark) {
        this.id = id;
        this.businessId = businessId;
        this.name = name;
        this.describe = describe;
        this.number = number;
        this.price = price;
        this.img = img;
        this.mark=mark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
