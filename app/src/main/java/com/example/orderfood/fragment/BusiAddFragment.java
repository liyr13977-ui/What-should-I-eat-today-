package com.example.orderfood.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.orderfood.R;
import com.example.orderfood.dao.FoodDAO;
import com.example.orderfood.until.FileImageUntil;

import org.w3c.dom.Text;

import java.io.FileInputStream;


public class BusiAddFragment extends Fragment {
    private ImageView img;
    private Toolbar title;
    private Button addFood;
    private EditText foodName,foodPrice,foodNum,foodDescribe;
    private ActivityResultLauncher<String> getContentLauncher;
    Drawable defaultDrawable;
    Uri imageUri=null;

    public BusiAddFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_busi_add,container,false);

        initViews(view);
        initData(view);


        String path= FileImageUntil.getImageName();
        FileImageUntil.saveImageUriToFile(imageUri, getContext(),path);


        return view;
    }
    public void initViews(View view){
        foodName=view.findViewById(R.id.et_foodName_add);
        foodPrice=view.findViewById(R.id.et_foodPrice_add);
        foodNum=view.findViewById(R.id.et_foodNum_add);
        foodDescribe=view.findViewById(R.id.et_foodDescribe_add);
        img=view.findViewById(R.id.iv_foodPicture_add);
        title=view.findViewById(R.id.tb_Addfood);
        addFood=view.findViewById(R.id.btn_addFood);
        defaultDrawable= ContextCompat.getDrawable(getContext(),R.drawable.upimg);
    }
    public void initData(View view){
        title.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });
        getContentLauncher=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if(result!=null){
                    img.setImageURI(result);
                    imageUri=result;
                }else{
                    Toast.makeText(getContext(), "未选择商品图片", Toast.LENGTH_SHORT).show();
                }
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContentLauncher.launch("image/*");
            }
        });
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFood();
            }
        });
    }
    public static int getBusinessId(Context context) {
        SharedPreferences sp = context.getSharedPreferences("BusinessId", Context.MODE_PRIVATE);
        return sp.getInt("businessId", -1); // 默认值为 -1，表示未找到
    }
    public void addFood(){
        String name=foodName.getText().toString();
        String price=foodPrice.getText().toString();
        String num=foodNum.getText().toString();
        String describe=foodDescribe.getText().toString();


        Drawable drawable=img.getDrawable();//获取当前标签的图片
        Bitmap bitmap=((BitmapDrawable) drawable).getBitmap();//获取这个图片的二进制文件
        Bitmap Defaultbitmap=((BitmapDrawable) defaultDrawable).getBitmap();


        if(bitmap.sameAs(Defaultbitmap)) {
            Toast.makeText(getContext(), "请设置商品图片", Toast.LENGTH_SHORT).show();
        }else if(name.isEmpty()){
            foodName.setError("请输入商品名称");
            foodName.requestFocus();
        }else if(price.isEmpty()){
            foodPrice.setError("请输入商品价格");
            foodPrice.requestFocus();
        }else if(num.isEmpty()){
            foodNum.setError("请输入商品数量");
            foodNum.requestFocus();
        }else if(describe.isEmpty()){
            foodDescribe.setError("请输入商品描述");
            foodDescribe.requestFocus();
        } else{
            String path=FileImageUntil.getImageName();

            FileImageUntil.saveImageUriToFile(imageUri,getContext(),path);
            String businessId=String.valueOf(getBusinessId(getContext()));
            int requestCode=FoodDAO.addFood_Business(businessId,name,price,describe,num,path);
            if(requestCode==1){
                Toast.makeText(getContext(), "添加商品成功", Toast.LENGTH_SHORT).show();
                foodName.setText("");
                foodPrice.setText("");
                foodNum.setText("");
                foodDescribe.setText("");
                img.setImageDrawable(defaultDrawable);
                // 生成新的图片路径名，确保下一次添加不会覆盖默认图片
                String path1 = FileImageUntil.getImageName();
            }else{
                Toast.makeText(getContext(), "添加商品失败", Toast.LENGTH_SHORT).show();
            }
        }

    }
}