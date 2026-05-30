package com.example.orderfood.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.orderfood.R;
import com.example.orderfood.activity.BusiUpdateFoodActivity;
import com.example.orderfood.activity.DeliveryOrderInfoActivity;
import com.example.orderfood.activity.UserBuyActivity;
import com.example.orderfood.adapter.BusiFoodListAdapter;
import com.example.orderfood.adapter.DeliveryFoodListAdapter;
import com.example.orderfood.adapter.UserFoodListAdapter;
import com.example.orderfood.dao.FoodDAO;
import com.example.orderfood.dao.OrderDAO;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Business;
import com.example.orderfood.entity.Food;
import com.example.orderfood.entity.Order;

import java.util.List;


public class DeliveryHomeFragment extends Fragment {
    private SwipeRefreshLayout refreshLayout;
    private ListView lvOrderList;
    private DeliveryFoodListAdapter adapter;
    private View view;
    private SearchView sv;

    public DeliveryHomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_delivery_home,container,false);
        initView();
        initData();
        loadData();
        return view;
    }
    public void initView(){
        lvOrderList=view.findViewById(R.id.lv_deliveryHome_orderList);
        refreshLayout=view.findViewById(R.id.refresh_deliveryHome);
        sv=view.findViewById(R.id.sv_deliveryHome);
    }
    public void initData(){
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        lvOrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object selectedItem=adapterView.getItemAtPosition(i);
                if(selectedItem instanceof Order){
                    Order selectedOrder=(Order)selectedItem;
                    String orderId=selectedOrder.getId();
                    Intent intent=new Intent(getContext(), DeliveryOrderInfoActivity.class);
                    intent.putExtra("orderId",orderId);
                    startActivity(intent);
                }
            }
        });
//        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                if (s.isEmpty()) {
//                    loadData(); // 如果输入为空，加载默认数据
//                } else {
//                    List<Food> queryResult = FoodDAO.queryFood_User(s);
//                    List<Business> queryResult1=UserDAO.getAllBusi();
//                    adapter = new UserFoodListAdapter(getContext(), queryResult,queryResult1);
//                    foodList.setAdapter(adapter);
//                }
//                return false;
//            }
//        });
    }

    public void loadData(){
        List<Order> orderList = OrderDAO.getDeliveryOrderList();
        adapter=new DeliveryFoodListAdapter(requireContext(), orderList);
        if(orderList.size() == 0){
            lvOrderList.setAdapter(null);
        }else{
            lvOrderList.setAdapter(adapter);
        }
    }
    private void refreshData() {
        new Handler().postDelayed(() -> {
            loadData();
            refreshLayout.setRefreshing(false);
        }, 1000);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }


}