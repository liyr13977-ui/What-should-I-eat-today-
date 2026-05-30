package com.example.orderfood.activity;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.Toolbar;

import com.example.orderfood.R;
import com.example.orderfood.dao.FoodDAO;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Delivery;
import com.example.orderfood.entity.User;
import com.example.orderfood.until.FileImageUntil;
import com.example.orderfood.until.LoadImage;
import com.example.orderfood.until.UserUntil;

import java.util.Objects;

public class UserChangeInfoActivity extends BaseActivity {
    private Toolbar tbTitle;
    private Button btnChangeInfo;
    private EditText etPwd,etNickName,etPhone,etIntro;
    private String userId;
    private ImageView ivPhoto;
    private RadioGroup rg;
    private RadioButton rbMale,rbFemale;
    private ActivityResultLauncher<String> getContentLauncher;
    private Uri imageUri=null;
    private Drawable defaultDrawable;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userId=String.valueOf(UserUntil.getUserId(this));
        user=UserDAO.getUserById(userId);
        if (user != null) {
            etNickName.setText(user.getNickName());
            etPwd.setText(user.getPwd());
            etPhone.setText(user.getPhone());
            LoadImage.loadImage(this,ivPhoto,user.getImg());
            if(Objects.equals(user.getGender(), "男")){
                rbMale.setChecked(true);
            }else{
                rbFemale.setChecked(true);
            }
        }

    }

    @Override
    protected int initLayout() {
        return R.layout.activity_user_change_info;
    }

    @Override
    protected void initView() {
        tbTitle=findViewById(R.id.tb_user_changeInfo);
        ivPhoto=findViewById(R.id.iv_user_changeInfo_photo);
        etNickName=findViewById(R.id.et_user_changeInfo_nickName);
        rg=findViewById(R.id.rg_user_changeInfo);
        rbMale=findViewById(R.id.rb_user_changeInfo_Male);
        rbFemale=findViewById(R.id.rb_user_changeInfo_Female);
        etPwd=findViewById(R.id.et_user_changeInfo_pwd);
        etPhone=findViewById(R.id.et_user_changeInfo_phone);
        btnChangeInfo=findViewById(R.id.btn_user_changeInfo_ensure);

    }

    @Override
    protected void initData() {
        tbTitle.setOnClickListener(v->{
            super.onBackPressed();
            finish();
        });
        btnChangeInfo.setOnClickListener(v->{changeInfo();});

        getContentLauncher=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(result!=null){
                    ivPhoto.setImageURI(result);
                    imageUri=result;
                }else{
                    showToast("未选择图片");
                }
            }
        });
        ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContentLauncher.launch("image/*");
            }
        });
    }

    public void changeInfo(){
        String pwd=etPwd.getText().toString();
        String nickName=etNickName.getText().toString();
        String phone=etPhone.getText().toString();

        if(pwd.isEmpty()){
            etPwd.setError("请输入密码");
            etPwd.requestFocus();
        }else if(nickName.isEmpty()){
            etNickName.setError("请输入姓名");
            etNickName.requestFocus();
        }else if(phone.isEmpty()){
            etPhone.setError("请输入手机号");
            etPhone.requestFocus();
        }else{

            String path;
            if(imageUri==null){
                path=user.getImg();
            }else{
                path= FileImageUntil.getImageName();
            }
            String gender="男";
            int selectedId=rg.getCheckedRadioButtonId();
            if(selectedId==R.id.rb_user_changeInfo_Female){
                gender="女";
            }
            FileImageUntil.saveImageUriToFile(imageUri,this,path);
            int requestCode= UserDAO.updateUserInfo(userId,pwd,nickName,gender,phone,path);
            if(requestCode==1){
                showToast("更新信息成功");
            }else{
                showToast("更新信息失败");
            }
        }
    }
}