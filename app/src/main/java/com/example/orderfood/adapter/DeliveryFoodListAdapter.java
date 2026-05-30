package com.example.orderfood.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.orderfood.R;
import com.example.orderfood.dao.FoodDAO;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Business;
import com.example.orderfood.entity.Food;
import com.example.orderfood.entity.Order;
import com.example.orderfood.until.LoadImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//显示用户商品的adapter
public class DeliveryFoodListAdapter extends ArrayAdapter<Order>{
    private final List<Order> orderList;
    private Context context;

    public DeliveryFoodListAdapter(@NonNull Context context, List<Order> orderList) {
        super(context, R.layout.adapter_delivery_foodlist,orderList);
        this.context=context;
        this.orderList=orderList;
    }
    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater inflater=LayoutInflater.from(getContext());
            view=inflater.inflate(R.layout.adapter_delivery_foodlist,viewGroup,false);
        }
        Order order=orderList.get(position);
        String businessId=order.getBusinessId();
        Business business=UserDAO.getBusinessById(businessId);

        TextView tvStart=view.findViewById(R.id.tv_delivery_foodList_start);
        TextView tvEnd=view.findViewById(R.id.tv_delivery_foodList_end);
        TextView tvFee=view.findViewById(R.id.tv_delivery_foodList_fee);



        tvStart.setText(business.getName());
        tvEnd.setText(order.getAddress());
        tvFee.setText("￥"+business.getDelivery());


        return view;
    }
}
