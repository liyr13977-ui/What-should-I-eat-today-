package com.example.orderfood.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.orderfood.R;
import com.example.orderfood.dao.FoodDAO;
import com.example.orderfood.entity.Food;
import com.example.orderfood.until.LoadImage;

import java.util.List;

public class UserCommentOrderAdapter extends ArrayAdapter<Food> {
    private Context context;
    private List<Food> foodlist;
    private String orderId;
    private boolean viewOnly = false;

    public UserCommentOrderAdapter(@NonNull Context context, List<Food> listFood, String orderId) {
        super(context, R.layout.adapter_user_commentorder, listFood);
        this.context = context;
        this.foodlist = listFood;
        this.orderId = orderId;
    }

    public void setViewOnly(boolean viewOnly) {
        this.viewOnly = viewOnly;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.adapter_user_commentorder, viewGroup, false);
        }

        ImageView foodImg = view.findViewById(R.id.iv_user_commentOrder_foodImg);
        TextView foodName = view.findViewById(R.id.tv_user_commentOrder_foodName);
        RatingBar rb = view.findViewById(R.id.rb_user_commentOrder_start);

        Food food = foodlist.get(position);

        if (viewOnly) {
            rb.setIsIndicator(true);
            String mark = food.getMark();
            if (mark != null && !mark.isEmpty()) {
                rb.setRating(Float.parseFloat(mark));
            }
        } else {
            rb.setIsIndicator(false);
            rb.setRating(0);
            rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    FoodDAO.updateFoodMark(orderId, food.getId(), String.valueOf(rating));
                }
            });
        }

        foodName.setText(food.getName());
        LoadImage.loadImage(context, foodImg, food.getImg());
        return view;
    }
}

