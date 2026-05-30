package com.example.orderfood.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.orderfood.R;
import com.example.orderfood.dao.OrderDAO;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Business;
import com.example.orderfood.entity.Food;
import com.example.orderfood.entity.Order;
import com.example.orderfood.until.LoadImage;

import java.util.List;

public class DeliveryDCAdapter extends BaseAdapter {
    private Context context;
    private List<Order> orderList;
    private OnDeliveryOrderListener onDeliveryOrderListener; // 定义回调接口

    public interface OnDeliveryOrderListener {
        void onCancelOrder(String orderId);
        void onConfirmOrder(String orderId);
    }

    public void setOnDeliveryOrderListener(OnDeliveryOrderListener listener) {
        this.onDeliveryOrderListener = listener;
    }

    public DeliveryDCAdapter(@NonNull Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
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
    public View getView(int position, View view, @NonNull ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater inflater=LayoutInflater.from(context);
            view=inflater.inflate(R.layout.adapter_delivery_mydc,viewGroup,false);
        }
        Order order=orderList.get(position);
        String businessId=order.getBusinessId();
        Business business=UserDAO.getBusinessById(businessId);

        TextView tvStart=view.findViewById(R.id.tv_delivery_foodList_start);
        TextView tvEnd=view.findViewById(R.id.tv_delivery_foodList_end);
        TextView tvFee=view.findViewById(R.id.tv_delivery_foodList_fee);
        Button btnCancel=view.findViewById(R.id.btn_delivery_MyDC_cancel);
        Button btnConfirm=view.findViewById(R.id.btn_delivery_MyDC_confirm);

        btnCancel.setOnClickListener(v -> {
            if (onDeliveryOrderListener != null) {
                onDeliveryOrderListener.onCancelOrder(order.getId());
            }
        });

        btnConfirm.setOnClickListener(v -> {
            if (onDeliveryOrderListener != null) {
                onDeliveryOrderListener.onConfirmOrder(order.getId());
            }
        });



        tvStart.setText(business.getName());
        tvEnd.setText(order.getAddress());
        tvFee.setText("￥"+business.getDelivery());


        return view;
    }
}