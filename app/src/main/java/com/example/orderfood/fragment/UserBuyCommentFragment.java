package com.example.orderfood.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfood.R;
import com.example.orderfood.activity.UserPaymentActivity;
import com.example.orderfood.adapter.BusiFoodListAdapter;
import com.example.orderfood.adapter.UserBuyComment1Adapter;
import com.example.orderfood.adapter.UserBuyFoodAdapter;
import com.example.orderfood.adapter.UserFoodListAdapter;
import com.example.orderfood.dao.FoodDAO;
import com.example.orderfood.dao.OrderDAO;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Business;
import com.example.orderfood.entity.Food;
import com.example.orderfood.entity.Order;
import com.example.orderfood.until.UserUntil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;

public class UserBuyCommentFragment extends Fragment{
    private ListView lvOrderList;
    private UserBuyComment1Adapter adapter;
    private View view;

    public UserBuyCommentFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_user_buy_comment, container, false);
        initView();
        initData();
        loadData();
        return view;
    }
    public void initView(){
        lvOrderList=view.findViewById(R.id.lv_user_buyComment_OrderList);
    }
    public void initData(){

    }
    public void loadData(){
        String businessId= null;
        if (getArguments() != null) {
            businessId = getArguments().getString("businessId");
        }
        List<Order> orderList= OrderDAO.getBusiOrderComment(businessId);
        adapter=new UserBuyComment1Adapter(requireContext(),orderList);
        if(orderList.size() == 0){
            lvOrderList.setAdapter(null);
        }else{
            lvOrderList.setAdapter(adapter);
        }
    }

}