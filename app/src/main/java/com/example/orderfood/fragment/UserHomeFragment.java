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
import com.example.orderfood.activity.UserBuyActivity;
import com.example.orderfood.adapter.BusiFoodListAdapter;
import com.example.orderfood.adapter.UserFoodListAdapter;
import com.example.orderfood.dao.FoodDAO;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Business;
import com.example.orderfood.entity.Food;

import java.util.List;


public class UserHomeFragment extends Fragment {
    private SwipeRefreshLayout refreshLayout;
    private ListView foodList;
    private UserFoodListAdapter adapter;
    private View view;
    private SearchView sv;

    public UserHomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_user_home,container,false);
        initView();
        initData();
        loadData();
        return view;
    }
    public void initView(){
        foodList=view.findViewById(R.id.lv_user_food_home);
        refreshLayout=view.findViewById(R.id.swipe_user_refresh_home);
        sv=view.findViewById(R.id.sv_user_home);
    }
    public void initData(){
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        foodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UserFoodListAdapter adapter1=(UserFoodListAdapter) adapterView.getAdapter();
                if(adapter1!=null){
                    Food selectedFood=(Food)adapter1.getItem(i);
                    String businessId=selectedFood.getBusinessId();
                    Business selectedBusiness=UserDAO.getBusinessById(businessId);
                    Intent intent=new Intent(getContext(), UserBuyActivity.class);
                    intent.putExtra("selectedBusiness",selectedBusiness);
                    startActivity(intent);
                }
            }
        });
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.isEmpty()) {
                    loadData(); // 如果输入为空，加载默认数据
                } else {
                    List<Food> queryResult = FoodDAO.queryFood_User(s);
                    List<Business> queryResult1=UserDAO.getAllBusi();
                    adapter = new UserFoodListAdapter(getContext(), queryResult,queryResult1);
                    foodList.setAdapter(adapter);
                }
                return false;
            }
        });
    }

    public void loadData(){
        List<Food> foodData = FoodDAO.getAllFoodUser();
        List<Business> busiDate = UserDAO.getAllBusi();
        adapter=new UserFoodListAdapter(requireContext(), foodData, busiDate);
        if(foodData.size() == 0){
            foodList.setAdapter(null);
        }else{
            foodList.setAdapter(adapter);
        }
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

}