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



public class RegisterBusinessActivity extends BaseActivity {
    private Toolbar title;
    private EditText account,pwd,name,describe,fee,contact;
    private Button register;
    private ImageView img;
    private ActivityResultLauncher<String> getContentLauncher;
    private Drawable defaultDrawable;
    private Uri imageUri=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected int initLayout() {
        return R.layout.activity_register_business;
    }
    @Override
    protected void initView() {
        title=findViewById(R.id.tb_registerBusi);
        setSupportActionBar(title);//toolbar生效
        account=findViewById(R.id.et_account_registerB);
        pwd=findViewById(R.id.et_pwd_registerB);
        name=findViewById(R.id.et_name_registerB);
        describe=findViewById(R.id.et_describe_registerB);
        fee=findViewById(R.id.et_registerBusi_fee);
        register=findViewById(R.id.btn_finalRegisterB);
        img=findViewById(R.id.iv_registerB);
        contact=findViewById(R.id.et_contact_registerB);
        defaultDrawable= ContextCompat.getDrawable(this,R.drawable.upimg);
    }
    @Override
    protected void initData() {
        title.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getContentLauncher=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(result!=null){
                    img.setImageURI(result);
                    imageUri=result;
                }else{
                    showToast("未选择头像");
                }
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContentLauncher.launch("image/*");
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account1=account.getText().toString();
                String pwd1=pwd.getText().toString();
                String name1=name.getText().toString();
                String contact1=contact.getText().toString();
                String describe1=describe.getText().toString();
                String fee1=fee.getText().toString();
                Drawable drawable=img.getDrawable();//获取当前标签的图片

                Bitmap bitmap=(((BitmapDrawable) drawable).getBitmap());//获取这个图片的二进制文件
                Bitmap Defaultbitmap=(((BitmapDrawable) defaultDrawable).getBitmap());
                if(bitmap.sameAs(Defaultbitmap)){
                    showToast("请点击图片设置头像");
                }else if(account1.isEmpty()){
                    account.setError("请输入账号");
                    account.requestFocus();
                }else if(pwd1.isEmpty()){
                    pwd.setError("请输入密码");
                    pwd.requestFocus();
                }else if(contact1.isEmpty()){
                    contact.setError("请输入联系方式");
                    contact.requestFocus();
                }else if(name1.isEmpty()){
                    name.setError("请输入店铺名");
                    name.requestFocus();
                }else if(describe1.isEmpty()){
                    describe.setError("请输入店铺描述");
                    describe.requestFocus();
                }else if(fee1.isEmpty()){
                    fee.setError("请输入配送费");
                    fee.requestFocus();
                }else{
                    //图片的存储路径
                    String path=FileImageUntil.getImageName();
                    //将图片保存到系统中
                    FileImageUntil.saveImageUriToFile(imageUri,RegisterBusinessActivity.this,path);
                    int requestCode= UserDAO.saveBusiness(account1,pwd1,contact1,name1,describe1,fee1,path);
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