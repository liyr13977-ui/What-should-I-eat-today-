package com.example.orderfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.orderfood.R;
import com.example.orderfood.adapter.ChatAdapter;
import com.example.orderfood.dao.ChatDAO;
import com.example.orderfood.entity.ChatMessage;

import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private ListView lvChat;
    private EditText etChatInput;
    private Button btnSend;
    private ChatAdapter chatAdapter;
    private ChatDAO chatDAO;
    private List<ChatMessage> chatMessages;
    private String businessId;
    private String userId;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // 初始化界面
        lvChat = findViewById(R.id.lv_chat);
        etChatInput = findViewById(R.id.et_chat_input);
        btnSend = findViewById(R.id.btn_send);

        // 获取传入的商家ID和用户ID
        Intent intent = getIntent();
        businessId = intent.getStringExtra("businessId");
        userId = intent.getStringExtra("userId");
        role = intent.getStringExtra("role");

        // 初始化消息DAO
        chatDAO = new ChatDAO(this);
        chatMessages = chatDAO.getChatMessages(businessId, userId);

        // 设置适配器
        chatAdapter = new ChatAdapter(this, chatMessages,role);
        lvChat.setAdapter(chatAdapter);

        // 发送消息按钮点击事件
        btnSend.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
        String content = etChatInput.getText().toString().trim();
        if (content.isEmpty()) {
            return;
        }

        // 插入消息到数据库
        ChatMessage message = new ChatMessage(
                businessId,
                userId,
                content,
                role, // 发送者标识，根据传入的 role 参数设置
                new java.util.Date().toString() // 当前时间
        );
        chatDAO.insertMessage(message);

        // 更新消息列表
        chatMessages.add(message);
        chatAdapter.notifyDataSetChanged();

        // 清空输入框
        etChatInput.setText("");
    }
}