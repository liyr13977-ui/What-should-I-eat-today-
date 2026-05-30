package com.example.orderfood.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.orderfood.R;
import com.example.orderfood.adapter.OrderInfoAdapter;
import com.example.orderfood.adapter.UserPaymentAdapter;
import com.example.orderfood.dao.FoodDAO;
import com.example.orderfood.dao.OrderDAO;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Business;
import com.example.orderfood.entity.Delivery;
import com.example.orderfood.entity.Food;
import com.example.orderfood.entity.Order;
import com.example.orderfood.until.UserUntil;

import java.util.List;

public class DeliveryOrderInfoActivity extends BaseActivity {
    private ListView foodList;
    private OrderInfoAdapter adapter;
    private String orderId;
    private TextView delivery,payment,tvRemark,tvStart,tvEnd,tvFee,tvBusiName;
    private Button btnEnsure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderId=getIntent().getStringExtra("orderId");
        loadData();
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_delivery_order_info;
    }

    @Override
    protected void initView() {
        delivery=findViewById(R.id.tv_delivery_orderInfo_fee);
        tvFee=findViewById(R.id.tv_delivery_orderInfo_fee1);
        foodList=findViewById(R.id.lv_delivery_orderInfo_foodlist);
        payment=findViewById(R.id.tv_delivery_orderInfo_payment);
        tvRemark=findViewById(R.id.tv_delivery_OrderInfo_remark);
        tvStart=findViewById(R.id.tv_delivery_orderInfo_start);
        tvEnd=findViewById(R.id.tv_delivery_orderInfo_end);
        tvBusiName=findViewById(R.id.tv_delivery_orderInfo_businessName);
        btnEnsure=findViewById(R.id.tv_delivery_OrderInfo_ensure);

    }

    @Override
    protected void initData() {
        btnEnsure.setOnClickListener(v->{
             IntentTo(DeliveryMyDCActivity.class);
             String deliveryManId= String.valueOf(UserUntil.getDeliveryManId(this));
             Delivery deliveryMan=UserDAO.getDeliveryManById(deliveryManId);
            if (deliveryMan != null) {
                UserDAO.updateOrderDC(orderId,deliveryMan.getName());
            }
            finish();
        });
    }

    @SuppressLint("SetTextI18n")
    private void loadData(){
        //加载基本订单信息
        Order order=OrderDAO.getOrderById(orderId);
        Float deliveryFee=OrderDAO.getDeliveryByOrder(orderId);

        delivery.setText(String.valueOf(deliveryFee));
        tvFee.setText("￥"+deliveryFee);

        if (order != null) {
            Business business=UserDAO.getBusinessById(order.getBusinessId());
            tvRemark.setText(order.getRemark());
            tvEnd.setText(order.getAddress());
            if (business != null) {
                tvStart.setText(business.getName());
                tvBusiName.setText(business.getName());
            }
        }


        String[] foodOrderInfo =OrderDAO.getOneFoodBasicInfo2(orderId);
        double totalPrice = Double.parseDouble(foodOrderInfo[1]);
        double totalAmount=totalPrice+deliveryFee;
        payment.setText("合计￥"+totalAmount);

        //加载食物列表
        List<Food> foodData = FoodDAO.getFoodByOrder(orderId);
        adapter=new OrderInfoAdapter(this, foodData,orderId);
        foodList.setAdapter(adapter);
    }
}