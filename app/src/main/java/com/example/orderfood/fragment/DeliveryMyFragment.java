package com.example.orderfood.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfood.R;
import com.example.orderfood.activity.DeliveryChangeInfoActivity;
import com.example.orderfood.activity.DeliveryMyDCActivity;
import com.example.orderfood.activity.DeliveryMyFinishActivity;
import com.example.orderfood.activity.LoginActivity;
import com.example.orderfood.activity.UserEditAddressActivity;
import com.example.orderfood.activity.UserMyAllOrderActivity;
import com.example.orderfood.activity.UserMyAwaitingActivity;
import com.example.orderfood.activity.UserMyCancelActivity;
import com.example.orderfood.activity.UserMyFinishActivity;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Delivery;
import com.example.orderfood.until.UserUntil;

public class DeliveryMyFragment extends Fragment {
    private View view;
    private Button awaiting,cancel,finish;
    private LinearLayout changeInfo,exit,logout;
    private TextView tvName,tvIntro;

    public DeliveryMyFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_delivery_my, container, false);

        initView();
        loadData();
        initData();

        return view;
    }
    private void initView(){
        awaiting=view.findViewById(R.id.btn_delivery_my_awaiting);
        finish=view.findViewById(R.id.btn_delivery_my_finish);
        changeInfo=view.findViewById(R.id.ll_delivery_my_changeInfo);
        logout=view.findViewById(R.id.ll_delivery_my_logout);
        exit=view.findViewById(R.id.ll_delivery_my_exit);
        tvName=view.findViewById(R.id.tv_delivery_my_name);
        tvIntro=view.findViewById(R.id.tv_delivery_my_intro);
    }
    private void loadData(){
        String deliveryManId=String.valueOf(UserUntil.getDeliveryManId(requireContext()));
        Delivery delivery=UserDAO.getDeliveryManById(deliveryManId);
        if (delivery != null) {
            tvName.setText(delivery.getName());
            tvIntro.setText(delivery.getDescribe());
        }


    }
    private void initData(){
        awaiting.setOnClickListener(v->{
            Intent intent=new Intent(getContext(), DeliveryMyDCActivity.class);
            startActivity(intent);
        });
        finish.setOnClickListener(v->{
            Intent intent=new Intent(getContext(), DeliveryMyFinishActivity.class);
            startActivity(intent);
        });

        changeInfo.setOnClickListener(v->{
            Intent intent = new Intent(requireContext(), DeliveryChangeInfoActivity.class);
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
                    .setMessage("确定要注销账号吗？")
                    .setPositiveButton("确定", (dialog, which) -> {
                        performLogout();
                    })
                    .setNegativeButton("取消", (dialog, which) -> {
                        dialog.dismiss();
                    })
                    .show();
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