package com.example.orderfood.entity;

public class Order {
    private String id;
    private String userId;
    private String businessId;
    private String date;
    private String address;
    private String state;
    private String name;
    private String phone;
    private String mark;
    private String delivery;
    private String remark;
    private String evaluate;
    private String deliveryMark;

    public Order() {
    }

    public Order(String id, String userId, String businessId, String date, String address, String state, String name,String phone,String mark,String delivery,String remark,String evaluate,String deliveryMark) {
        this.id = id;
        this.userId = userId;
        this.businessId = businessId;
        this.date = date;
        this.address = address;
        this.state = state;
        this.name=name;
        this.phone=phone;
        this.mark = mark;
        this.delivery=delivery;
        this.remark=remark;
        this.evaluate=evaluate;
        this.deliveryMark=deliveryMark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getDelivery(){return delivery;}

    public void setDelivery(String delivery){this.delivery=delivery;}

    public String getRemark(){return remark;}

    public void setRemark(String remark){this.remark=remark;}


    public String getEvaluate(){return evaluate;}

    public void setEvaluate(String evaluate){this.evaluate=evaluate;}


    public String getDeliveryMark(){return deliveryMark;}

    public void setDeliveryMark(String deliveryMark){this.deliveryMark=deliveryMark;}
}
