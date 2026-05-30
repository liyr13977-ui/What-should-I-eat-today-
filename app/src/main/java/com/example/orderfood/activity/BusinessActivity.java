package com.example.orderfood.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.orderfood.R;
import com.example.orderfood.adapter.FragmentAdapter;
import com.example.orderfood.fragment.BusiAddFragment;
import com.example.orderfood.fragment.BusiHomeFragment;
import com.example.orderfood.fragment.BusiMyFragment;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BusinessActivity extends BaseActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private FragmentAdapter FragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //当选择输入框时，使底部的tablayout始终保持在底部，不会随着软键盘的弹出而改变位置
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_business;
    }

    @Override
    protected void initView() {
        tabLayout=findViewById(R.id.tl_Business);
        viewPager2=findViewById(R.id.vp2_Business);
        initNavigation();
    }

    @Override
    protected void initData() {

    }
    private void initNavigation(){
        List<Fragment> fragmentList=new ArrayList<>();
        List<String> titleList=new ArrayList<>();
        List<Integer> iconList=new ArrayList<>();
        fragmentList.add(new BusiHomeFragment());
        fragmentList.add(new BusiAddFragment());
        fragmentList.add(new BusiMyFragment());
        titleList.add("商品");
        titleList.add("添加");
        titleList.add("我的");
        iconList.add(R.drawable.foodhome);
        iconList.add(R.drawable.add);
        iconList.add(R.drawable.my);

        FragmentAdapter=new FragmentAdapter(this,fragmentList,titleList);
        viewPager2.setAdapter(FragmentAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titleList.get(position));
                tab.setIcon(iconList.get(position));
            }
        }).attach();

        //禁用tablayout的滑动动画
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