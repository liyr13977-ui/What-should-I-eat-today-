package com.example.orderfood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.orderfood.R;
import com.example.orderfood.adapter.BusiFoodListAdapter;
import com.example.orderfood.adapter.UserAllOrderAdapter;
import com.example.orderfood.adapter.UserAwaitingAdapter;
import com.example.orderfood.adapter.UserFoodListAdapter;
import com.example.orderfood.dao.FoodDAO;
import com.example.orderfood.dao.OrderDAO;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Business;
import com.example.orderfood.entity.Food;
import com.example.orderfood.entity.Order;

import java.util.List;

public class UserMyAllOrderActivity extends BaseActivity implements UserAllOrderAdapter.OnCancelOrderListener{
    private SwipeRefreshLayout refreshLayout;
    private ListView orderList;
    private List<Food> Data;
    private UserAllOrderAdapter adapter;
    private SearchView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_user_my_all_order;
    }

    @Override
    protected void initView() {
        orderList=findViewById(R.id.lv_user_my_allOrder_foodlist);
        refreshLayout=findViewById(R.id.swipe_user_my_allOrder);
        sv=findViewById(R.id.sv_user_my_allOrder);
    }

    @Override
    protected void initData() {
        loadData();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object selectedItem=adapterView.getItemAtPosition(i);
                if(selectedItem instanceof Order){
                    Order selectedOrder=(Order)selectedItem;
                    String orderId=selectedOrder.getId();
                    Intent intent=new Intent(UserMyAllOrderActivity.this, OrderInfoActivity.class);
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
//                    List<Food> queryResult = FoodDAO.queryFood_Business(s,String.valueOf(getBusinessId(getContext())));
//                    adapter = new BusiFoodListAdapter(UserMyAllOrderActivity.this, queryResult);
//                    foodList.setAdapter(adapter);
//                }
//                return false;
//            }
//        });
    }

    public void loadData(){
        List<Order> orderList1 = OrderDAO.getUserOrder6(String.valueOf(getUserId(this)));
        adapter=new UserAllOrderAdapter(this,orderList1);
        orderList.setAdapter(adapter);
        adapter.setOnCancelOrderListener(this);

    }

    private void refreshData() {
        new Handler().postDelayed(() -> {
            loadData();
            refreshLayout.setRefreshing(false);
        }, 1000);
    }

    public static int getUserId(Context context){
        SharedPreferences sp=context.getSharedPreferences("userId", Context.MODE_PRIVATE);
        return sp.getInt("userId",-1);
    }

    @Override
    public void onCancelOrder() {
        loadData();
    }
}