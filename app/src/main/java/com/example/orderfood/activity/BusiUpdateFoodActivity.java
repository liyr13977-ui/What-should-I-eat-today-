package com.example.orderfood.activity;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfood.R;
import com.example.orderfood.dao.FoodDAO;
import com.example.orderfood.entity.Food;
import com.example.orderfood.until.FileImageUntil;
import com.example.orderfood.until.LoadImage;

public class BusiUpdateFoodActivity extends BaseActivity {
    private ImageView img;
    private Toolbar title;
    private Button updateFood,deleteFood;
    private EditText foodName,foodPrice,foodNum,foodDescribe;
    private ActivityResultLauncher<String> getContentLauncher;
    private String foodId;
    private Food food;
    private Drawable defaultDrawable;
    private Uri imageUri=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        food=(Food) intent.getSerializableExtra("Food");

        LoadImage.loadImage(this,img,food.getImg());


        foodName.setText(food.getName());
        foodPrice.setText(food.getPrice());
        foodNum.setText(food.getNumber());
        foodDescribe.setText(food.getDescribe());
        foodId=food.getId();
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_busi_update_food;
    }

    @Override
    protected void initView() {
        foodName=findViewById(R.id.et_foodName_Update);
        foodPrice=findViewById(R.id.et_foodPrice_Update);
        foodNum=findViewById(R.id.et_foodNum_Update);
        foodDescribe=findViewById(R.id.et_foodDescribe_Update);
        img=findViewById(R.id.iv_foodPicture_Update);
        title=findViewById(R.id.tb_Updatefood);
        updateFood=findViewById(R.id.btn_UpdateFood);
        deleteFood=findViewById(R.id.btn_DeleteFood);
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
        updateFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateFood();
            }
        });
        deleteFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFood();
            }
        });
    }
    public void UpdateFood(){
        String name=foodName.getText().toString();
        String price=foodPrice.getText().toString();
        String num=foodNum.getText().toString();
        String describe=foodDescribe.getText().toString();

        if(name.isEmpty()){
            foodName.setError("请输入食物名称");
            foodName.requestFocus();
        }else if(price.isEmpty()){
            foodPrice.setError("请输入食物价格");
            foodPrice.requestFocus();
        }else if(num.isEmpty()){
            foodNum.setError("请输入食物数量");
            foodNum.requestFocus();
        }else if(describe.isEmpty()){
            foodDescribe.setError("请输入食物描述");
            foodDescribe.requestFocus();
        } else{
            String path;
            if(imageUri==null){
                path=food.getImg();
            }else{
                path=FileImageUntil.getImageName();
            }
            FileImageUntil.saveImageUriToFile(imageUri,this,path);
            int requestCode=FoodDAO.updateFood_Business(foodId,name,price,describe,num,path);
            if(requestCode==1){
                showToast("更新商品成功");
                IntentTo(BusinessActivity.class);
            }else{
                showToast("更新商品失败");
            }
        }
    }
    public void deleteFood() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("确认删除")
                .setMessage("确定要删除该商品吗？")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 用户点击确认，执行删除操作
                        int requestCode = FoodDAO.deleteFood_Business(foodId);
                        if (requestCode == 1) {
                            showToast("删除商品成功");
                            IntentTo(BusinessActivity.class);
                        } else {
                            showToast("删除商品失败");
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 用户点击取消，关闭对话框
                        dialog.dismiss();
                    }
                });

        // 显示对话框
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}