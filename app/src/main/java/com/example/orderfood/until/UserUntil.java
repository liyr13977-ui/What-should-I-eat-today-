package com.example.orderfood.until;

import android.content.Context;
import android.content.SharedPreferences;

public class UserUntil {
    public static int getUserId(Context context){
        SharedPreferences sp=context.getSharedPreferences("userId", Context.MODE_PRIVATE);
        return sp.getInt("userId",-1);
    }
    public static int getBusinessId(Context context){
        SharedPreferences sp=context.getSharedPreferences("BusinessId", Context.MODE_PRIVATE);
        return sp.getInt("businessId",-1);
    }

    public static int getDeliveryManId(Context context){
        SharedPreferences sp=context.getSharedPreferences("DeliveryManId", Context.MODE_PRIVATE);
        return sp.getInt("DeliveryManId",-1);
    }
}
