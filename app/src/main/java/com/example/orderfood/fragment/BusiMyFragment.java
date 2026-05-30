package com.example.orderfood.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfood.R;
import com.example.orderfood.activity.BusiMyAllOrderActivity;
import com.example.orderfood.activity.BusiMyAwaitingActivity;
import com.example.orderfood.activity.BusiMyCancelOrderActivity;
import com.example.orderfood.activity.BusiMyCompleteOrderActivity;
import com.example.orderfood.activity.BusiMyUnderwayOrderActivity;
import com.example.orderfood.activity.BusiShowCommentActivity;
import com.example.orderfood.activity.BusiUpdatePwdActivity;
import com.example.orderfood.activity.BusiUpdateShopActivity;
import com.example.orderfood.activity.LoginActivity;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Business;
import com.example.orderfood.until.LoadImage;

public class BusiMyFragment extends Fragment {
    private TextView name,describe,tvScore;
    private View view;
    private ImageView img;
    private Button allOrder,cancelOrder,completeOrder,underwayOrder,awaitingOrder,showComment;
    private LinearLayout changeShopMessage,changeShopPwd,exit,logout;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    public BusiMyFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()== Activity.RESULT_OK){
                    loadData();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_busi_my, container, false);

        initView();
        loadData();
        initData();

        return view;
    }
    public void initView(){
        name=view.findViewById(R.id.tv_name_my);
        describe=view.findViewById(R.id.tv_describe_my);
        img=view.findViewById(R.id.iv_img_my);
        changeShopMessage=view.findViewById(R.id.ll_changeShopMessage_busi_my);
        changeShopPwd=view.findViewById(R.id.ll_changeShopPwd_busi_my);
        exit=view.findViewById(R.id.ll_exit_busi_my);
        logout=view.findViewById(R.id.ll_logout_busi_my);
        allOrder=view.findViewById(R.id.btn_busi_my_allOrder);
        underwayOrder=view.findViewById(R.id.btn_busi_my_underwayOrder);
        cancelOrder=view.findViewById(R.id.btn_busi_my_cancelOrder);
        completeOrder=view.findViewById(R.id.btn_busi_my_completeOrder);
        awaitingOrder=view.findViewById(R.id.btn_busi_my_awaitingOrder);
        tvScore=view.findViewById(R.id.tv_busi_my_score);
        showComment=view.findViewById(R.id.btn_busi_my_showComment);
    }
    public void initData(){
        allOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), BusiMyAllOrderActivity.class);
                startActivity(intent);
            }
        });
        awaitingOrder.setOnClickListener(v->{
            Intent intent=new Intent(getContext(), BusiMyAwaitingActivity.class);
            startActivity(intent);
        });
        showComment.setOnClickListener(v->{
            Intent intent=new Intent(getContext(), BusiShowCommentActivity.class);
            startActivity(intent);
        });
        underwayOrder.setOnClickListener(v->{
            Intent intent=new Intent(getContext(), BusiMyUnderwayOrderActivity.class);
            startActivity(intent);
        });
        cancelOrder.setOnClickListener(v->{
            Intent intent=new Intent(getContext(), BusiMyCancelOrderActivity.class);
            startActivity(intent);
        });
        completeOrder.setOnClickListener(v->{
            Intent intent=new Intent(getContext(), BusiMyCompleteOrderActivity.class);
            startActivity(intent);
        });
        changeShopMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), BusiUpdateShopActivity.class);
                activityResultLauncher.launch(intent);
            }
        });
        changeShopPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), BusiUpdatePwdActivity.class);
                startActivity(intent);
            }
        });
        exit.setOnClickListener(v->{
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            startActivity(intent);
            if (getActivity() != null) {
                getActivity().finishAffinity();
            }
        });
        logout.setOnClickListener(v->{
            new AlertDialog.Builder(requireActivity())
                    .setTitle("确认注销")
                    .setMessage("确定要注销登录吗？")
                    .setPositiveButton("确定", (dialog, which) -> {
                        performLogout();
                    })
                    .setNegativeButton("取消", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
        });
    }
    @SuppressLint("SetTextI18n")
    public void loadData(){
        Business business=new Business();
        business=UserDAO.getBusinessById(String.valueOf(getBusinessId(requireContext())));

        if(business!=null){
            name.setText(business.getName());
            describe.setText(business.getDescribe());
            if(business.getMark()==null){
                tvScore.setText("0");
            }else{
                tvScore.setText("评分："+business.getMark());
            }


            LoadImage.loadImage(getContext(),img,business.getImg());


        }else{
            name.setText("null");
        }

    }
    public static int getBusinessId(Context context){
        SharedPreferences sp=context.getSharedPreferences("BusinessId", Context.MODE_PRIVATE);
        return sp.getInt("businessId",-1);
    }

    private void performLogout() {
        String businessId=String.valueOf(getBusinessId(requireContext()));
        int requestCode=UserDAO.logout(businessId);
        if(requestCode==1){
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finishAffinity();
        }else{
            Toast.makeText(requireContext(), "注销失败", Toast.LENGTH_SHORT).show();
        }
    }

}