package com.example.orderfood.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.orderfood.R;
import com.example.orderfood.adapter.UserPaymentAdapter;
import com.example.orderfood.dao.AddressDAO;
import com.example.orderfood.dao.FoodDAO;
import com.example.orderfood.dao.OrderDAO;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Address;
import com.example.orderfood.entity.Business;
import com.example.orderfood.entity.Food;
import com.example.orderfood.entity.User;
import com.example.orderfood.until.UserUntil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserPaymentActivity extends BaseActivity {
    private UserPaymentAdapter adapter;
    private ListView foodList;
    private Toolbar title;
    private Button upload,btnAddRemark;
    private TextView address,nickName,phone,date,businessId,totalPrice,delivery,businessName,tvRemark;
    private Business business;
    private User user;
    private String foodListJson;
    private CardView addressCard;
    private static final int REQUEST_CODE_EDIT_ADDRESS = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        user=UserDAO.getUserById(String.valueOf(getUserId(UserPaymentActivity.this)));
        initInfo();
        loadData();
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_user_payment;
    }

    @Override
    protected void initView() {
        foodList=findViewById(R.id.lv_user_payment_foodlist);
        title=findViewById(R.id.tb_user_payment_back);
        address=findViewById(R.id.tv_user_payment_address);
        nickName=findViewById(R.id.tv_user_payment_nickName);
        phone=findViewById(R.id.tv_user_payment_phone);
        date=findViewById(R.id.tv_user_payment_date);
        totalPrice=findViewById(R.id.tv_user_payment_totalPrice);
        delivery=findViewById(R.id.tv_user_payment_delivery);
        businessName=findViewById(R.id.tv_user_payment_businessName);
        upload=findViewById(R.id.btn_user_payment_upload);
        addressCard=findViewById(R.id.cv_user_payment_userInfo);
        btnAddRemark=findViewById(R.id.btn_user_payment_addRemark);
        tvRemark=findViewById(R.id.tv_user_payment_remarkContent);
    }

    @Override
    protected void initData() {
        title.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uId=String.valueOf(getUserId(UserPaymentActivity.this));
                String address3=address.getText().toString();
                String nickName3=nickName.getText().toString();
                String phone3=phone.getText().toString();
                String remark3=tvRemark.getText().toString();
                int requestCode= OrderDAO.uploadOrder(uId,business.getId(),address3,String.valueOf(1),foodListJson,nickName3,phone3,remark3);
                if(requestCode==1){
                    Intent intent = new Intent(UserPaymentActivity.this, UserMyAwaitingActivity.class);

                    intent.putExtra("cartItems",foodListJson);
                    startActivity(intent);
                    finish();
                }else{
                    showToast("提交订单失败，请稍后重试");
                }

            }
        });
        btnAddRemark.setOnClickListener(v->{
            AlertDialog.Builder builder = new AlertDialog.Builder(UserPaymentActivity.this);
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_remark, null);
            builder.setView(dialogView);


            EditText etRemark=dialogView.findViewById(R.id.et_dialog_remark);
            Button btnSave = dialogView.findViewById(R.id.btn_dialog_Save);
            Button btnCancel = dialogView.findViewById(R.id.btn_dialog_Cancel);

            AlertDialog dialog1 = builder.create();

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String remark = etRemark.getText().toString().trim();
                    tvRemark.setText(remark);
                    dialog1.dismiss();

                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog1.dismiss();
                }
            });

            dialog1.show();
        });

        addressCard.setOnClickListener(V->{
            Intent intent=new Intent(UserPaymentActivity.this,UserEditAddressActivity.class);
            startActivityForResult(intent,REQUEST_CODE_EDIT_ADDRESS);
        });

    }
    private void loadData(){
        List<Food> foodData = initFoodList();
        adapter=new UserPaymentAdapter(this, foodData);
        foodList.setAdapter(adapter);
    }
    //将选择的商品信息转换成foodList
    @SuppressLint("SetTextI18n")
    private List<Food> initFoodList(){
        List<Food> list = new ArrayList<>();
        String cartItems=getIntent().getStringExtra("cartItems");
        foodListJson=cartItems;
        BigDecimal tPrice=new BigDecimal("0");
        String delivery1 = business.getDelivery(); // 假设 delivery 是一个 TextView，需确保已正确初始化
        BigDecimal BDdelivery = new BigDecimal(delivery1);
        try{
            //将字符串转换成JSONArray
            JSONArray jsonArray=new JSONArray(cartItems);
            for(int i=0;i<jsonArray.length();i++){
                //获取每个JSONObject
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                //提取具体字段内容
                String foodId=jsonObject.getString("foodId");
                String foodNum=jsonObject.getString("foodNum");
                String foodPrice=jsonObject.getString("foodPrice");
                BigDecimal price=new BigDecimal(foodPrice);
                tPrice=tPrice.add(price.multiply(new BigDecimal(foodNum)));
                //将商品数量存到数据库中
                FoodDAO.updateFoodNum(foodId,foodNum);
                //将查询到的food添加到list集合中
                list.add(FoodDAO.getFoodById(foodId));
            }
            tPrice = tPrice.add(BDdelivery);
            totalPrice.setText("合计：￥"+tPrice.toString());

        }catch (Exception e){
            e.printStackTrace();
            list=null;
        }
        return list;
    }
    @SuppressLint("SetTextI18n")
    private void initInfo(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.MINUTE,30296);
        Date now= calendar.getTime();
        String date1=dateFormat.format(now);
        date.setText(date1);

        int userId=getUserId(UserPaymentActivity.this);
        Address addressEntity= AddressDAO.getDefaultAddressById(String.valueOf(userId));

        String businessId=getIntent().getStringExtra("businessId");

        business=UserDAO.getBusinessById(businessId);

        if(business!=null){
            delivery.setText("￥"+business.getDelivery());
            businessName.setText(business.getName());
        }


        if (addressEntity != null) {
            nickName.setText(addressEntity.getName());
            phone.setText(addressEntity.getPhone());
            address.setText(addressEntity.getAddress());
        }
    }

    public static int getUserId(Context context){
        SharedPreferences sp=context.getSharedPreferences("userId", Context.MODE_PRIVATE);
        return sp.getInt("userId",-1);
    }

    private void updateAddressUI(String selectedAddressId) {
        String userId=String.valueOf(UserUntil.getUserId(this));
        Address addressEntity = AddressDAO.getDefaultAddress(userId,selectedAddressId);
        if (addressEntity != null) {
            nickName.setText(addressEntity.getName());
            phone.setText(addressEntity.getPhone());
            address.setText(addressEntity.getAddress());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_EDIT_ADDRESS && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String selectedAddressId = data.getStringExtra("selectedAddressId");
                updateAddressUI(selectedAddressId);
            }
        }
    }


}