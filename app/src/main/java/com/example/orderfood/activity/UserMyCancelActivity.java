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
import com.example.orderfood.adapter.UserAwaitingAdapter;
import com.example.orderfood.adapter.UserCancelAdapter;
import com.example.orderfood.adapter.UserFoodListAdapter;
import com.example.orderfood.dao.OrderDAO;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Business;
import com.example.orderfood.entity.Food;
import com.example.orderfood.entity.Order;

import java.util.List;

public class UserMyCancelActivity extends BaseActivity {
    private SwipeRefreshLayout refreshLayout;
    private ListView orderList;
    private List<Food> Data;
    private UserCancelAdapter adapter;
    private SearchView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_user_my_cancel;
    }

    @Override
    protected void initView() {
        orderList=findViewById(R.id.lv_user_my_cancel_foodlist);
        refreshLayout=findViewById(R.id.swipe_user_my_cancel);
        sv=findViewById(R.id.sv_user_my_cancel);
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
                    Intent intent = new Intent(UserMyCancelActivity.this, OrderInfoActivity.class);
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
        List<Order> orderList1 = OrderDAO.getCancelOrder(String.valueOf(getUserId(this)));
        adapter=new UserCancelAdapter(this,orderList1);
        orderList.setAdapter(adapter);

    }

    private void refreshData() {
        // 模拟网络请求延迟
        new Handler().postDelayed(() -> {
            // 重新加载数据
            loadData();
            // 刷新完成后，停止刷新动画
            refreshLayout.setRefreshing(false);
        }, 1000);
    }

    public static int getUserId(Context context){
        SharedPreferences sp=context.getSharedPreferences("userId", Context.MODE_PRIVATE);
        return sp.getInt("userId",-1);
    }
}