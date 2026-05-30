package com.example.orderfood.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.orderfood.R;
import com.example.orderfood.dao.OrderDAO;
import com.example.orderfood.entity.Business;
import com.example.orderfood.entity.Food;
import com.example.orderfood.entity.Order;
import com.example.orderfood.until.LoadImage;

import java.io.File;
import java.util.List;

public class BusiAllOrderAdapter extends BaseAdapter {
    private Context context;
    private List<Order> orderList;
    private Business business;
    private ImageView foodImg;
    private TextView foodName,number,price;
    private DataUpdateListener dataUpdateListener;

    public BusiAllOrderAdapter(Context context, List<Order> orderList,Business business,DataUpdateListener dataUpdateListener) {
        this.context = context;
        this.orderList = orderList;
        this.business=business;
        this.dataUpdateListener = dataUpdateListener;
    }
    public interface DataUpdateListener {
        void onDataUpdated();
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @NonNull
    @Override
    public View getView(int position, View convertView,ViewGroup viewGroup) {
        Order order=orderList.get(position);
        int layoutId=getLayoutIdForStatus(order.getState());
        @SuppressLint("ViewHolder") View view=LayoutInflater.from(context).inflate(layoutId,viewGroup,false);


        ImageView businessImg=view.findViewById(R.id.businessImg);
        TextView businessName=view.findViewById(R.id.businessName);
        foodImg=view.findViewById(R.id.foodImg);
        foodName=view.findViewById(R.id.foodName);
        number=view.findViewById(R.id.number);
        price=view.findViewById(R.id.price);
        TextView date=view.findViewById(R.id.date);
        TextView orderState=view.findViewById(R.id.orderState);


        LoadImage.loadImage(context,businessImg,business.getImg());

        businessName.setText(business.getName());
        date.setText(order.getDate());

        Food food=OrderDAO.getOneFoodBasicInfo(order.getId());
        LoadImage.loadImage(context,foodImg,food.getImg());
        foodName.setText(food.getName());
        String[] foodOrderInfo = new String[2];
        foodOrderInfo=OrderDAO.getOneFoodBasicInfo2(order.getId());
        double pricePerUnit = Double.parseDouble(foodOrderInfo[1]);
        number.setText("共"+foodOrderInfo[0]+"件");
        price.setText("￥"+String.format("%.2f", pricePerUnit));

        //全部订单里需要展示所有状态的订单，每个状态的订单有着不同的布局
        switch (order.getState()) {
            case "1": {
                orderState.setText("待出餐");
                Button cancel = view.findViewById(R.id.btn_busi_tobeserved_cancel);
                cancel.setOnClickListener(v -> {
                    OrderDAO.cancelOrder(order.getId());
                    dataUpdateListener.onDataUpdated();
                });
                Button ensure=view.findViewById(R.id.btn_busi_tobeserved_ensureFood);
                ensure.setOnClickListener(v->{
                    OrderDAO.ensureFood(order.getId());
                    dataUpdateListener.onDataUpdated();
                });
                break;
            }
            case "4": {
                orderState.setText("待骑手接单");
                break;


            }
            case "7":{
                orderState.setText("骑手正在送货");
            }
            case "2":
                orderState.setText("已取消");
//                Button refund = view.findViewById(R.id.btn_user_cancel_refund);
//                refund.setOnClickListener(v -> {
//
//                });
                break;
            default:
                orderState.setText("已完成");

                break;
        }





        return view;
    }

    private int getLayoutIdForStatus(String status) {//state就是status
        switch (status) {
            case "1":
                return R.layout.adapter_busi_tobeserved;
            case "4":
                return R.layout.adapter_busi_awaiting;
            case "2":
                return R.layout.adapter_user_cancel;
            default:
                return R.layout.adapter_user_finish;// 已完成订单为默认布局
        }
    }
}
