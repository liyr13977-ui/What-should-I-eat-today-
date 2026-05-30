package com.example.orderfood.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.orderfood.R;
import com.example.orderfood.adapter.OrderInfoAdapter;
import com.example.orderfood.dao.FoodDAO;
import com.example.orderfood.dao.OrderDAO;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Business;
import com.example.orderfood.entity.Food;
import com.example.orderfood.entity.Order;
import com.example.orderfood.entity.User;

import java.util.List;

public class OrderInfo2Activity extends BaseActivity {
    private ListView foodList;
    private OrderInfoAdapter adapter;
    private String orderId;
    private TextView delivery,payment,orderID,orderTime,deliveryMan,arrivalTime,receiptInfo,receiptAddress,businessName;
    private TextView orderState;
    private Button btnChat;
    private String businessId,userId;
    private Order order1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderId=getIntent().getStringExtra("orderId");
        order1=OrderDAO.getOrderById(orderId);
        if (order1 != null) {
            User user=UserDAO.getUserById(order1.getUserId());
            Business business=UserDAO.getBusinessById(order1.getBusinessId());
            if (user != null) {
                userId = user.getId();
            }
            if (business != null) {
                businessId=business.getId();
            }
        }

        loadData();
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_order_info;
    }

    @Override
    protected void initView() {
        delivery=findViewById(R.id.tv_user_orderInfo_delivery);
        foodList=findViewById(R.id.lv_orderInfo_foodlist);
        payment=findViewById(R.id.tv_user_orderInfo_payment);
        orderID=findViewById(R.id.tv_user_orderInfo_orderID);
        orderTime=findViewById(R.id.tv_user_orderInfo_orderTime);
        deliveryMan=findViewById(R.id.tv_user_orderInfo_deliveryMan);
        receiptAddress=findViewById(R.id.tv_user_orderInfo_receiptAddress);
        businessName=findViewById(R.id.tv_user_orderInfo_businessName);
        orderState=findViewById(R.id.tb_user_orderInfo_orderState);
        btnChat=findViewById(R.id.btn_orderInfo_chat);

    }

    @Override
    protected void initData() {
        btnChat.setOnClickListener(v->{
            Intent intent = new Intent(OrderInfo2Activity.this, ChatActivity.class);
            intent.putExtra("businessId", businessId);
            intent.putExtra("userId", userId);
            intent.putExtra("role", "business");
            OrderInfo2Activity.this.startActivity(intent);
        });
    }

    @SuppressLint("SetTextI18n")
    private void loadData(){
        //加载基本订单信息
        Order order=OrderDAO.getOrderById(orderId);
        Float deliveryFee=OrderDAO.getDeliveryByOrder(orderId);
        delivery.setText(String.valueOf(deliveryFee));


        String[] foodOrderInfo =OrderDAO.getOneFoodBasicInfo2(orderId);
        double totalPrice = Double.parseDouble(foodOrderInfo[1]);
        double totalAmount=totalPrice+deliveryFee;
        payment.setText("合计￥"+totalAmount);
        orderID.setText("1000000"+orderId);
        if (order != null) {
            switch (order.getState()){
                case "1":orderState.setText("待出餐");deliveryMan.setText("无");break;
                case "2":orderState.setText("已取消");deliveryMan.setText("无");break;
                case "4":orderState.setText("待骑手接单");deliveryMan.setText("无");break;
                case "3":orderState.setText("待评论");deliveryMan.setText(order.getDelivery());break;
                case "6":orderState.setText("已完成");deliveryMan.setText(order.getDelivery());break;
                case "7":orderState.setText("配送中");deliveryMan.setText(order.getDelivery());break;
                default:orderState.setText("default");deliveryMan.setText(order.getDelivery());break;
            }


            Business business= UserDAO.getBusinessById(order.getBusinessId());
            orderTime.setText(order.getDate());
            receiptAddress.setText(order.getAddress());
            businessName.setText(business.getName());
        }
        //加载食物列表
        List<Food> foodData = FoodDAO.getFoodByOrder(orderId);
        adapter=new OrderInfoAdapter(this, foodData,orderId);
        foodList.setAdapter(adapter);
    }
}