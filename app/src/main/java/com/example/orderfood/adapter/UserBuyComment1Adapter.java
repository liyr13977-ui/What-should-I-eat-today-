package com.example.orderfood.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.orderfood.R;
import com.example.orderfood.dao.FoodDAO;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Food;
import com.example.orderfood.entity.Order;
import com.example.orderfood.entity.User;
import com.example.orderfood.until.LoadImage;

import java.util.List;

public class UserBuyComment1Adapter extends ArrayAdapter<Order> {
    private Context context;
    private List<Order> listOrder;

    public UserBuyComment1Adapter(@NonNull Context context, List<Order> listOrder) {
        super(context, R.layout.adapter_user_showcomment1, listOrder);
        this.context = context;
        this.listOrder = listOrder;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.adapter_user_showcomment1, viewGroup, false);
        }

        ImageView userPhoto=view.findViewById(R.id.tv_user_showComment_userPhoto);
        TextView userName = view.findViewById(R.id.tv_user_showComment_userName);
        TextView content = view.findViewById(R.id.tv_user_showComment_content);
        TextView star=view.findViewById(R.id.tv_user_showComment_star);

        Order order = listOrder.get(position);
        String userId=order.getUserId();
        User user= UserDAO.getUserById(userId);
        if (user != null) {
            userName.setText(user.getNickName());
            content.setText(order.getEvaluate());
            LoadImage.loadImage(context,userPhoto,user.getImg());
            star.setText("评分："+order.getMark());
        }


        return view;
    }
}