package com.example.orderfood.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

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
import com.example.orderfood.activity.LoginActivity;
import com.example.orderfood.activity.UserChangeInfoActivity;
import com.example.orderfood.activity.UserEditAddressActivity;
import com.example.orderfood.activity.UserMyAllOrderActivity;
import com.example.orderfood.activity.UserMyAwaitingActivity;
import com.example.orderfood.activity.UserMyCancelActivity;
import com.example.orderfood.activity.UserMyFinishActivity;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Business;
import com.example.orderfood.entity.User;
import com.example.orderfood.until.LoadImage;
import com.example.orderfood.until.UserUntil;

public class UserMyFragment extends Fragment {
    private View view;
    private Button finishOrder,awaiting,cancel,awaitComment;
    private LinearLayout deliveryAddress,exit,logout,changeInfo;
    private TextView tvName,tvAccount;
    private ImageView ivUserImg;

    public UserMyFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_user_my, container, false);

        initView();
        loadData();
        initData();

        return view;
    }
    private void initView(){
        finishOrder=view.findViewById(R.id.btn_user_my_finishOrder);
        awaiting=view.findViewById(R.id.btn_user_my_awaiting);
        cancel=view.findViewById(R.id.btn_user_my_cancel);
        awaitComment=view.findViewById(R.id.btn_user_my_awaitComment);
        deliveryAddress=view.findViewById(R.id.ll_user_my_deliveryAddress);
        logout=view.findViewById(R.id.ll_user_my_logout);
        exit=view.findViewById(R.id.ll_user_my_exit);
        tvName=view.findViewById(R.id.tv_user_my_name);
        ivUserImg=view.findViewById(R.id.iv_user_my_img);
        changeInfo=view.findViewById(R.id.ll_changeShopPwd_user_my);
        tvAccount=view.findViewById(R.id.tv_user_my_account);

    }
    @SuppressLint("SetTextI18n")
    private void loadData(){
        String userId=String.valueOf(UserUntil.getUserId(requireContext()));
        User user=UserDAO.getUserById(userId);
        if (user != null) {
            tvName.setText(user.getNickName());
            tvAccount.setText("账号："+user.getAccount());
            LoadImage.loadImage(getContext(),ivUserImg,user.getImg());
        }

    }
    private void initData(){
        finishOrder.setOnClickListener(v->{
            Intent intent=new Intent(getContext(), UserMyAllOrderActivity.class);
            startActivity(intent);
        });
        changeInfo.setOnClickListener(v->{
            Intent intent=new Intent(getContext(), UserChangeInfoActivity.class);
            startActivity(intent);
        });
        awaiting.setOnClickListener(v->{
            Intent intent=new Intent(getContext(), UserMyAwaitingActivity.class);
            startActivity(intent);
        });
        cancel.setOnClickListener(v->{
            Intent intent=new Intent(getContext(), UserMyCancelActivity.class);
            startActivity(intent);
        });
        awaitComment.setOnClickListener(v->{
            //FinishActivity就是待评论,state为3
            Intent intent=new Intent(getContext(), UserMyFinishActivity.class);
            startActivity(intent);
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
        deliveryAddress.setOnClickListener(v->{
            Intent intent = new Intent(requireContext(), UserEditAddressActivity.class);
            startActivity(intent);
        });

    }
    private void performLogout() {
        String businessId=String.valueOf(UserUntil.getBusinessId(requireContext()));
        int requestCode= UserDAO.logout(businessId);
        if(requestCode==1){
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finishAffinity();
        }else{
            Toast.makeText(requireContext(), "注销失败", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}