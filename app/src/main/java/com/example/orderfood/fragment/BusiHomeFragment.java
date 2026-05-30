package com.example.orderfood.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.orderfood.R;
import com.example.orderfood.activity.BusiUpdateFoodActivity;
import com.example.orderfood.adapter.BusiFoodListAdapter;
import com.example.orderfood.dao.FoodDAO;
import com.example.orderfood.entity.Food;

import java.util.List;


public class BusiHomeFragment extends Fragment {
    private SwipeRefreshLayout refreshLayout;
    private ListView foodList;
    private List<Food> Data;
    private BusiFoodListAdapter adapter;
    private View view;
    private SearchView sv;

    public BusiHomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_busi_home,container,false);
        initView();
        initData();
        return view;
    }
    public void initView(){
        foodList=view.findViewById(R.id.lv_food_home);
        refreshLayout=view.findViewById(R.id.swipe_refresh_home);
        sv=view.findViewById(R.id.sv_business_home);
    }
    public void initData(){
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        loadData();

        foodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BusiFoodListAdapter adapter1=(BusiFoodListAdapter) adapterView.getAdapter();
                if(adapter1!=null){
                    Food selectedFood=(Food)adapter1.getItem(i);
                    if(selectedFood!=null){
                        Intent intent=new Intent(getContext(), BusiUpdateFoodActivity.class);
                        intent.putExtra("Food",selectedFood);
                        startActivity(intent);
                    }
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
                    List<Food> queryResult = FoodDAO.queryFood_Business(s,String.valueOf(getBusinessId(getContext())));
                    adapter = new BusiFoodListAdapter(getContext(), queryResult);
                    foodList.setAdapter(adapter);
                }
                return false;
            }
        });
    }

    public void loadData(){
        Data=FoodDAO.getAllFoodBusi(getBusinessId(getContext()));
        adapter=new BusiFoodListAdapter(getContext(),Data);
        if(Data==null||Data.size()==0){
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

    public static int getBusinessId(Context context){
        SharedPreferences sp=context.getSharedPreferences("BusinessId", Context.MODE_PRIVATE);
        return sp.getInt("businessId",-1);
    }
}