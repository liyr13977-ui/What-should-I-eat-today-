package com.example.orderfood.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.orderfood.db.DBUntil;
import com.example.orderfood.entity.ChatMessage;
import java.util.ArrayList;
import java.util.List;

public class ChatDAO {
    public static SQLiteDatabase db;
    private String TABLE_CHAT = "messages";

    public ChatDAO(Context context) {
        db= DBUntil.con;
    }

    public void insertMessage(ChatMessage message) {
        ContentValues values = new ContentValues();
        values.put("businessId", message.getBusinessId());
        values.put("userId", message.getUserId());
        values.put("content", message.getContent());
        values.put("sender", message.getSender());
        values.put("date", message.getDate());

        db.insert("messages", null, values);
    }

    public List<ChatMessage> getChatMessages(String businessId, String userId) {
        List<ChatMessage> messages = new ArrayList<>();
        Cursor cursor = db.query(
                "messages",
                new String[]{"id", "businessId", "userId", "content", "sender", "date"},
                "businessId = ? AND userId = ?",
                new String[]{businessId, userId},
                null,
                null,
                "date"
        );

        while (cursor.moveToNext()) {
            @SuppressLint("Range") ChatMessage message = new ChatMessage(
                    cursor.getString(cursor.getColumnIndex("businessId")),
                    cursor.getString(cursor.getColumnIndex("userId")),
                    cursor.getString(cursor.getColumnIndex("content")),
                    cursor.getString(cursor.getColumnIndex("sender")),
                    cursor.getString(cursor.getColumnIndex("date"))
            );
            messages.add(message);
        }

        cursor.close();
        return messages;
    }
}