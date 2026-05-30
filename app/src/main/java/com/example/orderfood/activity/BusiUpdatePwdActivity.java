package com.example.orderfood.activity;

import androidx.appcompat.widget.Toolbar;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.orderfood.R;
import com.example.orderfood.dao.UserDAO;

public class BusiUpdatePwdActivity extends BaseActivity {
    private EditText pwd,cpwd;
    private Button btn;
    private Toolbar tb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_busi_update_pwd;
    }

    @Override
    protected void initView() {
        pwd=findViewById(R.id.et_shopPwd_Update);
        cpwd=findViewById(R.id.et_shopPwd_Update_confirm);
        btn=findViewById(R.id.btn_UpdatePwd);
        tb=findViewById(R.id.tb_UpdatePwd);
    }

    @Override
    protected void initData() {
        tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePwd();
            }
        });
    }public static int getBusinessId(Context context){
        SharedPreferences sp=context.getSharedPreferences("BusinessId", Context.MODE_PRIVATE);
        return sp.getInt("businessId",-1);
    }
    public void ChangePwd(){
        String pwd1=pwd.getText().toString().trim();
        String cpwd1=cpwd.getText().toString().trim();
        if(pwd1.isEmpty()){
            pwd.setError("请输入密码");
            pwd.requestFocus();
        }else if(cpwd1.isEmpty()){
            cpwd.setError("请输入密码");
            cpwd.requestFocus();
        }else if(!pwd1.equals(cpwd1)){
            cpwd.setError("请输入密码");
            cpwd.requestFocus();
        }else{
            String id=String.valueOf(getBusinessId(BusiUpdatePwdActivity.this));
            int requestCode=UserDAO.updateBusinessPwd(id,pwd1);
            if(requestCode==1){
                showToast("修改成功");
                onBackPressed();
            }else{
                showToast("修改失败");
            }
        }
    }

}