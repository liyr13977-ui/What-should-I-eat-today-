package com.example.orderfood.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orderfood.R;
import com.example.orderfood.adapter.UserCommentOrderAdapter;
import com.example.orderfood.dao.FoodDAO;
import com.example.orderfood.dao.OrderDAO;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Business;
import com.example.orderfood.entity.Food;
import com.example.orderfood.entity.Order;

import java.util.List;

public class UserCommentOrderActivity extends BaseActivity {
    private ListView lvFoodList;
    private UserCommentOrderAdapter adapter;
    private String orderId;
    private Button btnEnsure;
    private EditText etContent;
    private TextView tvDeliveryMan,tvBusinessName;
    private RatingBar rbDeliveryMan,rbOrder;
    private boolean viewOnly = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        orderId = getIntent().getStringExtra("orderId");
        viewOnly = getIntent().getBooleanExtra("viewOnly", false);

        Order order=OrderDAO.getOrderById(orderId);

        if (order != null) {
            Business business=UserDAO.getBusinessById(order.getBusinessId());
            tvDeliveryMan.setText(order.getDelivery());

            if (business != null) {
                tvBusinessName.setText(business.getName());
            }

            if (viewOnly) {
                String evaluate = order.getEvaluate();
                if (evaluate != null && !evaluate.isEmpty()) {
                    etContent.setText(evaluate);
                }
                String mark = order.getMark();
                if (mark != null && !mark.isEmpty()) {
                    rbOrder.setRating(Float.parseFloat(mark));
                }
                String deliveryMark = order.getDeliveryMark();
                if (deliveryMark != null && !deliveryMark.isEmpty()) {
                    rbDeliveryMan.setRating(Float.parseFloat(deliveryMark));
                }
            }
        }

        loadData();
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_user_comment_order;
    }

    @Override
    protected void initView() {
        lvFoodList = findViewById(R.id.lv_user_commentOrder_foodlist);
        btnEnsure = findViewById(R.id.btn_user_commentOrder_ensure);
        etContent=findViewById(R.id.et_user_commentOrder_content);
        tvDeliveryMan=findViewById(R.id.tv_user_commentOrder_deliveryMan);
        tvBusinessName=findViewById(R.id.tv_user_commentOrder_businessName);
        rbDeliveryMan=findViewById(R.id.rb_user_commentOrder_Deliverystart);
        rbOrder=findViewById(R.id.rb_user_commentOrder_OrderStar);

        if (viewOnly) {
            etContent.setEnabled(false);
            etContent.setFocusable(false);
            rbDeliveryMan.setIsIndicator(true);
            rbOrder.setIsIndicator(true);
            btnEnsure.setText("返回");
        }
    }

    @Override
    protected void initData() {
        btnEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewOnly) {
                    finish();
                } else {
                    submitComment();
                }
            }
        });
        if (!viewOnly) {
            rbDeliveryMan.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    FoodDAO.updateDeliveryMark(orderId, String.valueOf(rating));
                }
            });
            rbOrder.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    OrderDAO.updateOrderMark(orderId,String.valueOf(v));
                    Business business=UserDAO.getBusinessByOrderId(orderId);
                    if (business != null) {
                        String businessMark = business.getMark();
                        if (businessMark != null && !businessMark.isEmpty()) {
                            float currentMark = Float.parseFloat(businessMark);
                            float newMark = (currentMark + v) / 2;
                            OrderDAO.updateBusinessMark(business.getId(), String.valueOf(newMark));
                        } else {
                            OrderDAO.updateBusinessMark(business.getId(), String.valueOf(v));
                        }
                    }

                }
            });
        }
    }

    public void loadData() {
        List<Food> foodList = FoodDAO.getFoodByOrder(orderId);
        if (!foodList.isEmpty()) {
            adapter = new UserCommentOrderAdapter(this, foodList, orderId);
            adapter.setViewOnly(viewOnly);
            lvFoodList.setAdapter(adapter);
        } else {
            Log.e("main", "Food list is empty or null");
        }
    }

    public void submitComment() {
        String evaluate=etContent.getText().toString();
        int requestCode=FoodDAO.updateFoodEvaluate(orderId,evaluate);
        OrderDAO.finishOrder(orderId);
        if(requestCode==1){
            Toast.makeText(this, "提交评价成功！", Toast.LENGTH_SHORT).show();
            loadData();
            finish();
        }else{
            Toast.makeText(this, "提交评价失败！", Toast.LENGTH_SHORT).show();
        }


    }
}
