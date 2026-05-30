package com.example.orderfood.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.orderfood.R;
import com.example.orderfood.activity.UserCommentOrderActivity;
import com.example.orderfood.dao.OrderDAO;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Business;
import com.example.orderfood.entity.Food;
import com.example.orderfood.entity.Order;
import com.example.orderfood.until.LoadImage;

import java.io.File;
import java.util.List;

public class UserFinishAdapter extends ArrayAdapter<Order> {
    private View view;
    private List<Order> listOrder;

    public UserFinishAdapter(@NonNull Context context, List<Order> listOrder) {
        super(context, R.layout.adapter_user_finish,listOrder);
        this.listOrder=listOrder;
    }

    @Override
    public Order getItem(int position) {
        return listOrder.get(position);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater inflater=LayoutInflater.from(getContext());
            view=inflater.inflate(R.layout.adapter_user_finish,viewGroup,false);
        }
        Order order=listOrder.get(position);

        ImageView businessImg=view.findViewById(R.id.businessImg);
        TextView businessName=view.findViewById(R.id.businessName);
        ImageView foodImg = view.findViewById(R.id.foodImg);
        TextView foodName = view.findViewById(R.id.foodName);
        TextView number = view.findViewById(R.id.number);
        TextView price = view.findViewById(R.id.price);
        TextView date=view.findViewById(R.id.date);
        TextView orderState=view.findViewById(R.id.orderState);
        Button commentOrder=view.findViewById(R.id.btn_user_finish_commentOrder);

        commentOrder.setOnClickListener(V->{
            Intent intent=new Intent(getContext(), UserCommentOrderActivity.class);
            intent.putExtra("orderId",order.getId());
            getContext().startActivity(intent);
        });

        Business business= UserDAO.getBusinessById(order.getBusinessId());

        if (business != null) {
            LoadImage.loadImage(getContext(),businessImg,business.getImg());
            businessName.setText(business.getName());
        }

        date.setText(order.getDate());

        Food food= OrderDAO.getOneFoodBasicInfo(order.getId());
        LoadImage.loadImage(getContext(),foodImg,food.getImg());
        foodName.setText(food.getName());


        String[] foodOrderInfo = OrderDAO.getOneFoodBasicInfo2(order.getId());


        int totalQuantity = Integer.parseInt(foodOrderInfo[0]);
        double totalPrice = Double.parseDouble(foodOrderInfo[1]);

        number.setText("共"+totalQuantity+"件");
        price.setText("￥"+String.format("%.2f", totalPrice));

        switch (order.getState()){
            case "1":orderState.setText("待商家出餐");break;
            case "2":orderState.setText("已取消");break;
            case "3":orderState.setText("已完成");break;
            case "4":orderState.setText("待收货");break;
        }




        return view;
    }

}
