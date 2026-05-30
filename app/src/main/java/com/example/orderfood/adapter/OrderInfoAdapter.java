package com.example.orderfood.adapter;

import static android.util.Log.*;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.orderfood.R;
import com.example.orderfood.dao.FoodDAO;
import com.example.orderfood.dao.OrderDAO;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Food;
import com.example.orderfood.until.LoadImage;

import java.math.BigDecimal;
import java.util.List;

public class OrderInfoAdapter extends ArrayAdapter<Food> {
    private Context context;
    private final List<Food> foodlist;
    private String orderId;

    public OrderInfoAdapter(@NonNull Context context, List<Food> listFood,String orderId) {
        super(context, R.layout.adapter_order_info,listFood);
        this.context=context;
        this.foodlist=listFood;
        this.orderId=orderId;
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup viewGroup) {
        if (view==null){
            LayoutInflater inflater=LayoutInflater.from(getContext());
            view=inflater.inflate(R.layout.adapter_order_info,viewGroup,false);
        }

        ImageView foodImg=view.findViewById(R.id.iv_orderInfo_foodImg);
        TextView foodName = view.findViewById(R.id.tv_orderInfo_foodName);
        TextView foodPrice = view.findViewById(R.id.tv_orderInfo_foodPrice);
        TextView foodNum = view.findViewById(R.id.tv_orderInfo_foodNum);

        Food food=foodlist.get(position);

        String[] foodInfo=FoodDAO.getOrderFoodBasicInfo(food.getId(),orderId);
        int quantity = Integer.parseInt(foodInfo[0]);

        foodName.setText(food.getName());
        foodPrice.setText("￥"+String.format("%.2f",Double.parseDouble(food.getPrice())*quantity));
        foodNum.setText("×"+foodInfo[0]);
        
        LoadImage.loadImage(context,foodImg,food.getImg());


        return view;
    }
}
