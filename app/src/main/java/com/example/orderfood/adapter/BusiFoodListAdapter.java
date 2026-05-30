package com.example.orderfood.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.orderfood.R;
import com.example.orderfood.dao.FoodDAO;
import com.example.orderfood.entity.Food;
import java.io.File;
import java.util.List;

//显示商家商品的adapter
public class BusiFoodListAdapter extends ArrayAdapter<Food>{
    private List<Food> list;
    private Context context;
    public BusiFoodListAdapter(@NonNull Context context, List<Food> list) {
        super(context,R.layout.adapter_busi_foodlist,list);
        this.context=context;
        this.list=list;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View view, ViewGroup viewGroup){
        if(view==null){
            LayoutInflater inflater=LayoutInflater.from(getContext());
            view=inflater.inflate(R.layout.adapter_busi_foodlist,viewGroup,false);
        }
        Food food=list.get(position);
        ImageView foodImg=view.findViewById(R.id.iv_foodImg);
        TextView foodName=view.findViewById(R.id.tv_foodName);
        TextView foodSales=view.findViewById(R.id.tv_foodSales);
        TextView foodPrice=view.findViewById(R.id.tv_foodPrice);
        TextView foodDes=view.findViewById(R.id.tv_foodDes);


        loadImage(getContext(),foodImg,food.getImg());

        foodName.setText(food.getName());
        foodSales.setText("已售："+FoodDAO.getFoodSales(food.getId()));
        foodPrice.setText("￥"+food.getPrice());
        foodDes.setText("详情："+food.getDescribe());

        return view;
    }

    public static void loadImage(Context context, ImageView imgView, String imgResource) {
        // 判断是否是资源ID
        //逻辑是相反的，有用就行
        if (isResourceID(imgResource)) {

            // 是图片路径，使用setImageURI
            imgView.setImageURI(Uri.parse(imgResource));


        } else {
            // 是资源ID，使用setImageResource
            int imgResourceId = context.getResources().getIdentifier(imgResource, "drawable", context.getPackageName());
            imgView.setImageResource(imgResourceId);
        }
    }
    private static boolean isResourceID(String imgResource) {
        // 假设资源ID是以 "R.drawable." 开头的字符串
        return imgResource.startsWith("/");
    }
}
