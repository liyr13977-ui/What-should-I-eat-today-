package com.example.orderfood.entity;

public class ChatMessage {
    private int id;
    private String businessId;
    private String userId;
    private String content;
    private String sender; // 发送者标识，可以是 "user" 或 "business"
    private String date; // 发送时间

    // 构造函数
    public ChatMessage(String businessId, String userId, String content, String sender, String date) {
        this.businessId = businessId;
        this.userId = userId;
        this.content = content;
        this.sender = sender;
        this.date = date;
    }

    // Getters 和 Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getBusinessId() { return businessId; }
    public void setBusinessId(String businessId) { this.businessId = businessId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getSender() { return sender; }
    public void setSender(String sender) { this.sender = sender; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}