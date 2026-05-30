package com.example.orderfood.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfood.R;
import com.example.orderfood.adapter.FragmentAdapter;
import com.example.orderfood.dao.OrderDAO;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Business;
import com.example.orderfood.fragment.UserBuyCommentFragment;
import com.example.orderfood.fragment.UserBuyFoodFragment;
import com.example.orderfood.until.LoadImage;
import com.example.orderfood.until.UserUntil;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class UserBuyActivity extends BaseActivity {
    private Toolbar tb;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private FragmentAdapter FragmentAdapter;
    private Business business;
    private TextView shopName,shopDesc,shopMark,shopSale,businessId,tvFee;
    private ImageView shopImg;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        business=(Business) intent.getSerializableExtra("selectedBusiness");
        businessId.setText(business.getId());
        shopName.setText(business.getName());
        shopDesc.setText(business.getDescribe());
        tvFee.setText("配送费："+business.getDelivery());

        // 获取图片资源的ID并设置到ImageView
        LoadImage.loadImage(this,shopImg,business.getImg());

        shopMark.setText("评分："+UserDAO.getBusinessMark(business.getId()));
        shopSale.setText("月售："+ UserDAO.getBusinessSale(business.getId()));
        initNavigation();

    }

    @Override
    protected int initLayout() {
        return R.layout.activity_user_buy;
    }

    @Override
    protected void initView() {
        tb=findViewById(R.id.tb_user_buy);
        tabLayout=findViewById(R.id.tl_user_buy);
        viewPager2=findViewById(R.id.vp2_user_buy);
        shopName=findViewById(R.id.tv_user_buy_shopName);
        shopDesc=findViewById(R.id.tv_user_buy_shopDescribe);
        shopImg=findViewById(R.id.iv_user_buy_shopImg);
        shopMark=findViewById(R.id.tv_user_buy_mark);
        shopSale=findViewById(R.id.tv_user_buy_sale);
        businessId=findViewById(R.id.tv_user_buy_businessId);
        tvFee=findViewById(R.id.tv_user_buy_fee);
    }

    @Override
    protected void initData() {
        setSupportActionBar(tb);
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void initNavigation(){
        UserBuyFoodFragment userBuyFoodFragment=new UserBuyFoodFragment();
        UserBuyCommentFragment userBuyCommentFragment=new UserBuyCommentFragment();

        //将businessId传给显示商品的fragment
        Bundle bundle=new Bundle();
        bundle.putString("businessId",business.getId());
        userBuyFoodFragment.setArguments(bundle);

        Bundle bundle1=new Bundle();
        bundle1.putString("businessId",business.getId());
        userBuyCommentFragment.setArguments(bundle1);


        List<Fragment> fragmentList=new ArrayList<>();
        List<String> titleList=new ArrayList<>();
        fragmentList.add(userBuyFoodFragment);
        fragmentList.add(userBuyCommentFragment);
        titleList.add("商品");
        titleList.add("评价");

        FragmentAdapter=new FragmentAdapter(this,fragmentList,titleList);
        viewPager2.setAdapter(FragmentAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titleList.get(position));
            }
        }).attach();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position=tab.getPosition();
                viewPager2.setCurrentItem(position,false);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        // 获取当前显示的 Fragment 的位置
        int currentPosition = viewPager2.getCurrentItem();
        // 如果当前显示的不是首页，则切换到首页
        if (currentPosition != 0) {
            viewPager2.setCurrentItem(0, true); // 切换到首页（索引为 0）
        } else {
            // 如果当前已经在首页，则调用 super.onBackPressed() 返回到上一个 Activity
            super.onBackPressed();
        }
    }
}