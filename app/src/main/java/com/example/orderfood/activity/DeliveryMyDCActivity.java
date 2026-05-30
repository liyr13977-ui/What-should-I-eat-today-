package com.example.orderfood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.orderfood.R;
import com.example.orderfood.adapter.DeliveryDCAdapter;
import com.example.orderfood.adapter.UserAllOrderAdapter;
import com.example.orderfood.dao.OrderDAO;
import com.example.orderfood.entity.Food;
import com.example.orderfood.entity.Order;

import java.util.List;

public class DeliveryMyDCActivity extends BaseActivity implements DeliveryDCAdapter.OnDeliveryOrderListener{
    private SwipeRefreshLayout sr;
    private ListView lvOrderList;
    private List<Order> orderList;
    private DeliveryDCAdapter adapter;
    private SearchView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_delivery_my_dcactivity;
    }

    @Override
    protected void initView() {
        sr=findViewById(R.id.sr_delivery_MyDC);
        lvOrderList=findViewById(R.id.lv_delivery_MyDC_orderList);
        sv=findViewById(R.id.sv_delivery_MyDC);
    }

    @Override
    protected void initData() {
        loadData();
        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
//        lvOrderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Object selectedItem=adapterView.getItemAtPosition(i);
//                if(selectedItem instanceof Order){
//                    Order selectedOrder=(Order)selectedItem;
//                    String orderId=selectedOrder.getId();
//                    Intent intent=new Intent(DeliveryMyDCActivity.this, DeliveryOrderInfoActivity.class);
//                    intent.putExtra("orderId",orderId);
//                    startActivity(intent);
//                }
//            }
//        });
    }
    public void loadData(){
        List<Order> orderList1 = OrderDAO.getOrder7();
        adapter=new DeliveryDCAdapter(this,orderList1);
        lvOrderList.setAdapter(adapter);
        adapter.setOnDeliveryOrderListener(this);

    }

    private void refreshData() {
        new Handler().postDelayed(() -> {
            loadData();
            sr.setRefreshing(false);
        }, 1000);
    }

    @Override
    public void onCancelOrder(String orderId) {
        OrderDAO.cancelOrderDelivery(orderId);
        Toast.makeText(this, "订单已取消", Toast.LENGTH_SHORT).show();
        loadData();

    }

    @Override
    public void onConfirmOrder(String orderId) {
        OrderDAO.ensureOrder(orderId);
        Toast.makeText(this, "订单已确认送达", Toast.LENGTH_SHORT).show();
        loadData();
    }
}