package com.example.orderfood.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.orderfood.db.DBUntil;
import com.example.orderfood.entity.Food;
import com.example.orderfood.entity.Order;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrderDAO {
    public static SQLiteDatabase db= DBUntil.con;

    public static int uploadOrder(String userId,String businessId,String address,String state,String foodJson,String name,String phone,String remark){
        //实时获取时间
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.MINUTE,30067);
        Date now= calendar.getTime();
        String date=dateFormat.format(now);


        String[] data={userId,businessId,date,address,state,name,phone,remark};
        String sql="INSERT INTO orders(userId,businessId,date,address,state,name,phone,remark) VALUES(?,?,?,?,?,?,?,?)";
        try{
            db.execSQL(sql,data);
            // 通过 rawQuery 获取最后插入的 ID
            Cursor cursor = db.rawQuery("SELECT last_insert_rowid()", null);
            long orderId=0;
            if (cursor.moveToFirst()) {
                orderId = cursor.getLong(0); //获取orderId
            }
            cursor.close();

            //处理foodJson，将商品信息添加到orderfood表中
            JSONArray jsonArray=new JSONArray(foodJson);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String foodId=jsonObject.getString("foodId");
                String foodNum=jsonObject.getString("foodNum");
                String foodPrice=jsonObject.getString("foodPrice");
                String sql1 = "INSERT INTO orderfood(order_id, food_id, quantity, price) VALUES(?, ?, ?, ?)";
                try {
                    db.execSQL(sql1, new String[]{String.valueOf(orderId), foodId, foodNum, foodPrice});
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    public static List<Order> getOrder(String userId){
        List<Order> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from orders where userId=? order by date desc",new String[]{userId});
        while(cursor.moveToNext()){
            @SuppressLint("Range") String id=cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String businessId=cursor.getString(cursor.getColumnIndex("businessId"));
            @SuppressLint("Range") String date=cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String address=cursor.getString(cursor.getColumnIndex("address"));
            @SuppressLint("Range") String state=cursor.getString(cursor.getColumnIndex("state"));
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String phone=cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String mark=cursor.getString(cursor.getColumnIndex("mark"));
            @SuppressLint("Range") String delivery=cursor.getString(cursor.getColumnIndex("delivery"));
            @SuppressLint("Range") String remark=cursor.getString(cursor.getColumnIndex("remark"));
            @SuppressLint("Range") String evaluate=cursor.getString(cursor.getColumnIndex("evaluate"));
            @SuppressLint("Range") String deliveryMark=cursor.getString(cursor.getColumnIndex("deliveryMark"));
            Order order=new Order(id,userId,businessId,date,address,state,name,phone,mark,delivery,remark,evaluate,deliveryMark);
            list.add(order);
        }
        return list;
    }
    //7为配送中
    public static List<Order> getOrder7(){
        List<Order> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from orders where state=? order by date desc",new String[]{"7"});
        while(cursor.moveToNext()){
            @SuppressLint("Range") String id=cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String userId=cursor.getString(cursor.getColumnIndex("userId"));
            @SuppressLint("Range") String businessId=cursor.getString(cursor.getColumnIndex("businessId"));
            @SuppressLint("Range") String date=cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String address=cursor.getString(cursor.getColumnIndex("address"));
            @SuppressLint("Range") String state=cursor.getString(cursor.getColumnIndex("state"));
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String phone=cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String mark=cursor.getString(cursor.getColumnIndex("mark"));
            @SuppressLint("Range") String delivery=cursor.getString(cursor.getColumnIndex("delivery"));
            @SuppressLint("Range") String remark=cursor.getString(cursor.getColumnIndex("remark"));
            @SuppressLint("Range") String evaluate=cursor.getString(cursor.getColumnIndex("evaluate"));
            @SuppressLint("Range") String deliveryMark=cursor.getString(cursor.getColumnIndex("deliveryMark"));
            Order order=new Order(id,userId,businessId,date,address,state,name,phone,mark,delivery,remark,evaluate,deliveryMark);
            list.add(order);
        }
        return list;
    }

    //6为已完成
    public static List<Order> getUserOrder6(String userId){
        List<Order> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from orders where state in (?,?) and userId=? order by date desc",new String[]{"6","3",userId});
        while(cursor.moveToNext()){
            @SuppressLint("Range") String id=cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String businessId=cursor.getString(cursor.getColumnIndex("businessId"));
            @SuppressLint("Range") String date=cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String address=cursor.getString(cursor.getColumnIndex("address"));
            @SuppressLint("Range") String state=cursor.getString(cursor.getColumnIndex("state"));
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String phone=cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String mark=cursor.getString(cursor.getColumnIndex("mark"));
            @SuppressLint("Range") String delivery=cursor.getString(cursor.getColumnIndex("delivery"));
            @SuppressLint("Range") String remark=cursor.getString(cursor.getColumnIndex("remark"));
            @SuppressLint("Range") String evaluate=cursor.getString(cursor.getColumnIndex("evaluate"));
            @SuppressLint("Range") String deliveryMark=cursor.getString(cursor.getColumnIndex("deliveryMark"));
            Order order=new Order(id,userId,businessId,date,address,state,name,phone,mark,delivery,remark,evaluate,deliveryMark);
            list.add(order);
        }
        return list;
    }
    public static List<Order> getFinishOrderByDelivery(String deliveryMan){
        List<Order> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from orders where delivery=? and state=? order by date desc",new String[]{deliveryMan,"3"});
        while(cursor.moveToNext()){
            @SuppressLint("Range") String id=cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String userId=cursor.getString(cursor.getColumnIndex("userId"));
            @SuppressLint("Range") String businessId=cursor.getString(cursor.getColumnIndex("businessId"));
            @SuppressLint("Range") String date=cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String address=cursor.getString(cursor.getColumnIndex("address"));
            @SuppressLint("Range") String state=cursor.getString(cursor.getColumnIndex("state"));
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String phone=cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String mark=cursor.getString(cursor.getColumnIndex("mark"));
            @SuppressLint("Range") String remark=cursor.getString(cursor.getColumnIndex("remark"));
            @SuppressLint("Range") String evaluate=cursor.getString(cursor.getColumnIndex("evaluate"));
            @SuppressLint("Range") String deliveryMark=cursor.getString(cursor.getColumnIndex("deliveryMark"));
            Order order=new Order(id,userId,businessId,date,address,state,name,phone,mark,deliveryMan,remark,evaluate,deliveryMark);
            list.add(order);
        }
        return list;
    }
    public static List<Order> getBusiAllOrder(String businessId){
        List<Order> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from orders where businessId=? order by date desc",new String[]{businessId});
        while(cursor.moveToNext()){
            @SuppressLint("Range") String id=cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String userId=cursor.getString(cursor.getColumnIndex("userId"));
            @SuppressLint("Range") String date=cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String address=cursor.getString(cursor.getColumnIndex("address"));
            @SuppressLint("Range") String state=cursor.getString(cursor.getColumnIndex("state"));
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String phone=cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String mark=cursor.getString(cursor.getColumnIndex("mark"));
            @SuppressLint("Range") String delivery=cursor.getString(cursor.getColumnIndex("delivery"));
            @SuppressLint("Range") String remark=cursor.getString(cursor.getColumnIndex("remark"));
            @SuppressLint("Range") String evaluate=cursor.getString(cursor.getColumnIndex("evaluate"));
            @SuppressLint("Range") String deliveryMark=cursor.getString(cursor.getColumnIndex("deliveryMark"));
            Order order=new Order(id,userId,businessId,date,address,state,name,phone,mark,delivery,remark,evaluate,deliveryMark);
            list.add(order);
        }
        return list;
    }
    public static List<Order> getBusiOrderDown(String businessId){
        List<Order> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from orders where businessId=? and state in (?,?) order by date desc",new String[]{businessId,"3","6"});
        while(cursor.moveToNext()){
            @SuppressLint("Range") String id=cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String userId=cursor.getString(cursor.getColumnIndex("userId"));
            @SuppressLint("Range") String date=cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String address=cursor.getString(cursor.getColumnIndex("address"));
            @SuppressLint("Range") String state=cursor.getString(cursor.getColumnIndex("state"));
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String phone=cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String mark=cursor.getString(cursor.getColumnIndex("mark"));
            @SuppressLint("Range") String delivery=cursor.getString(cursor.getColumnIndex("delivery"));
            @SuppressLint("Range") String remark=cursor.getString(cursor.getColumnIndex("remark"));
            @SuppressLint("Range") String evaluate=cursor.getString(cursor.getColumnIndex("evaluate"));
            @SuppressLint("Range") String deliveryMark=cursor.getString(cursor.getColumnIndex("deliveryMark"));
            Order order=new Order(id,userId,businessId,date,address,state,name,phone,mark,delivery,remark,evaluate,deliveryMark);
            list.add(order);
        }
        return list;
    }
    public static List<Order> getBusiOrderComment(String businessId){
        List<Order> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from orders where businessId=? and state =? order by date desc",new String[]{businessId,"6"});
        while(cursor.moveToNext()){
            @SuppressLint("Range") String id=cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String userId=cursor.getString(cursor.getColumnIndex("userId"));
            @SuppressLint("Range") String date=cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String address=cursor.getString(cursor.getColumnIndex("address"));
            @SuppressLint("Range") String state=cursor.getString(cursor.getColumnIndex("state"));
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String phone=cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String mark=cursor.getString(cursor.getColumnIndex("mark"));
            @SuppressLint("Range") String delivery=cursor.getString(cursor.getColumnIndex("delivery"));
            @SuppressLint("Range") String remark=cursor.getString(cursor.getColumnIndex("remark"));
            @SuppressLint("Range") String evaluate=cursor.getString(cursor.getColumnIndex("evaluate"));
            @SuppressLint("Range") String deliveryMark=cursor.getString(cursor.getColumnIndex("deliveryMark"));
            Order order=new Order(id,userId,businessId,date,address,state,name,phone,mark,delivery,remark,evaluate,deliveryMark);
            list.add(order);
        }
        return list;
    }
    public static List<Order> getBusiOrderDown2(String businessId){
        List<Order> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from orders where businessId=? and state in (?,?) order by date desc",new String[]{businessId,"3","6"});
        while(cursor.moveToNext()){
            @SuppressLint("Range") String id=cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String userId=cursor.getString(cursor.getColumnIndex("userId"));
            @SuppressLint("Range") String date=cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String address=cursor.getString(cursor.getColumnIndex("address"));
            @SuppressLint("Range") String state=cursor.getString(cursor.getColumnIndex("state"));
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String phone=cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String mark=cursor.getString(cursor.getColumnIndex("mark"));
            @SuppressLint("Range") String delivery=cursor.getString(cursor.getColumnIndex("delivery"));
            @SuppressLint("Range") String remark=cursor.getString(cursor.getColumnIndex("remark"));
            @SuppressLint("Range") String evaluate=cursor.getString(cursor.getColumnIndex("evaluate"));
            @SuppressLint("Range") String deliveryMark=cursor.getString(cursor.getColumnIndex("deliveryMark"));
            Order order=new Order(id,userId,businessId,date,address,state,name,phone,mark,delivery,remark,evaluate,deliveryMark);
            list.add(order);
        }
        return list;
    }
    public static List<Order> getBusiUnderwayOrder(String businessId){
        List<Order> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from orders where state in (?,?) and businessId=? order by date desc",new String[]{"4","5",businessId});
        while(cursor.moveToNext()){
            @SuppressLint("Range") String id=cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String userId=cursor.getString(cursor.getColumnIndex("userId"));
            @SuppressLint("Range") String date=cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String address=cursor.getString(cursor.getColumnIndex("address"));
            @SuppressLint("Range") String state=cursor.getString(cursor.getColumnIndex("state"));
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String phone=cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String mark=cursor.getString(cursor.getColumnIndex("mark"));
            @SuppressLint("Range") String delivery=cursor.getString(cursor.getColumnIndex("delivery"));
            @SuppressLint("Range") String remark=cursor.getString(cursor.getColumnIndex("remark"));
            @SuppressLint("Range") String evaluate=cursor.getString(cursor.getColumnIndex("evaluate"));
            @SuppressLint("Range") String deliveryMark=cursor.getString(cursor.getColumnIndex("deliveryMark"));
            Order order=new Order(id,userId,businessId,date,address,state,name,phone,mark,delivery,remark,evaluate,deliveryMark);
            list.add(order);
        }
        return list;
    }
    public static List<Order> getFinishOrder(String userId){
        List<Order> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from orders where userId=? and state=? order by date desc",new String[]{userId,"3"});
        while(cursor.moveToNext()){
            @SuppressLint("Range") String id=cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String businessId=cursor.getString(cursor.getColumnIndex("businessId"));
            @SuppressLint("Range") String date=cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String address=cursor.getString(cursor.getColumnIndex("address"));
            @SuppressLint("Range") String state=cursor.getString(cursor.getColumnIndex("state"));
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String phone=cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String mark=cursor.getString(cursor.getColumnIndex("mark"));
            @SuppressLint("Range") String delivery=cursor.getString(cursor.getColumnIndex("delivery"));
            @SuppressLint("Range") String remark=cursor.getString(cursor.getColumnIndex("remark"));
            @SuppressLint("Range") String evaluate=cursor.getString(cursor.getColumnIndex("evaluate"));
            @SuppressLint("Range") String deliveryMark=cursor.getString(cursor.getColumnIndex("deliveryMark"));
            Order order=new Order(id,userId,businessId,date,address,state,name,phone,mark,delivery,remark,evaluate,deliveryMark);
            list.add(order);
        }
        return list;
    }
    public static List<Order> getAwaitingOrder(String userId){
        List<Order> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from orders where userId=? and state in (?,?,?) order by date desc",new String[]{userId,"1","4","7"});
        while(cursor.moveToNext()){
            @SuppressLint("Range") String id=cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String businessId=cursor.getString(cursor.getColumnIndex("businessId"));
            @SuppressLint("Range") String date=cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String address=cursor.getString(cursor.getColumnIndex("address"));
            @SuppressLint("Range") String state=cursor.getString(cursor.getColumnIndex("state"));
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String phone=cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String mark=cursor.getString(cursor.getColumnIndex("mark"));
            @SuppressLint("Range") String delivery=cursor.getString(cursor.getColumnIndex("delivery"));
            @SuppressLint("Range") String remark=cursor.getString(cursor.getColumnIndex("remark"));
            @SuppressLint("Range") String evaluate=cursor.getString(cursor.getColumnIndex("evaluate"));
            @SuppressLint("Range") String deliveryMark=cursor.getString(cursor.getColumnIndex("deliveryMark"));
            Order order=new Order(id,userId,businessId,date,address,state,name,phone,mark,delivery,remark,evaluate,deliveryMark);
            list.add(order);
        }
        return list;
    }
    public static List<Order> getAwaitingOrderByBusiness(String businessId){
        List<Order> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from orders where state=? and businessId=? order by date desc",new String[]{"1",businessId});
        while(cursor.moveToNext()){
            @SuppressLint("Range") String id=cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String userId=cursor.getString(cursor.getColumnIndex("userId"));
            @SuppressLint("Range") String date=cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String address=cursor.getString(cursor.getColumnIndex("address"));
            @SuppressLint("Range") String state=cursor.getString(cursor.getColumnIndex("state"));
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String phone=cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String mark=cursor.getString(cursor.getColumnIndex("mark"));
            @SuppressLint("Range") String delivery=cursor.getString(cursor.getColumnIndex("delivery"));
            @SuppressLint("Range") String remark=cursor.getString(cursor.getColumnIndex("remark"));
            @SuppressLint("Range") String evaluate=cursor.getString(cursor.getColumnIndex("evaluate"));
            @SuppressLint("Range") String deliveryMark=cursor.getString(cursor.getColumnIndex("deliveryMark"));
            Order order=new Order(id,userId,businessId,date,address,state,name,phone,mark,delivery,remark,evaluate,deliveryMark);
            list.add(order);
        }
        return list;
    }
    public static List<Order> getCompleteOrderByBusiness(String businessId){
        List<Order> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from orders where state=? and businessId=? order by date desc",new String[]{"3",businessId});
        while(cursor.moveToNext()){
            @SuppressLint("Range") String id=cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String userId=cursor.getString(cursor.getColumnIndex("userId"));
            @SuppressLint("Range") String date=cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String address=cursor.getString(cursor.getColumnIndex("address"));
            @SuppressLint("Range") String state=cursor.getString(cursor.getColumnIndex("state"));
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String phone=cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String mark=cursor.getString(cursor.getColumnIndex("mark"));
            @SuppressLint("Range") String delivery=cursor.getString(cursor.getColumnIndex("delivery"));
            @SuppressLint("Range") String remark=cursor.getString(cursor.getColumnIndex("remark"));
            @SuppressLint("Range") String evaluate=cursor.getString(cursor.getColumnIndex("evaluate"));
            @SuppressLint("Range") String deliveryMark=cursor.getString(cursor.getColumnIndex("deliveryMark"));
            Order order=new Order(id,userId,businessId,date,address,state,name,phone,mark,delivery,remark,evaluate,deliveryMark);
            list.add(order);
        }
        return list;
    }
    public static List<Order> getCancelOrder(String userId){
        List<Order> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from orders where userId=? and state =? order by date desc",new String[]{userId,"2"});
        while(cursor.moveToNext()){
            @SuppressLint("Range") String id=cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String businessId=cursor.getString(cursor.getColumnIndex("businessId"));
            @SuppressLint("Range") String date=cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String address=cursor.getString(cursor.getColumnIndex("address"));
            @SuppressLint("Range") String state=cursor.getString(cursor.getColumnIndex("state"));
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String phone=cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String mark=cursor.getString(cursor.getColumnIndex("mark"));
            @SuppressLint("Range") String delivery=cursor.getString(cursor.getColumnIndex("delivery"));
            @SuppressLint("Range") String remark=cursor.getString(cursor.getColumnIndex("remark"));
            @SuppressLint("Range") String evaluate=cursor.getString(cursor.getColumnIndex("evaluate"));
            @SuppressLint("Range") String deliveryMark=cursor.getString(cursor.getColumnIndex("deliveryMark"));
            Order order=new Order(id,userId,businessId,date,address,state,name,phone,mark,delivery,remark,evaluate,deliveryMark);
            list.add(order);
        }
        return list;
    }
    public static List<Order> getCancelOrderByBisiness(String businessId){
        List<Order> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from orders where state=? and businessId=? order by date desc",new String[]{"2",businessId});
        while(cursor.moveToNext()){
            @SuppressLint("Range") String id=cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String userId=cursor.getString(cursor.getColumnIndex("userId"));
            @SuppressLint("Range") String date=cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String address=cursor.getString(cursor.getColumnIndex("address"));
            @SuppressLint("Range") String state=cursor.getString(cursor.getColumnIndex("state"));
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String phone=cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String mark=cursor.getString(cursor.getColumnIndex("mark"));
            @SuppressLint("Range") String delivery=cursor.getString(cursor.getColumnIndex("delivery"));
            @SuppressLint("Range") String remark=cursor.getString(cursor.getColumnIndex("remark"));
            @SuppressLint("Range") String evaluate=cursor.getString(cursor.getColumnIndex("evaluate"));
            @SuppressLint("Range") String deliveryMark=cursor.getString(cursor.getColumnIndex("deliveryMark"));
            Order order=new Order(id,userId,businessId,date,address,state,name,phone,mark,delivery,remark,evaluate,deliveryMark);
            list.add(order);
        }
        return list;
    }
    public static void ensureOrder(String OrderId){
        ContentValues values = new ContentValues();
        values.put("state", 3);
        db.update("orders", values, "id = ?", new String[]{OrderId}); // 更新指定 orderId 的订单状态
    }
    public static void finishOrder(String OrderId){
        ContentValues values = new ContentValues();
        values.put("state", 6);
        db.update("orders", values, "id = ?", new String[]{OrderId}); // 更新指定 orderId 的订单状态
    }
    public static void cancelOrder(String OrderId){
        ContentValues values = new ContentValues();
        values.put("state", 2);
        db.update("orders", values, "id = ?", new String[]{OrderId}); // 更新指定 orderId 的订单状态
    }
    public static void cancelOrderDelivery(String OrderId){
        ContentValues values = new ContentValues();
        values.put("state", 4);
        values.put("delivery","");
        db.update("orders", values, "id = ?", new String[]{OrderId}); // 更新指定 orderId 的订单状态
    }

    public static void ensureFood(String OrderId){
        ContentValues values = new ContentValues();
        values.put("state", 4);
        db.update("orders", values, "id = ?", new String[]{OrderId}); // 更新指定 orderId 的订单状态
    }

    public static Food getOneFoodBasicInfo(String orderId) {
        Food food = null;
        Cursor cursor = null;
        Cursor cursor1 = null;

        try {
            cursor = db.rawQuery("SELECT * FROM orderfood WHERE order_id=?", new String[]{orderId});
            if (cursor.moveToFirst()) { // 确保游标移动到第一个记录
                @SuppressLint("Range") String foodId = cursor.getString(cursor.getColumnIndex("food_id"));

                cursor1 = db.rawQuery("SELECT * FROM food WHERE id=?", new String[]{foodId});
                if (cursor1.moveToFirst()) { // 确保游标移动到第一个记录
                    @SuppressLint("Range") String businessId = cursor1.getString(cursor1.getColumnIndex("businessId"));
                    @SuppressLint("Range") String name = cursor1.getString(cursor1.getColumnIndex("name"));
                    @SuppressLint("Range") String describe = cursor1.getString(cursor1.getColumnIndex("describe"));
                    @SuppressLint("Range") String number = cursor1.getString(cursor1.getColumnIndex("number"));
                    @SuppressLint("Range") String price = cursor1.getString(cursor1.getColumnIndex("price"));
                    @SuppressLint("Range") String img = cursor1.getString(cursor1.getColumnIndex("img"));
                    @SuppressLint("Range") String mark = cursor1.getString(cursor1.getColumnIndex("mark"));
                    food = new Food(foodId, businessId, name, describe, number, price, img,mark);
                } else {
                    Log.e("Database", "No matching food found for foodId: " + foodId);
                }
            } else {
                Log.e("Database", "No matching orderfood found for orderId: " + orderId);
            }
        } catch (Exception e) {
            Log.e("Database", "Error retrieving food information", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (cursor1 != null) {
                cursor1.close();
            }
        }

        return food;
    }
    public static void updateOrderMark(String orderId,String mark){
        ContentValues values=new ContentValues();
        values.put("mark",mark);
        try{
            db.update("orders",//表名
                    values,//更新的字段
                    "id=?",//where子句
                    new String[]{orderId});//where子句的参数
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void updateBusinessMark(String businessId,String mark){
        ContentValues values=new ContentValues();
        values.put("mark",mark);
        try{
            db.update("business",//表名
                    values,//更新的字段
                    "id=?",//where子句
                    new String[]{businessId});//where子句的参数
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    //粗略展示订单信息
    // 统计订单中所有商品的总数量和总金额
    public static String[] getOneFoodBasicInfo2(String orderId) {
        int totalQuantity = 0;
        double totalPrice = 0.0;
        Cursor cursor = db.rawQuery("SELECT quantity, price FROM orderfood WHERE order_id=?", new String[]{orderId});

        if (cursor.moveToFirst()) {
            do {
                // 从游标中获取数量和价格
                @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex("quantity"));
                @SuppressLint("Range") double price = cursor.getDouble(cursor.getColumnIndex("price"));

                // 累加数量和价格
                totalQuantity += quantity;
                totalPrice += price * quantity;

            } while (cursor.moveToNext());
        }
        cursor.close(); // 记得关闭游标

        // 将结果转换为字符串数组并返回

        return new String[]{String.valueOf(totalQuantity), String.valueOf(totalPrice)};
    }
    @SuppressLint("Range")
    public static float getDeliveryByOrder(String orderId){
        float delivery=-1.0f;
        Cursor cursor = db.rawQuery("select business.delivery " +
                        "FROM business "+
                        "join orders on business.id = orders.businessId " +
                        "where orders.id=?",
                new String[]{String.valueOf(orderId)});

        if(cursor.moveToFirst()){
            delivery=cursor.getFloat(cursor.getColumnIndex("delivery"));
        }
        cursor.close();
        return delivery;

    }
    public static Order getOrderById(String id){
        Order order=new Order();
        @SuppressLint("Recycle") Cursor cursor=db.rawQuery("select * from orders where id=?",new String[]{id});
        if(cursor.moveToNext()){
            @SuppressLint("Range") String userId=cursor.getString(cursor.getColumnIndex("userId"));
            @SuppressLint("Range") String businessId=cursor.getString(cursor.getColumnIndex("businessId"));
            @SuppressLint("Range") String date=cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String address=cursor.getString(cursor.getColumnIndex("address"));
            @SuppressLint("Range") String state=cursor.getString(cursor.getColumnIndex("state"));
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String phone=cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String mark=cursor.getString(cursor.getColumnIndex("mark"));
            @SuppressLint("Range") String delivery=cursor.getString(cursor.getColumnIndex("delivery"));
            @SuppressLint("Range") String remark=cursor.getString(cursor.getColumnIndex("remark"));
            @SuppressLint("Range") String evaluate=cursor.getString(cursor.getColumnIndex("evaluate"));
            @SuppressLint("Range") String deliveryMark=cursor.getString(cursor.getColumnIndex("deliveryMark"));
            order.setUserId(userId);
            order.setBusinessId(businessId);
            order.setDate(date);
            order.setAddress(address);
            order.setState(state);
            order.setName(name);
            order.setPhone(phone);
            order.setMark(mark);
            order.setDelivery(delivery);
            order.setRemark(remark);
            order.setEvaluate(evaluate);
            order.setDeliveryMark(deliveryMark);
            return order;
        }else{
            return null;
        }
    }

    public static List<Order> getDeliveryOrderList(){
        List<Order> list=new ArrayList<>();
        @SuppressLint("Recycle") Cursor cursor=db.rawQuery("select * from orders where state=?",new String[]{"4"});
        while(cursor.moveToNext()){
            @SuppressLint("Range") String id=cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String userId=cursor.getString(cursor.getColumnIndex("userId"));
            @SuppressLint("Range") String businessId=cursor.getString(cursor.getColumnIndex("businessId"));
            @SuppressLint("Range") String date=cursor.getString(cursor.getColumnIndex("date"));
            @SuppressLint("Range") String address=cursor.getString(cursor.getColumnIndex("address"));
            String state="4";
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String phone=cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String mark=cursor.getString(cursor.getColumnIndex("mark"));
            @SuppressLint("Range") String delivery=cursor.getString(cursor.getColumnIndex("delivery"));
            @SuppressLint("Range") String remark=cursor.getString(cursor.getColumnIndex("remark"));
            @SuppressLint("Range") String evaluate=cursor.getString(cursor.getColumnIndex("evaluate"));
            @SuppressLint("Range") String deliveryMark=cursor.getString(cursor.getColumnIndex("deliveryMark"));
            Order order=new Order(id,userId,businessId,date,address,state,name,phone,mark,delivery,remark,evaluate,deliveryMark);
            list.add(order);
        }
        return list;
    }


}
