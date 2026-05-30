package com.example.orderfood.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.orderfood.R;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Business;
import com.example.orderfood.entity.ChatMessage;
import com.example.orderfood.entity.User;
import com.example.orderfood.until.LoadImage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatAdapter extends ArrayAdapter<ChatMessage> {
    private String currentRole; // 当前视角的角色（user 或 business）

    public ChatAdapter(Context context, List<ChatMessage> messages, String currentRole) {
        super(context, R.layout.item_chat, messages);
        this.currentRole = currentRole;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_chat, parent, false);
        }

        TextView tvSender = convertView.findViewById(R.id.tv_chat_sender);
        TextView tvContent = convertView.findViewById(R.id.tv_chat_content);
        TextView tvTime = convertView.findViewById(R.id.tv_chat_time);
        ImageView ivRight=convertView.findViewById(R.id.iv_chat_imgRight);
        ImageView ivLeft=convertView.findViewById(R.id.iv_chat_imgLeft);

        ChatMessage message = getItem(position);
        String sender = message.getSender();
        String userId=message.getUserId();
        String businessId=message.getBusinessId();
        User user= UserDAO.getUserById(userId);
        Business business=UserDAO.getBusinessById(businessId);

        // 根据当前视角和发送者设置对齐方式和背景
        if (currentRole.equals("user")) {
            // 用户视角
            if (sender.equals("user")) {
                // 用户发送的消息靠右对齐
                setGravity(tvSender, Gravity.END);
                setGravity(tvContent, Gravity.END);
                setGravity(tvTime, Gravity.END);
                if (user != null) {
                    LoadImage.loadImage(getContext(),ivRight,user.getImg());
                }

            } else if (sender.equals("business")) {
                // 商家发送的消息靠左对齐
                setGravity(tvSender, Gravity.START);
                setGravity(tvContent, Gravity.START);
                setGravity(tvTime, Gravity.START);
                if (business != null) {
                    LoadImage.loadImage(getContext(),ivLeft,business.getImg());
                }
            }
        } else if (currentRole.equals("business")) {
            // 商家视角
            if (sender.equals("user")) {
                // 用户发送的消息靠左对齐
                setGravity(tvSender, Gravity.START);
                setGravity(tvContent, Gravity.START);
                setGravity(tvTime, Gravity.START);
                if (user != null) {
                    LoadImage.loadImage(getContext(),ivLeft,user.getImg());
                }
            } else if (sender.equals("business")) {
                // 商家发送的消息靠右对齐
                setGravity(tvSender, Gravity.END);
                setGravity(tvContent, Gravity.END);
                setGravity(tvTime, Gravity.END);
                if (business != null) {
                    LoadImage.loadImage(getContext(),ivRight,business.getImg());
                }
            }
        }

        // 设置文本内容
        if (sender.equals("user")) {
            tvSender.setText("用户");
        } else if (sender.equals("business")) {
            tvSender.setText("商家");
        }

        tvContent.setText(message.getContent());
        // 格式化日期，只显示年月日
        SimpleDateFormat originalFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        SimpleDateFormat targetFormat = new SimpleDateFormat("MM-dd HH:mm", Locale.US);
        try {
            Date date = originalFormat.parse(message.getDate());
            String formattedDate = targetFormat.format(date);
            tvTime.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            tvTime.setText(message.getDate()); // 如果格式化失败，使用原始日期
        }

        return convertView;
    }

    private void setGravity(View view, int gravity) {
        if (view.getLayoutParams() instanceof LinearLayout.LayoutParams) {
            ((LinearLayout.LayoutParams) view.getLayoutParams()).gravity = gravity;
        }
    }
}