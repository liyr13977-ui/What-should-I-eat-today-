package com.example.orderfood.dao;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.orderfood.db.DBUntil;
import com.example.orderfood.entity.Address;
import com.example.orderfood.entity.Food;

public class AddressDAO {
    public static SQLiteDatabase db= DBUntil.con;

    public static int setDefaultAddress(String userId, String addressId) {
        try {
            // 将当前用户的其他地址的默认状态设置为0
            db.execSQL("UPDATE address SET is_default = 0 WHERE user_id = ?", new String[]{userId});
            // 将指定地址设置为默认地址
            db.execSQL("UPDATE address SET is_default = 1 WHERE id = ? AND user_id = ?",
                    new String[]{addressId, userId});
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getDefaultAddressId(String userId) {
        Cursor cursor = db.rawQuery(
                "SELECT id FROM address WHERE user_id = ? AND is_default = 1",
                new String[]{String.valueOf(userId)}
        );
        if (cursor != null && cursor.moveToFirst()) {
            String id = cursor.getString(0);
            cursor.close();
            return id;
        }
        cursor.close();
        return null; // 如果没有默认地址，返回 null
    }

    public static void deleteAddress(String addressId) {
        try {
            String tableName = "address";
            String whereClause = "id = ?";
            String[] whereArgs = { addressId };
            db.delete(tableName, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Address getDefaultAddressById(String userId){
        Address addressEntity=new Address();
        @SuppressLint("Recycle") Cursor cursor=db.rawQuery("select * from address where user_id=? and is_default=?",new String[]{userId,"1"});
        if(cursor.moveToNext()){
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String address=cursor.getString(cursor.getColumnIndex("address"));
            @SuppressLint("Range") String phone=cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String is_default=cursor.getString(cursor.getColumnIndex("is_default"));
            addressEntity.setUserId(userId);
            addressEntity.setName(name);
            addressEntity.setAddress(address);
            addressEntity.setPhone(phone);
            addressEntity.setIs_default(is_default);
            return addressEntity;
        }else{
            return null;
        }
    }

    public static Address getDefaultAddress(String userId,String addressId){
        Address addressEntity=new Address();
        @SuppressLint("Recycle") Cursor cursor=db.rawQuery("select * from address where user_id=? and id=?",new String[]{userId,addressId});
        if(cursor.moveToNext()){
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String address=cursor.getString(cursor.getColumnIndex("address"));
            @SuppressLint("Range") String phone=cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String is_default=cursor.getString(cursor.getColumnIndex("is_default"));
            addressEntity.setUserId(userId);
            addressEntity.setName(name);
            addressEntity.setAddress(address);
            addressEntity.setPhone(phone);
            addressEntity.setIs_default(is_default);
            return addressEntity;
        }else{
            return null;
        }
    }
}
