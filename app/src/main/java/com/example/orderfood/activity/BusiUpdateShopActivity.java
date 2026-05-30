package com.example.orderfood.activity;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.orderfood.R;
import com.example.orderfood.dao.FoodDAO;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Business;
import com.example.orderfood.fragment.BusiMyFragment;
import com.example.orderfood.until.FileImageUntil;

public class BusiUpdateShopActivity extends BaseActivity {
    private ImageView img;
    private EditText name,fee,describe,etPhone;
    private Button btn;
    private ActivityResultLauncher<String> getContentLauncher;
    private Drawable defaultDrawable;
    private Uri imageUri=null;
    private Business business=new Business();;
    private Toolbar title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        loadData();
        initData();
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_busi_update_shop;
    }

    @Override
    protected void initView() {
        img=findViewById(R.id.iv_shopPicture_Update);
        name=findViewById(R.id.et_shopName_Update);
        fee=findViewById(R.id.et_busi_updateShop_fee);
        describe=findViewById(R.id.et_shopDescribe_Update);
        btn=findViewById(R.id.btn_UpdateShop);
        title=findViewById(R.id.tb_UpdateShop);
        etPhone=findViewById(R.id.et_busi_updateShop_phone);
    }

    @Override
    protected void initData() {
        business= UserDAO.getBusinessById(String.valueOf(getBusinessId(this)));

        if(business!=null){
            name.setText(business.getName());
            describe.setText(business.getDescribe());
            fee.setText(business.getDelivery());
            img.setImageURI(Uri.parse(business.getImg()));
            etPhone.setText(business.getPhone());

            loadImage(this,img,business.getImg());

        }else{
            name.setText("null");
        }
    }
    public void loadData(){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateShop();
            }
        });
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
                    showToast("未选择图片");
                }
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContentLauncher.launch("image/*");
            }
        });
    }
    public static int getBusinessId(Context context){
        SharedPreferences sp=context.getSharedPreferences("BusinessId", Context.MODE_PRIVATE);
        return sp.getInt("businessId",-1);
    }
    public void UpdateShop(){
        String shopName=name.getText().toString();
        String shopFee=fee.getText().toString();
        String shopDescribe=describe.getText().toString();
        String shopPhone=etPhone.getText().toString();

        if(shopName.isEmpty()){
            name.setError("店铺名称不能为空");
            name.requestFocus();
        }else if(shopPhone.isEmpty()){
            etPhone.setError("联系方式不能为空");
            etPhone.requestFocus();
        } else if(shopFee.isEmpty()){
            fee.setError("配送费不能为空");
            fee.requestFocus();
        }else if(shopDescribe.isEmpty()){
            describe.setError("店铺描述不能为空");
            describe.requestFocus();
        }else{
            String path;
            if(imageUri==null){
                path=business.getImg();
            }else{
                path= FileImageUntil.getImageName();
            }
            FileImageUntil.saveImageUriToFile(imageUri,this,path);
            int requestCode= UserDAO.updateBusiness(String.valueOf(getBusinessId(this)),shopName,shopPhone,shopFee,shopDescribe,path);
            if(requestCode==1){
                showToast("修改成功");
                setResult(Activity.RESULT_OK);

            }else{
                showToast("修改失败");
            }
        }
    }


    public static void loadImage(Context context, ImageView imgView, String imgResource) {
        // 判断是否是资源ID
        //逻辑是相反的，有用就行
        if (isResourceID(imgResource)) {

            // 是图片路径，使用setImageURI
            imgView.setImageURI(Uri.parse(imgResource));


        } else {
            // 是资源ID，使用setImageResource
            int imgResourceId = context.getResources().getIdentifier(imgResource, "drawable", context.getPackageName());
            imgView.setImageResource(imgResourceId);
        }
    }
    private static boolean isResourceID(String imgResource) {
        // 假设资源ID是以 "R.drawable." 开头的字符串
        return imgResource.startsWith("/");
    }


}