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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.example.orderfood.R;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.until.FileImageUntil;

public class RegisterUserActivity extends BaseActivity {
    private Toolbar title;
    private Button register;
    private ImageView img;
    private RadioGroup rg;
    private EditText account,pwd,nickName,address,phone;
    private ActivityResultLauncher<String> getContentLauncher;
    private Drawable defaultDrawable;
    private Uri imageUri=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    protected int initLayout() {
        return R.layout.activity_register_user;
    }
    @Override
    protected void initView() {
        title=findViewById(R.id.tb_registerUser);
        setSupportActionBar(title);
        img=findViewById(R.id.iv_registerU);
        defaultDrawable= ContextCompat.getDrawable(this,R.drawable.upimg);
        account=findViewById(R.id.et_account_registerU);
        pwd=findViewById(R.id.et_pwd_registerU);
        nickName=findViewById(R.id.et_nickName_registerU);
        rg=findViewById(R.id.rg);
        RadioButton male = findViewById(R.id.rb_registerMale);
        male.setChecked(true);
        address=findViewById(R.id.et_address_resisterU);
        phone=findViewById(R.id.et_phone_resisterU);
        register=findViewById(R.id.btn_finalRegisterU);
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
                register();
            }
        });
    }
    public void register(){
        String account1=account.getText().toString();
        String pwd1=pwd.getText().toString();
        String name1=nickName.getText().toString();
        String address1=address.getText().toString();
        String phone1=phone.getText().toString();
        Drawable drawable=img.getDrawable();//获取当前标签的图片
        Bitmap bitmap=((BitmapDrawable) drawable).getBitmap();//获取这个图片的二进制文件
        Bitmap Defaultbitmap=((BitmapDrawable) defaultDrawable).getBitmap();
        if(bitmap.sameAs(Defaultbitmap)){
            showToast("请点击图片设置头像");
        }else if(account1.isEmpty()){
            account.setError("请输入账号");
            account.requestFocus();
        }else if(pwd1.isEmpty()){
            pwd.setError("请输入密码");
            pwd.requestFocus();
        }else if(name1.isEmpty()){
            nickName.setError("请输入用户名");
            nickName.requestFocus();
        }else if(address1.isEmpty()){
            address.setError("请输入地址");
            address.requestFocus();
        }else if(phone1.isEmpty()){
            phone.setError("请输入联系方式");
            phone.requestFocus();
        }else{
            //可以向数据库保存数据
            String gender="男";
            int selectedId=rg.getCheckedRadioButtonId();
            if(selectedId==R.id.rb_registerFemale){
                gender="女";
            }
            String path= FileImageUntil.getImageName();
            FileImageUntil.saveImageUriToFile(imageUri,RegisterUserActivity.this,path);
            int requestCode= UserDAO.saveUser(account1,pwd1,name1,gender,address1,phone1,path);
            if(requestCode==1){
                IntentTo(LoginActivity.class);
                showToast("注册成功");
            }else{
                showToast("注册失败");
            }
        }
    }

}