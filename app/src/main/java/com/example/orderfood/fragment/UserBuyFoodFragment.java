package com.example.orderfood.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfood.R;
import com.example.orderfood.activity.UserPaymentActivity;
import com.example.orderfood.adapter.BusiFoodListAdapter;
import com.example.orderfood.adapter.UserBuyFoodAdapter;
import com.example.orderfood.adapter.UserFoodListAdapter;
import com.example.orderfood.dao.FoodDAO;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Business;
import com.example.orderfood.entity.Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;

public class UserBuyFoodFragment extends Fragment {
    private ListView foodList;
    private List<Food> FoodData;
    private UserBuyFoodAdapter adapter;
    private View view;
    private SharedPreferences cartSharedPreferences;

    public UserBuyFoodFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_buy_food, container, false);

        foodList = view.findViewById(R.id.lv_user_buy_food);
        cartSharedPreferences = requireActivity().getSharedPreferences("cartItems", Context.MODE_PRIVATE);


        foodList.setItemsCanFocus(false);
        foodList.setFocusable(false);
        foodList.setFocusableInTouchMode(false);

        // 设置滚动监听，滚动时清除焦点
        foodList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    view.clearFocus();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        loadData();
        return view;
    }


    public void loadData() {
        // 接收从UserBuyActivity传过来的businessId
        if (getArguments() != null) {
            String businessId = getArguments().getString("businessId");
            FoodData = FoodDAO.getAllFoodBusi(Integer.parseInt(businessId));
        }

        // 清空购物车数据
        clearCartSharedPreferences();

        adapter = new UserBuyFoodAdapter(requireContext(), FoodData, requireActivity(), cartSharedPreferences // 传递标志，表示需要清空购物车
        );

        if (FoodData == null || FoodData.size() == 0) {
            foodList.setAdapter(null);
        } else {
            foodList.setAdapter(adapter);
        }
    }

    private void clearCartSharedPreferences() {
        SharedPreferences.Editor editor = cartSharedPreferences.edit();
        editor.clear(); // 清空所有数据
        editor.apply(); // 提交更改
    }
}