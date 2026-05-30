package com.example.orderfood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.orderfood.R;
import com.example.orderfood.adapter.UserBuyComment1Adapter;
import com.example.orderfood.dao.OrderDAO;
import com.example.orderfood.entity.Order;
import com.example.orderfood.until.UserUntil;

import java.util.List;

public class BusiShowCommentActivity extends BaseActivity {
    private ListView lvOrderList;
    private UserBuyComment1Adapter adapter;
    private Toolbar tbTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadData();
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_busi_show_comment;
    }

    @Override
    protected void initView() {
        lvOrderList=findViewById(R.id.lv_busi_buyComment_OrderList);
        tbTitle=findViewById(R.id.tb_busi_showComment);
    }

    @Override
    protected void initData() {
        tbTitle.setOnClickListener(v->{
            onBackPressed();
        });

    }


    private void loadData(){
        String businessId=String.valueOf(UserUntil.getBusinessId(this));
        List<Order> orderList= OrderDAO.getBusiOrderDown(businessId);
        adapter=new UserBuyComment1Adapter(this,orderList);
        if(orderList.size() == 0){
            lvOrderList.setAdapter(null);
        }else{
            lvOrderList.setAdapter(adapter);
        }
    }
}