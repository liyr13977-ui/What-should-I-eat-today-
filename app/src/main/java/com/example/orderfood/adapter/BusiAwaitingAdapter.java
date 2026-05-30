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

public class BusiAwaitingAdapter extends BaseAdapter {
    private Context context;
    private List<Order> orderList;
    private OnEnsureClickListener onEnsureClickListener;
    private Order order;

    public interface OnEnsureClickListener {
        void onEnsureClick();
    }

    public void setOnEnsureClickListener(OnEnsureClickListener listener) {
        this.onEnsureClickListener = listener;
    }

    public BusiAwaitingAdapter(@NonNull Context context, List<Order> orderList) {
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
        order = orderList.get(position);
        @SuppressLint("ViewHolder") View view = LayoutInflater.from(context).inflate(R.layout.adapter_busi_tobeserved, viewGroup, false);

        ImageView businessImg = view.findViewById(R.id.businessImg);
        TextView businessName = view.findViewById(R.id.businessName);
        ImageView foodImg = view.findViewById(R.id.foodImg);
        TextView foodName = view.findViewById(R.id.foodName);
        TextView number = view.findViewById(R.id.number);
        TextView price = view.findViewById(R.id.price);
        TextView date = view.findViewById(R.id.date);
        TextView orderState = view.findViewById(R.id.orderState);
        Button ensure = view.findViewById(R.id.btn_busi_tobeserved_ensureFood);
        Button cancel = view.findViewById(R.id.btn_busi_tobeserved_cancel);

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

        int quantity = Integer.parseInt(foodOrderInfo[0]);
        double pricePerUnit = Double.parseDouble(foodOrderInfo[1]);
        number.setText("共" + foodOrderInfo[0] + "件");
        price.setText("￥" + String.format("%.2f", pricePerUnit));
        orderState.setText("待出餐");

        date.setText(order.getDate());


        ensure.setOnClickListener(v -> {
            OrderDAO.ensureFood(order.getId());
            if (onEnsureClickListener != null) {
                onEnsureClickListener.onEnsureClick();
            }
        });

        cancel.setOnClickListener(v -> {
            OrderDAO.cancelOrder(order.getId());
            if (onEnsureClickListener != null) {
                onEnsureClickListener.onEnsureClick();
            }
        });

        return view;
    }

}