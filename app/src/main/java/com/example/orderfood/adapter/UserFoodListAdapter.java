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
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Business;
import com.example.orderfood.entity.Food;
import com.example.orderfood.until.LoadImage;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//显示用户商品的adapter
public class UserFoodListAdapter extends ArrayAdapter<Food>{
    private final List<Food> listFood;
    private final List<Business> listBusi;
    private Context context;
    private Map<String, Business> businessMap; // 用于快速查找 Business 数据

    public UserFoodListAdapter(@NonNull Context context, List<Food> listFood, List<Business> listBusi) {
        super(context, R.layout.adapter_user_foodlist,listFood);
        this.context=context;
        this.listFood=listFood;
        this.listBusi=listBusi;
        businessMap = new HashMap<>();
        if (listBusi != null) {
            for (Business business : listBusi) {
                businessMap.put(business.getId(), business);
            }
        }

    }
    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, View view, @NonNull ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater inflater=LayoutInflater.from(getContext());
            view=inflater.inflate(R.layout.adapter_user_foodlist,viewGroup,false);
        }
        Food food=listFood.get(position);
        String businessId = food.getBusinessId();
        Business business = businessMap.get(businessId);

        ImageView foodImg=view.findViewById(R.id.iv_foodImg_User);
        TextView foodName=view.findViewById(R.id.tv_foodName_User);
        TextView foodPrice=view.findViewById(R.id.tv_foodPrice_User);
        TextView foodDes=view.findViewById(R.id.tv_foodDes_User);
        ImageView BusiImg=view.findViewById(R.id.tv_BusiImg_User);
        TextView BusiName=view.findViewById(R.id.tv_BusiName_User);
        TextView ShopMark=view.findViewById(R.id.tv_ShopMark_User);
        TextView foodSales=view.findViewById(R.id.tv_foodSales_User);


        LoadImage.loadImage(getContext(),foodImg,food.getImg());



        foodName.setText(food.getName());
        foodPrice.setText("￥"+food.getPrice());
        foodDes.setText(food.getDescribe());


        if (business != null) {

            LoadImage.loadImage(getContext(),BusiImg,business.getImg());
            BusiName.setText(business.getName());
            ShopMark.setText("评分："+ UserDAO.getBusinessMark(businessId));
            foodSales.setText("月售"+FoodDAO.getFoodSales(food.getId())+"份");
        } else {
            BusiName.setText("商家信息未找到");
        }
        return view;
    }
}
