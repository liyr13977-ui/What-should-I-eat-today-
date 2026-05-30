package com.example.orderfood.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.orderfood.R;
import com.example.orderfood.dao.OrderDAO;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Business;
import com.example.orderfood.entity.Food;
import com.example.orderfood.entity.Order;
import com.example.orderfood.until.LoadImage;

import java.io.File;
import java.util.List;

public class UserAwaitingAdapter extends BaseAdapter {
    private Context context;
    private List<Order> orderList;
    private OnEnsureClickListener onEnsureClickListener;

    public interface OnEnsureClickListener {
        void onEnsureClick();
    }

    public void setOnEnsureClickListener(OnEnsureClickListener listener) {
        this.onEnsureClickListener = listener;
    }

    public UserAwaitingAdapter(@NonNull Context context, List<Order> orderList) {
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
    public View getView(int position, View convertView, @NonNull ViewGroup viewGroup) {
        Order order = orderList.get(position);
        int layoutId = getLayoutIdForStatus(order.getState());
        @SuppressLint("ViewHolder") View view = LayoutInflater.from(context).inflate(layoutId, viewGroup, false);

        ImageView businessImg = view.findViewById(R.id.businessImg);
        TextView businessName = view.findViewById(R.id.businessName);
        ImageView foodImg = view.findViewById(R.id.foodImg);
        TextView foodName = view.findViewById(R.id.foodName);
        TextView number = view.findViewById(R.id.number);
        TextView price = view.findViewById(R.id.price);
        TextView date = view.findViewById(R.id.date);
        TextView orderState = view.findViewById(R.id.orderState);

        Business business= UserDAO.getBusinessById(order.getBusinessId());

        if (business != null) {
            LoadImage.loadImage(context,businessImg,business.getImg());
            businessName.setText(business.getName());
        }

        Food food = OrderDAO.getOneFoodBasicInfo(order.getId());
        LoadImage.loadImage(context, foodImg, food.getImg());
        foodName.setText(food.getName());
        String[] foodOrderInfo = new String[2];
        foodOrderInfo = OrderDAO.getOneFoodBasicInfo2(order.getId());

        double pricePerUnit = Double.parseDouble(foodOrderInfo[1]);
        number.setText("共" + foodOrderInfo[0] + "件");
        price.setText("￥" + String.format("%.2f", pricePerUnit));


        date.setText(order.getDate());

        if (order.getState().equals("4")) {
            orderState.setText("待骑手接单");
            Button cancel = view.findViewById(R.id.btn_user_tobeserved_cancel);
            cancel.setOnClickListener(v -> {

                new AlertDialog.Builder(context)
                        .setTitle("确认取消")
                        .setMessage("确定要取消订单吗？")
                        .setPositiveButton("确定", (dialog, which) -> {
                            OrderDAO.cancelOrder(order.getId());
                            if (onEnsureClickListener != null) {
                                onEnsureClickListener.onEnsureClick();
                            }
                        })
                        .setNegativeButton("取消", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();

            });
        } else if(order.getState().equals("1")){
            orderState.setText("待商家出餐");
            Button cancel = view.findViewById(R.id.btn_user_tobeserved_cancel);
            cancel.setOnClickListener(v -> {

                new AlertDialog.Builder(context)
                        .setTitle("确认取消")
                        .setMessage("确定要取消订单吗？")
                        .setPositiveButton("确定", (dialog, which) -> {
                            OrderDAO.cancelOrder(order.getId());
                            if (onEnsureClickListener != null) {
                                onEnsureClickListener.onEnsureClick();
                            }
                        })
                        .setNegativeButton("取消", (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();

            });
        }else if(order.getState().equals("7")){
            orderState.setText("骑手配送中");
        }

        return view;
    }

    private int getLayoutIdForStatus(String status) {
        if (status.equals("7")) {
            return R.layout.adapter_user_finish6;
        }
        return R.layout.adapter_user_tobeserved;
    }
}