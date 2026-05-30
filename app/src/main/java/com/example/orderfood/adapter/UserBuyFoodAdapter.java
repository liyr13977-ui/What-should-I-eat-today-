package com.example.orderfood.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.orderfood.R;
import com.example.orderfood.activity.UserPaymentActivity;
import com.example.orderfood.dao.FoodDAO;
import com.example.orderfood.entity.Food;
import com.example.orderfood.until.LoadImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;

public class UserBuyFoodAdapter extends ArrayAdapter<Food> {
    private final Activity MyActivity;
    private Button settlement;
    private TextView businessId, totalPrice;
    private SharedPreferences cartSharedPreferences;

    public UserBuyFoodAdapter(@NonNull Context context, List<Food> list, Activity MyActivity,SharedPreferences cartSharedPreferences) {
        super(context, R.layout.adapter_user_buyfood, list);
        this.MyActivity = MyActivity;
        this.cartSharedPreferences=cartSharedPreferences;
        this.settlement = MyActivity.findViewById(R.id.btn_user_buy_settlement);
        this.businessId = MyActivity.findViewById(R.id.tv_user_buy_businessId);
        this.totalPrice = MyActivity.findViewById(R.id.tv_user_buy_totalPrice);
        Log.e("main","开始数据："+cartSharedPreferences.getString("cartItems","[]"));

        // 初始化结算按钮
        settlement.setOnClickListener(v -> handleSettlement());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.adapter_user_buyfood, viewGroup, false);
        }

        // 获取控件
        ImageView foodImg = view.findViewById(R.id.iv_user_buyfood_foodImg);
        TextView foodName = view.findViewById(R.id.tv_user_buyfood_foodName);
        TextView foodSales = view.findViewById(R.id.tv_user_buyfood_foodSales);
        TextView foodMark = view.findViewById(R.id.tv_user_buyfood_foodMark);
        TextView foodPrice = view.findViewById(R.id.tv_user_buyfood_foodPrice);
        final TextView foodNum = view.findViewById(R.id.tv_user_buyfood_foodNum);
        TextView foodDesc = view.findViewById(R.id.tv_user_buyfood_foodDesc);
        ImageView addFood = view.findViewById(R.id.iv_user_buyfood_addFood);
        ImageView subFood = view.findViewById(R.id.iv_user_buyfood_subFood);

        Food currentFood = getItem(position);

        LoadImage.loadImage(getContext(),foodImg,currentFood.getImg());


        int quantity = getFoodQuantityFromCart(currentFood.getId());
        foodNum.setText(String.valueOf(quantity));



        foodName.setText(currentFood.getName());
        foodSales.setText("月售："+ FoodDAO.getFoodSales(currentFood.getId()));
        foodMark.setText("评分：");
        foodPrice.setText("￥" + currentFood.getPrice());
        foodDesc.setText(currentFood.getDescribe());

        refreshTotalPrice();

        // 添加按钮点击事件
        addFood.setOnClickListener(v -> {
            v.clearFocus();
            addFood(currentFood);});

        // 减少按钮点击事件
        subFood.setOnClickListener(v -> {
            v.clearFocus();
            subFood(currentFood);
        });

        return view;
    }

    private JSONArray getCartItems() {
        String cartJson = cartSharedPreferences.getString("cartItems", "[]");
        try {
            return new JSONArray(cartJson);
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    private int getFoodQuantityFromCart(String foodId) {
        JSONArray cartItems = getCartItems();
        for (int i = 0; i < cartItems.length(); i++) {
            try {
                JSONObject item = cartItems.getJSONObject(i);
                if (item.getString("foodId").equals(foodId)) {
                    return item.getInt("foodNum");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    private void addFood(Food currentFood) {
        int currentNum = getFoodQuantityFromCart(currentFood.getId());
        currentNum++;
        updateCart(currentFood, currentNum);
        notifyDataSetChanged();
    }

    private void subFood(Food currentFood) {
        int currentNum = getFoodQuantityFromCart(currentFood.getId());
        if (currentNum > 0) {
            currentNum--;
            updateCart(currentFood, currentNum);
            notifyDataSetChanged();
        }
    }
    //旧方法

//    private void addFood(TextView foodNum, Food currentFood) {
//        int currentNum;
//        try {
//            currentNum = Integer.parseInt(foodNum.getText().toString());
//        } catch (NumberFormatException e) {
//            currentNum = 0;
//        }
//
//        currentNum++;
//        foodNum.setText(String.valueOf(currentNum));
//
//        BigDecimal price = new BigDecimal(currentFood.getPrice());
//        updateTotalPrice(price, true);
//        updateCart(currentFood, currentNum);
//    }
//
//    private void subFood(TextView foodNum, Food currentFood) {
//        int currentNum;
//        try {
//            currentNum = Integer.parseInt(foodNum.getText().toString());
//        } catch (NumberFormatException e) {
//            currentNum = 0;
//            foodNum.setText("0");
//            return;
//        }
//
//        if (currentNum > 0) {
//            currentNum--;
//            foodNum.setText(String.valueOf(currentNum));
//
//            BigDecimal price = new BigDecimal(currentFood.getPrice());
//            updateTotalPrice(price, false);
//
//            updateCart(currentFood, currentNum);
//        }
//    }

    private void updateCart(Food food, int num) {
        JSONArray cartItems = getCartItems();
        String foodId = food.getId();

        try {
            boolean itemExists = false;
            for (int i = 0; i < cartItems.length(); i++) {
                JSONObject item = cartItems.getJSONObject(i);
                if (item.getString("foodId").equals(foodId)) {
                    item.put("foodNum", num);
                    itemExists = true;
                    break;
                }
            }

            if (!itemExists && num > 0) {
                JSONObject newItem = new JSONObject();
                newItem.put("foodId", foodId);
                newItem.put("foodPrice", food.getPrice());
                newItem.put("foodNum", num);
                cartItems.put(newItem);
            } else if (num == 0) {
                // 如果数量为0，移除该商品
                for (int i = 0; i < cartItems.length(); i++) {
                    JSONObject item = cartItems.getJSONObject(i);
                    if (item.getString("foodId").equals(foodId)) {
                        cartItems.remove(i);
                        break;
                    }
                }
            }

            String updatedCartJson = cartItems.toString();
            saveCartItems(updatedCartJson);
            refreshTotalPrice();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void refreshTotalPrice() {
        BigDecimal total = BigDecimal.ZERO;
        JSONArray cartItems = getCartItems();

        for (int i = 0; i < cartItems.length(); i++) {
            try {
                JSONObject item = cartItems.getJSONObject(i);
                BigDecimal price = new BigDecimal(item.getString("foodPrice"));
                int quantity = item.getInt("foodNum");
                total = total.add(price.multiply(BigDecimal.valueOf(quantity)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        totalPrice.setText(total.toString());
    }

    private void saveCartItems(String cartJson) {
        SharedPreferences.Editor editor = cartSharedPreferences.edit();
        editor.putString("cartItems", cartJson);
        editor.apply();
    }

    private void handleSettlement() {
        String cartJson = cartSharedPreferences.getString("cartItems", "[]");
        if (cartJson.equals("[]")) {
            Toast.makeText(MyActivity, "未选择商品", Toast.LENGTH_SHORT).show();
        } else {
            String bID = businessId.getText().toString();
            Intent intent = new Intent(MyActivity, UserPaymentActivity.class);
            intent.putExtra("cartItems", cartJson);
            intent.putExtra("businessId", bID);
            MyActivity.startActivity(intent);
        }
    }

}