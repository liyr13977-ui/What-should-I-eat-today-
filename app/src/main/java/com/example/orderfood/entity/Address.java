package com.example.orderfood.entity;

public class Address {
    private String id;
    private String userId;
    private String name;
    private String address;
    private String phone;
    private String is_default;

    public Address(){

    }

    public Address(String id, String userId, String name, String address, String phone,String is_default) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.is_default=is_default;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getIs_default(){return is_default;}

    public void setIs_default(String is_default){this.is_default=is_default;}
}
