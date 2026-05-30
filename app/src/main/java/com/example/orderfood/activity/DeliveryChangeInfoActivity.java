package com.example.orderfood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.orderfood.R;
import com.example.orderfood.dao.FoodDAO;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Delivery;
import com.example.orderfood.fragment.DeliveryMyFragment;
import com.example.orderfood.until.FileImageUntil;
import com.example.orderfood.until.UserUntil;

public class DeliveryChangeInfoActivity extends BaseActivity {
    private Toolbar tbTitle;
    private Button btnChangeInfo;
    private EditText etPwd,etName,etPhone,etIntro;
    private String deliveryManId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int initLayout() {
        return R.layout.activity_delivery_change_info;
    }

    @Override
    protected void initView() {
        btnChangeInfo=findViewById(R.id.btn_delivery_changeInfo_ensure);
        etPwd=findViewById(R.id.et_delivery_changeInfo_pwd);
        etName=findViewById(R.id.et_delivery_changeInfo_name);
        etPhone=findViewById(R.id.et_delivery_changeInfo_phone);
        etIntro=findViewById(R.id.et_delivery_changeInfo_intro);
        tbTitle=findViewById(R.id.tb_delivery_changeInfo);
    }

    @Override
    protected void initData() {
        deliveryManId= String.valueOf(UserUntil.getDeliveryManId(this));
        loadData();
        tbTitle.setOnClickListener(v->{
            super.onBackPressed();
            finish();
        });
        btnChangeInfo.setOnClickListener(v->{UpdateFood();});
    }

    public void UpdateFood(){
        String pwd=etPwd.getText().toString();
        String name=etName.getText().toString();
        String phone=etPhone.getText().toString();
        String intro=etIntro.getText().toString();

        if(pwd.isEmpty()){
            etPwd.setError("请输入密码");
            etPwd.requestFocus();
        }else if(name.isEmpty()){
            etName.setError("请输入姓名");
            etName.requestFocus();
        }else if(phone.isEmpty()){
            etPhone.setError("请输入手机号");
            etPhone.requestFocus();
        }else if(intro.isEmpty()){
            etIntro.setError("请输入个人介绍");
            etIntro.requestFocus();
        } else{

            int requestCode= UserDAO.updateDeliveryMan(deliveryManId,pwd,name,phone,intro);
            if(requestCode==1){
                showToast("更新个人信息成功");
                finish();
            }else{
                showToast("更新个人信息失败");
            }
        }
    }
    private void loadData(){
        Delivery deliveryMan=UserDAO.getDeliveryManById(deliveryManId);
        if (deliveryMan != null) {
            etPwd.setText(deliveryMan.getPwd());
            etName.setText(deliveryMan.getName());
            etPhone.setText(deliveryMan.getPhone());
            etIntro.setText(deliveryMan.getDescribe());
        }
    }
}