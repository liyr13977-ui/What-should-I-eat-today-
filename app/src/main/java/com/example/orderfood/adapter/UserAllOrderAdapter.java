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
import android.widget.BaseAdapter;
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

public class UserAllOrderAdapter extends BaseAdapter {
    private Context context;
    private List<Order> orderList;
    private OnCancelOrderListener onCancelOrderListener;

    public UserAllOrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    public interface OnCancelOrderListener {
        void onCancelOrder();
    }

    public void setOnCancelOrderListener(OnCancelOrderListener onCancelOrderListener) {
        this.onCancelOrderListener = onCancelOrderListener;
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
        @SuppressLint("ViewHolder") View view=LayoutInflater.from(context).inflate(R.layout.adapter_user_finish,viewGroup,false);

        ImageView businessImg=view.findViewById(R.id.businessImg);
        TextView businessName=view.findViewById(R.id.businessName);
        ImageView foodImg = view.findViewById(R.id.foodImg);
        TextView foodName = view.findViewById(R.id.foodName);
        TextView number = view.findViewById(R.id.number);
        TextView price = view.findViewById(R.id.price);
        TextView date=view.findViewById(R.id.date);
        TextView orderState=view.findViewById(R.id.orderState);

        Business business= UserDAO.getBusinessById(order.getBusinessId());
        if (business != null) {
            LoadImage.loadImage(context,businessImg,business.getImg());
            businessName.setText(business.getName());
        }
        date.setText(order.getDate());


        //加载订单详情
        Food food=OrderDAO.getOneFoodBasicInfo(order.getId());
        LoadImage.loadImage(context, foodImg,food.getImg());
        foodName.setText(food.getName());
        String[] foodOrderInfo = new String[2];
        foodOrderInfo=OrderDAO.getOneFoodBasicInfo2(order.getId());

        double pricePerUnit = Double.parseDouble(foodOrderInfo[1]);
        double delivery=OrderDAO.getDeliveryByOrder(order.getId());
        double totalAmount=delivery+pricePerUnit;
        number.setText("共"+foodOrderInfo[0]+"件");
        price.setText("￥"+String.format("%.2f", totalAmount));

        if(order.getState().equals("3")){
            orderState.setText("待评论");
            Button btn=view.findViewById(R.id.btn_user_finish_commentOrder);
            btn.setText("去评论");
            btn.setOnClickListener(v->{
                Intent intent=new Intent(context, UserCommentOrderActivity.class);
                intent.putExtra("orderId",order.getId());
                context.startActivity(intent);
            });
        }else{
            orderState.setText("已完成");
            Button btn=view.findViewById(R.id.btn_user_finish_commentOrder);
            btn.setText("查看评论");
            btn.setOnClickListener(v->{
                Intent intent=new Intent(context, UserCommentOrderActivity.class);
                intent.putExtra("orderId",order.getId());
                intent.putExtra("viewOnly",true);
                context.startActivity(intent);

            });
        }

        return view;
    }
}
