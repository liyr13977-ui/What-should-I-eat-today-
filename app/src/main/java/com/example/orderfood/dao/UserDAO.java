package com.example.orderfood.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.orderfood.db.DBUntil;
import com.example.orderfood.entity.Address;
import com.example.orderfood.entity.Business;
import com.example.orderfood.entity.Delivery;
import com.example.orderfood.entity.Order;
import com.example.orderfood.entity.User;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public static SQLiteDatabase db= DBUntil.con;

    public static int saveUser(String account,String pwd,String nickName,String gender,String address,String phone,String img){
        String[] data={account,pwd,nickName,gender,address,phone,img};
        try{
            db.execSQL("INSERT INTO user(account,pwd,nickName,gender,address,phone,img)"+
                    "VALUES(?,?,?,?,?,?,?)",data);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }
    public static int saveBusiness(String account,String pwd,String phone,String name,String des,String fee,String img){
        String[] data={account,pwd,phone,name,des,fee,img,"0"};
        try{
            db.execSQL("INSERT INTO business(account,pwd,phone,name,describe,delivery,img,mark)"+
                    "VALUES(?,?,?,?,?,?,?,?)",data);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }
    public static int registerDeliveryMan(String account,String pwd,String name,String intro,String phone){
        String[] data={account,pwd,name,intro,"5",phone};
        try{
            db.execSQL("INSERT INTO delivery(account,pwd,name,describe,mark,phone)"+
                    "VALUES(?,?,?,?,?,?)",data);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }
    public static int loginUser(Context context,String account,String pwd){
        String[] data={account,pwd};
        String sql="select * from user where account=? and pwd=?";
        Cursor cursor=db.rawQuery(sql,data);
        while(cursor.moveToNext()){
            @SuppressLint("Range") int userId=cursor.getInt(cursor.getColumnIndex("id"));
            saveUserId(context,userId);
            return 1;
        }
        return 0;
    }
    public static int loginDelivery(Context context,String account,String pwd){
        String[] data={account,pwd};
        String sql="select * from delivery where account=? and pwd=?";
        Cursor cursor=db.rawQuery(sql,data);
        while(cursor.moveToNext()){
            @SuppressLint("Range") int DeliveryId=cursor.getInt(cursor.getColumnIndex("id"));
            saveDeliveryManId(context,DeliveryId);
            return 1;
        }
        return 0;
    }
    @SuppressLint("Range")
    public static int loginBusiness(Context context,String account, String pwd){
        String[] data={account,pwd};
        String sql="select * from business where account=? and pwd=?";
        Cursor cursor=db.rawQuery(sql,data);
        while(cursor.moveToNext()){
            int businessId=cursor.getInt(cursor.getColumnIndex("id"));
            saveBusinessId(context,businessId);
            return 1;
        }
        return 0;
    }
    public static void saveBusinessId(Context context, int businessId){
        SharedPreferences sp=context.getSharedPreferences("BusinessId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt("businessId",businessId);
        editor.apply();
    }

    public static void saveUserId(Context context,int userId){
        SharedPreferences sp=context.getSharedPreferences("userId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt("userId",userId);
        editor.apply();
    }
    public static void saveDeliveryManId(Context context,int DeliveryManId){
        SharedPreferences sp=context.getSharedPreferences("DeliveryManId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putInt("DeliveryManId",DeliveryManId);
        editor.apply();
    }
    public static Business getBusinessById(String id){
        Business business=new Business();
        String sql="select * from business where id=?";
        Cursor cursor=db.rawQuery(sql,new String[]{id});
        if(cursor.moveToNext()){
            @SuppressLint("Range") String account=cursor.getString(cursor.getColumnIndex("account"));
            @SuppressLint("Range") String pwd=cursor.getString(cursor.getColumnIndex("pwd"));
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String describe=cursor.getString(cursor.getColumnIndex("describe"));
            @SuppressLint("Range") String delivery=cursor.getString(cursor.getColumnIndex("delivery"));
            @SuppressLint("Range") String img=cursor.getString(cursor.getColumnIndex("img"));
            @SuppressLint("Range") String mark=cursor.getString(cursor.getColumnIndex("mark"));
            @SuppressLint("Range") String phone=cursor.getString(cursor.getColumnIndex("phone"));
            business.setId(id);
            business.setAccount(account);
            business.setPwd(pwd);
            business.setName(name);
            business.setDescribe(describe);
            business.setDelivery(delivery);
            business.setImg(img);
            business.setMark(mark);
            business.setPhone(phone);
            return business;
        }else{
            return null;
        }
    }
    public static Business getBusinessByOrderId(String id){
        Business business=new Business();
        String sql = "SELECT b.* FROM business b INNER JOIN orders o ON b.id = o.businessId WHERE o.id = ?";
        Cursor cursor=db.rawQuery(sql,new String[]{id});
        if(cursor != null && cursor.moveToNext()){
            @SuppressLint("Range") String bId = cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String account=cursor.getString(cursor.getColumnIndex("account"));
            @SuppressLint("Range") String pwd=cursor.getString(cursor.getColumnIndex("pwd"));
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String describe=cursor.getString(cursor.getColumnIndex("describe"));
            @SuppressLint("Range") String delivery=cursor.getString(cursor.getColumnIndex("delivery"));
            @SuppressLint("Range") String img=cursor.getString(cursor.getColumnIndex("img"));
            @SuppressLint("Range") String mark=cursor.getString(cursor.getColumnIndex("mark"));
            @SuppressLint("Range") String phone=cursor.getString(cursor.getColumnIndex("phone"));
            business.setId(bId);
            business.setAccount(account);
            business.setPwd(pwd);
            business.setName(name);
            business.setDescribe(describe);
            business.setDelivery(delivery);
            business.setImg(img);
            business.setMark(mark);
            business.setPhone(phone);
            cursor.close();
            return business;
        }else{
            if (cursor != null) cursor.close();
            return null;
        }
    }

    public static User getUserById(String UserId){
        User user=new User();
        String sql="select * from user where id=?";
        Cursor cursor=db.rawQuery(sql,new String[]{UserId});
        if(cursor.moveToNext()){
            @SuppressLint("Range") String account=cursor.getString(cursor.getColumnIndex("account"));
            @SuppressLint("Range") String pwd=cursor.getString(cursor.getColumnIndex("pwd"));
            @SuppressLint("Range") String nickName=cursor.getString(cursor.getColumnIndex("nickName"));
            @SuppressLint("Range") String gender=cursor.getString(cursor.getColumnIndex("gender"));
            @SuppressLint("Range") String address=cursor.getString(cursor.getColumnIndex("address"));
            @SuppressLint("Range") String phone=cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String img=cursor.getString(cursor.getColumnIndex("img"));
            user.setId(UserId);
            user.setAccount(account);
            user.setPwd(pwd);
            user.setNickName(nickName);
            user.setGender(gender);
            user.setAddress(address);
            user.setPhone(phone);
            user.setImg(img);
            return user;
        }else{
            return null;
        }
    }
    public static Delivery getDeliveryManById(String DeliveryManId){
        Delivery delivery=new Delivery();
        String sql="select * from delivery where id=?";
        Cursor cursor=db.rawQuery(sql,new String[]{DeliveryManId});
        if(cursor.moveToNext()){
            @SuppressLint("Range") String account=cursor.getString(cursor.getColumnIndex("account"));
            @SuppressLint("Range") String pwd=cursor.getString(cursor.getColumnIndex("pwd"));
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String intro=cursor.getString(cursor.getColumnIndex("describe"));
            @SuppressLint("Range") String mark=cursor.getString(cursor.getColumnIndex("mark"));
            @SuppressLint("Range") String phone=cursor.getString(cursor.getColumnIndex("phone"));
            delivery.setId(DeliveryManId);
            delivery.setAccount(account);
            delivery.setPwd(pwd);
            delivery.setName(name);
            delivery.setDescribe(intro);
            delivery.setMark(mark);
            delivery.setPhone(phone);
            return delivery;
        }else{
            return null;
        }
    }
    public static List<Business> getAllBusi(){
        List<Business> list=new ArrayList<>();
        Cursor cursor=db.rawQuery("select * from business",null);
        while(cursor.moveToNext()){
            @SuppressLint("Range") String id=cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String account=cursor.getString(cursor.getColumnIndex("account"));
            @SuppressLint("Range") String pwd=cursor.getString(cursor.getColumnIndex("pwd"));
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String describe=cursor.getString(cursor.getColumnIndex("describe"));
            @SuppressLint("Range") String img=cursor.getString(cursor.getColumnIndex("img"));
            @SuppressLint("Range") String delivery=cursor.getString(cursor.getColumnIndex("delivery"));
            @SuppressLint("Range") String mark=cursor.getString(cursor.getColumnIndex("mark"));
            @SuppressLint("Range") String phone=cursor.getString(cursor.getColumnIndex("phone"));
            Business business=new Business(id,account,pwd,name,describe,img,delivery,mark,phone);
            list.add(business);
        }
        return list;
    }
    public static int updateBusiness(String id,String name,String shopPhone,String fee,String describe,String img){
        ContentValues values=new ContentValues();
        values.put("name",name);
        values.put("phone",shopPhone);
        values.put("delivery",fee);
        values.put("describe",describe);
        values.put("img",img);
        try{
            db.update("business",//表名
                    values,//更新的字段
                    "id=?",//where子句
                    new String[]{id});//where子句的参数
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    public static int updateOrderDC(String orderId,String deliveryMan){
        ContentValues values=new ContentValues();
        values.put("state","7");
        values.put("delivery",deliveryMan);
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
    public static int updateDeliveryMan(String id,String pwd,String name,String phone,String intro){
        ContentValues values=new ContentValues();
        values.put("pwd",pwd);
        values.put("name",name);
        values.put("phone",phone);
        values.put("describe",intro);
        try{
            db.update("delivery",//表名
                    values,//更新的字段
                    "id=?",//where子句
                    new String[]{id});//where子句的参数
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    public static int updateBusinessPwd(String id,String pwd){
        ContentValues values=new ContentValues();
        values.put("pwd",pwd);

        try{
            db.update("business",//表名
                    values,//更新的字段
                    "id=?",//where子句
                    new String[]{id});//where子句的参数
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @SuppressLint("Range")
    public static String getBusinessMark(String id) {
        String sql = "select * from business where id=?";
        Cursor cursor = db.rawQuery(sql, new String[]{id});
        String mark="0";
        while (cursor.moveToNext()) {
            mark = cursor.getString(cursor.getColumnIndex("mark"));
        }
        return mark;
    }
    @SuppressLint("Range")
    public static String getDelivery(String id) {
        String sql = "select * from orders where businessId=?";
        Cursor cursor = db.rawQuery(sql, new String[]{id});
        String mark=null;
        while (cursor.moveToNext()) {
            mark = cursor.getString(cursor.getColumnIndex("mark"));
        }
        return mark;
    }
    public static String getBusinessSale(String id){
        String sql = "select * from orders where businessId=?";
        Cursor cursor = db.rawQuery(sql, new String[]{id});
        int num = 0;
        while (cursor.moveToNext()) {
            num++;
        }
        return String.valueOf(num);
    }


    public static int logout(String businessId){
        try {
            db.delete("food",//表名
                    "businessId=?",//where子句
                    new String[]{businessId});//where子句的参数

            db.delete("business",//表名
                    "id=?",//where子句
                    new String[]{businessId});//where子句的参数

            db.delete("orders",//表名
                    "businessId=?",//where子句
                    new String[]{businessId});//where子句的参数

            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static List<Address> getAddressById(String userId){
        List<Address> addressList=new ArrayList<>();
        String sql="select * from address where user_id=?";
        Cursor cursor=db.rawQuery(sql,new String[]{userId});
        while(cursor.moveToNext()){
            @SuppressLint("Range") String id=cursor.getString(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String name=cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String address=cursor.getString(cursor.getColumnIndex("address"));
            @SuppressLint("Range") String phone=cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String is_default=cursor.getString(cursor.getColumnIndex("is_default"));
            Address address1=new Address(id,userId,name,address,phone,is_default);
            addressList.add(address1);
        }
        return addressList;
    }

    public static int updateUserAddress(String addressId, String newName,String newAddress, String newPhone){
        ContentValues values=new ContentValues();
        values.put("name",newName);
        values.put("address",newAddress);
        values.put("phone",newPhone);
        try{
            db.update("address",//表名
                    values,//更新的字段
                    "id=?",//where子句
                    new String[]{addressId});//where子句的参数
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    public static void newUserAddress(String userId,String newName,String newAddress, String newPhone){
        // 检查用户是否已经有默认地址
        String defaultAddressId=AddressDAO.getDefaultAddressId(userId);
        String isDefault = (defaultAddressId==null) ? "1" : "0";
        ContentValues values = new ContentValues();
        values.put("user_id", userId);
        values.put("name", newName);
        values.put("address", newAddress);
        values.put("phone", newPhone);
        values.put("is_default", isDefault);

        db.insert("address", null, values);
    }
    public static int updateUserInfo(String userId,String pwd,String nickName,String gender,String phone,String userImg){
        ContentValues values=new ContentValues();
        values.put("pwd",pwd);
        values.put("nickName",nickName);
        values.put("gender",gender);
        values.put("phone",phone);
        values.put("img",userImg);
        try{
            db.update("user",//表名
                    values,//更新的字段
                    "id=?",//where子句
                    new String[]{userId});//where子句的参数
            return 1;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
}
