package com.example.orderfood.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.orderfood.R;
import com.example.orderfood.db.DBUntil;
import com.example.orderfood.dao.UserDAO;

public class LoginActivity extends BaseActivity {
    private Button login,RegBusiness,RegUser,regiDeliveryMan;
    private EditText account,pwd;
    private RadioButton busi_rb,User_rb,rb_deliveryMan;
    private static final int REQUEST_EXTERNAL_STORAGE=1;
    private static final String[] PERMISSIONS={Manifest.permission.READ_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBUntil dbUntil=new DBUntil(this);
        DBUntil.con=dbUntil.getWritableDatabase();
        ActivityCompat.requestPermissions(LoginActivity.this,PERMISSIONS,REQUEST_EXTERNAL_STORAGE);
    }
    @Override
    protected int initLayout() {
        return R.layout.activity_login;
    }
    @Override
    protected void initView() {
        login=findViewById(R.id.btn_login);
        RegBusiness=findViewById(R.id.btn_registerBusiness);
        RegUser=findViewById(R.id.btn_registerUser);
        busi_rb=findViewById(R.id.rb_busi);
        User_rb=findViewById(R.id.rb_user);
        User_rb.setChecked(true);//rbUser默认选择
        account=findViewById(R.id.et_account_login);
        pwd=findViewById(R.id.et_pwd_login);
        rb_deliveryMan=findViewById(R.id.rb_deliveryMan);
        regiDeliveryMan=findViewById(R.id.btn_registerDeliveryMan);
    }
    @Override
    protected void initData() {
        RegBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentTo(RegisterBusinessActivity.class);
            }
        });
        RegUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentTo(RegisterUserActivity.class);
            }
        });
        regiDeliveryMan.setOnClickListener(v->{
            IntentTo(RegisterDeliveryManActivity.class);
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account1=account.getText().toString();
                String pwd1=pwd.getText().toString();
                if(account1.isEmpty()){
                    account.setError("请输入账号");
                    account.requestFocus();
                }else if (pwd1.isEmpty()){
                    pwd.setError("请输入密码");
                    pwd.requestFocus();
                }else{
                    if(busi_rb.isChecked()){
                        int a= UserDAO.loginBusiness(LoginActivity.this,account1,pwd1);
                        if(a==1){
                            IntentTo(BusinessActivity.class);
                        }else{
                            showToast("账号或密码错误");
                        }
                    }else if(rb_deliveryMan.isChecked()){
                        int a= UserDAO.loginDelivery(LoginActivity.this,account1,pwd1);
                        if(a==1){
                            IntentTo(DeliveryActivity.class);
                        }else{
                            showToast("账号或密码错误");
                        }
                    }else{
                        int a= UserDAO.loginUser(LoginActivity.this,account1,pwd1);
                        if(a==1){
                            IntentTo(UserActivity.class);
                        }else{
                            showToast("账号或密码错误");
                        }
                    }
                }

            }
        });

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode==REQUEST_EXTERNAL_STORAGE){
//            if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
//                showToast("授权成功");
//            }else{
//                showToast("授权失败");
//            }
//        }
//    }

}