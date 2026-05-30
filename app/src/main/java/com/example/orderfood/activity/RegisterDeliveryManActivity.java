package com.example.orderfood.activity;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.example.orderfood.R;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.until.FileImageUntil;

public class RegisterDeliveryManActivity extends BaseActivity {
    private Toolbar tbTitle;
    private EditText etAccount,etPwd,etName,etIntro,etPhone;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected int initLayout() {
        return R.layout.activity_register_delivery_man;
    }
    @Override
    protected void initView() {
        tbTitle=findViewById(R.id.tb_regiDeliveryMan);
        setSupportActionBar(tbTitle);//toolbar生效
        etAccount=findViewById(R.id.et_regiDeliveryMan_account);
        etPwd=findViewById(R.id.et_regiDeliveryMan_pwd);
        etName=findViewById(R.id.et_regiDeliveryMan_name);
        etPhone=findViewById(R.id.et_regiDeliveryMan_phone);
        etIntro=findViewById(R.id.et_regiDeliveryMan_intro);
        btnRegister=findViewById(R.id.et_regiDeliveryMan_ensure);
    }
    @Override
    protected void initData() {
        tbTitle.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account=etAccount.getText().toString();
                String pwd=etPwd.getText().toString();
                String name=etName.getText().toString();
                String phone=etPhone.getText().toString();
                String intro=etIntro.getText().toString();

                if(account.isEmpty()){
                    etAccount.setError("请输入账号");
                    etAccount.requestFocus();
                }else if(pwd.isEmpty()){
                    etPwd.setError("请输入密码");
                    etPwd.requestFocus();
                }else if(name.isEmpty()){
                    etName.setError("请输入姓名");
                    etName.requestFocus();
                }else if(phone.isEmpty()){
                    etPhone.setError("请输入联系方式");
                    etPhone.requestFocus();
                }else if(intro.isEmpty()){
                    etIntro.setError("请输入个人介绍");
                    etIntro.requestFocus();
                }else{

                    int requestCode= UserDAO.registerDeliveryMan(account,pwd,name,intro,phone);
                    if(requestCode==1){
                        showToast("注册成功");
                        IntentTo(LoginActivity.class);
                    }else{
                        showToast("注册失败");
                    }
                }
            }
        });
    }

}