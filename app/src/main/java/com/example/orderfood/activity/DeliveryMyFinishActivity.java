package com.example.orderfood.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.orderfood.R;
import com.example.orderfood.adapter.DeliveryDCAdapter;
import com.example.orderfood.adapter.DeliveryFoodListAdapter;
import com.example.orderfood.dao.OrderDAO;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Delivery;
import com.example.orderfood.entity.Order;
import com.example.orderfood.until.UserUntil;

import java.util.List;

public class DeliveryMyFinishActivity extends BaseActivity{
    private SwipeRefreshLayout sr;
    private ListView lvOrderList;
    private List<Order> orderList;
    private DeliveryFoodListAdapter adapter;
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
        String deliveryManId= String.valueOf(UserUntil.getDeliveryManId(this));
        Delivery deliveryMan= UserDAO.getDeliveryManById(deliveryManId);
        if (deliveryMan != null) {
            String deliveryManName=deliveryMan.getName();
            List<Order> orderList1 = OrderDAO.getFinishOrderByDelivery(deliveryManName);
            adapter=new DeliveryFoodListAdapter(this,orderList1);
            lvOrderList.setAdapter(adapter);
        }


    }

    private void refreshData() {
        new Handler().postDelayed(() -> {
            loadData();
            sr.setRefreshing(false);
        }, 1000);
    }

}