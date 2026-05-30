package com.example.orderfood.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.appcompat.widget.ActionBarContextView;

import com.example.orderfood.db.DBUntil;
import com.example.orderfood.entity.Food;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FoodDAO {
    public static SQLiteDatabase db= DBUntil.con;

    public static List<Food> getAllFoodUser(){
        List<Food> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from food",null);
        while(cursor.moveToNext()){
            @SuppressLint("Range") String id=cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String business=cursor.getString(cursor.getColumnIndex("businessId"));
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String des=cursor.getString(cursor.getColumnIndex("describe"));
            @SuppressLint("Range") String number=cursor.getString(cursor.getColumnIndex("number"));
            @SuppressLint("Range") String price=cursor.getString(cursor.getColumnIndex("price"));
            @SuppressLint("Range") String img=cursor.getString(cursor.getColumnIndex("img"));
            @SuppressLint("Range") String mark=cursor.getString(cursor.getColumnIndex("mark"));
            Food food=new Food(id,business,name,des,number,price,img,mark);
            list.add(food);
        }
        return list;
    }

    public static List<Food> getAllFoodBusi(int BusinessId){
        List<Food> list=new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor=db.rawQuery("select * from food where BusinessId=?",new String[]{String.valueOf(BusinessId)});
        while(cursor.moveToNext()){
            @SuppressLint("Range") String id=cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String business=cursor.getString(cursor.getColumnIndex("businessId"));
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String des=cursor.getString(cursor.getColumnIndex("describe"));
            @SuppressLint("Range") String number=cursor.getString(cursor.getColumnIndex("number"));
            @SuppressLint("Range") String price=cursor.getString(cursor.getColumnIndex("price"));
            @SuppressLint("Range") String img=cursor.getString(cursor.getColumnIndex("img"));
            @SuppressLint("Range") String mark=cursor.getString(cursor.getColumnIndex("mark"));
            Food food=new Food(id,business,name,des,number,price,img,mark);
            list.add(food);
        }
        return list;
    }
    public static Food getFoodById(String id){
        Food food=new Food();
        @SuppressLint("Recycle") Cursor cursor=db.rawQuery("select * from food where id=?",new String[]{id});
        if(cursor.moveToNext()){
            @SuppressLint("Range") String businessId=cursor.getString(cursor.getColumnIndex("businessId"));
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String describe=cursor.getString(cursor.getColumnIndex("describe"));
            @SuppressLint("Range") String number=cursor.getString(cursor.getColumnIndex("number"));
            @SuppressLint("Range") String price=cursor.getString(cursor.getColumnIndex("price"));
            @SuppressLint("Range") String img=cursor.getString(cursor.getColumnIndex("img"));
            food.setId(id);
            food.setBusinessId(businessId);
            food.setName(name);
            food.setDescribe(describe);
            food.setNumber(number);
            food.setPrice(price);
            food.setImg(img);
            return food;
        }else{
            return null;
        }
    }
    public static List<Food> getFoodByOrder(String orderId) {
        List<Food> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            String sql = "SELECT food.id, food.businessId, food.name, food.describe, food.number, food.price, food.img, food.mark FROM food " +
                    "JOIN orderfood ON food.id = orderfood.food_id " +
                    "WHERE orderfood.order_id = ?";
            cursor = db.rawQuery(sql, new String[]{String.valueOf(orderId)});
            Log.d("main", "Executing query: " + sql + " with orderId: " + orderId);
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String business = cursor.getString(cursor.getColumnIndex("businessId"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String des = cursor.getString(cursor.getColumnIndex("describe"));
                @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex("number"));
                @SuppressLint("Range") String price = cursor.getString(cursor.getColumnIndex("price"));
                @SuppressLint("Range") String img = cursor.getString(cursor.getColumnIndex("img"));
                @SuppressLint("Range") String mark = cursor.getString(cursor.getColumnIndex("mark"));
                Food food = new Food(id, business, name, des, number, price, img, mark);
                list.add(food);
                Log.d("main", "Added food: " + food.getName());
            }
        } catch (Exception e) {
            Log.e("main", "Error retrieving food by order: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }

    public static int addFood_Business(String businessId,String name,String price,String describe,String number,String img){
        String[] data={businessId,name,price,describe,number,img};
        try{
            db.execSQL("INSERT INTO food(businessId,name,price,describe,number,img) " +
                    "VALUES(?,?,?,?,?,?)", data);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }
    public static int updateFood_Business(String foodId,String foodName,String foodPrice,String foodDescribe,String foodNumber,String foodImg){
        ContentValues values=new ContentValues();
        values.put("name",foodName);
        values.put("price",foodPrice);
        values.put("describe",foodDescribe);
        values.put("number",foodNumber);
        values.put("img",foodImg);
        try{
            db.update("food",//表名
                    values,//更新的字段
                    "id=?",//where子句
                    new String[]{foodId});//where子句的参数
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    public static int updateFoodMark(String orderId,String foodId,String mark){
        ContentValues values=new ContentValues();
        values.put("mark",mark);
        try{
            db.update("orderfood",//表名
                    values,//更新的字段
                    "order_id=? and food_id=?",//where子句
                    new String[]{orderId,foodId});//where子句的参数
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    public static int updateDeliveryMark(String orderId,String mark){
        ContentValues values=new ContentValues();
        values.put("deliveryMark",mark);
        try{
            db.update("orders",//表名
                    values,//更新的字段
                    "id=?",//where子句
                    new String[]{orderId});//where子句的参数
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    public static int updateFoodEvaluate(String orderId,String evaluate){
        ContentValues values=new ContentValues();
        values.put("evaluate",evaluate);
        try{
            db.update("orders",//表名
                    values,//更新的字段
                    "id=?",//where子句
                    new String[]{orderId});//where子句的参数
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    public static int deleteFood_Business(String foodId){
        try{
            db.delete("food","id=?",new String[]{foodId});
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public static List<Food> queryFood_Business(String foodName,String businessId) {
        List<Food> list = new ArrayList<>();

        String sql = "select * from food where name like ? and businessId =?";
        Cursor cursor = db.rawQuery(sql, new String[]{"%" + foodName + "%",businessId});//模糊查询
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String business = cursor.getString(cursor.getColumnIndex("businessId"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String des = cursor.getString(cursor.getColumnIndex("describe"));
            @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex("number"));
            @SuppressLint("Range") String price = cursor.getString(cursor.getColumnIndex("price"));
            @SuppressLint("Range") String img = cursor.getString(cursor.getColumnIndex("img"));
            @SuppressLint("Range") String mark=cursor.getString(cursor.getColumnIndex("mark"));
            Food food = new Food(id, business, name, des, number, price, img,mark);
            list.add(food);
        }
        return list;
    }
    public static List<Food> queryFood_User(String foodName) {
        List<Food> list = new ArrayList<>();

        String sql = "select * from food where name like ?";
        Cursor cursor = db.rawQuery(sql, new String[]{"%" + foodName + "%"});//模糊查询
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String business = cursor.getString(cursor.getColumnIndex("businessId"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String des = cursor.getString(cursor.getColumnIndex("describe"));
            @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex("number"));
            @SuppressLint("Range") String price = cursor.getString(cursor.getColumnIndex("price"));
            @SuppressLint("Range") String img = cursor.getString(cursor.getColumnIndex("img"));
            @SuppressLint("Range") String mark=cursor.getString(cursor.getColumnIndex("mark"));
            Food food = new Food(id, business, name, des, number, price, img,mark);
            list.add(food);


        }
        return list;
    }
    //更新商品数量
    public static void updateFoodNum(String foodId,String foodNum){
        ContentValues values=new ContentValues();
        values.put("number",foodNum);

        try{
            db.update("food",
                    values,
                    "id=?",
                    new String[]{foodId});
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public static int getFoodSales(String foodId) {
        int totalSales = 0;

        // 修改后的SQL查询，添加JOIN和状态条件
        String query = "SELECT orderfood.quantity " +
                "FROM orderfood " +
                "JOIN orders o ON orderfood.order_id = o.id " +
                "WHERE orderfood.food_id = ? AND o.state = '3'";

        Cursor cursor = db.rawQuery(query, new String[]{foodId});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int foodSales = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));
                totalSales += foodSales;
            }
            cursor.close(); // 关闭游标
        }

        return totalSales;
    }

    public static String[] getOrderFoodBasicInfo(String foodId, String orderId) {

        int totalQuantity = 0;
        double totalPrice = 0.0;
        String[] result = new String[2];
        Cursor cursor = db.rawQuery("SELECT quantity, price FROM orderfood WHERE food_id=? AND order_id=?", new String[]{foodId, orderId});

        if (cursor.moveToFirst()) {
            do {
                // 从游标中获取数量和价格z
                @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex("quantity"));
                @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex("price"));
                totalQuantity += quantity;
                totalPrice += price * quantity;
            } while (cursor.moveToNext());
        }
        cursor.close(); // 记得关闭游标

        result[0] = String.valueOf(totalQuantity);
        result[1] = String.valueOf(totalPrice);
        return result;
    }



}
