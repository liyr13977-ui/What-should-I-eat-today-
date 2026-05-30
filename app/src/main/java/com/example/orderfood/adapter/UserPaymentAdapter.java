package com.example.orderfood.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.orderfood.R;
import com.example.orderfood.entity.Business;
import com.example.orderfood.entity.Food;
import com.example.orderfood.until.LoadImage;

import org.w3c.dom.Text;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

public class UserPaymentAdapter extends ArrayAdapter<Food> {
    private Context context;
    private final List<Food> foodlist;

    public UserPaymentAdapter(@NonNull Context context, List<Food> listFood) {
        super(context, R.layout.adapter_user_payment,listFood);
        this.context=context;
        this.foodlist=listFood;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup viewGroup) {
        if (view==null){
            LayoutInflater inflater=LayoutInflater.from(getContext());
            view=inflater.inflate(R.layout.adapter_user_payment,viewGroup,false);
        }


        Food food=foodlist.get(position);

        ImageView foodImg=view.findViewById(R.id.iv_user_payment_foodImg);
        TextView foodName = view.findViewById(R.id.tv_user_payment_foodName);
        TextView foodPrice = view.findViewById(R.id.tv_user_payment_foodPrice);
        TextView foodNum = view.findViewById(R.id.tv_user_payment_foodNum);

        String fPrice= food.getPrice();
        String fNum=food.getNumber();
        BigDecimal fPrice1=new BigDecimal(fPrice);
        BigDecimal fNum1=new BigDecimal(fNum);

        BigDecimal signalFoodPrice=fPrice1.multiply(fNum1);

        foodName.setText(food.getName());
        foodPrice.setText("￥"+signalFoodPrice);
        foodNum.setText("×"+food.getNumber());

        LoadImage.loadImage(context,foodImg,food.getImg());


        return view;
    }
}
