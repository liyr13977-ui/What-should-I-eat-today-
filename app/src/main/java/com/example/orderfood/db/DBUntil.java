package com.example.orderfood.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import androidx.core.view.ViewPropertyAnimatorListener;

import com.example.orderfood.entity.Business;
import com.example.orderfood.entity.Order;

public class DBUntil extends SQLiteOpenHelper {
    private static final int version=474;//版本号，每次更改表结构都需要加1，否则不生效
    private static final String dbName="takeaway.db";
    public static SQLiteDatabase con;
    private static final String TABLE_USER="user";
    private static final String TABLE_BUSINESS="business";
    private static final String TABLE_FOOD="food";
    private static final String TABLE_ORDERS="orders";
    private static final String TABLE_ORDERFOOD="orderfood";
    private static final String TABLE_DELIVERY="delivery";
    private static final String TABLE_ADDRESS="address";
    private static final String TABLE_MESSAGES = "messages";

    private static final class UserFields {
        static final String ID = "id";
        static final String ACCOUNT = "account";
        static final String PWD = "pwd";
        static final String NICKNAME = "nickName";
        static final String GENDER = "gender";
        static final String ADDRESS = "address";
        static final String PHONE = "phone";
        static final String IMG = "img";
    }
    private static final class BusinessFields {
        static final String ID = "id";
        static final String ACCOUNT = "account";
        static final String PWD = "pwd";
        static final String NAME = "name";
        static final String PHONE = "phone";
        static final String DESCRIBE = "describe";
        static final String IMG = "img";
        static final String DELIVERY ="delivery";
        static final String MARK="mark";
    }
    private static final class DeliveryFields{
        static final String ID="id";
        static final String ACCOUNT = "account";
        static final String PWD = "pwd";
        static final String NAME = "name";
        static final String DESCRIBE = "describe";
        static final String PHONE = "phone";
        static final String MARK="mark";
    }
    private static final class FoodFields {
        static final String ID = "id";
        static final String BUSINESS_ID = "businessId";
        static final String NAME = "name";
        static final String DESCRIBE = "describe";
        static final String NUMBER = "number";
        static final String PRICE = "price";
        static final String IMG = "img";
        static final String MARK="mark";
    }
    private static final class OrdersFields {
        static final String ID = "id";
        static final String USER_ID = "userId";
        static final String BUSINESS_ID = "businessId";
        static final String DATE = "date";
        static final String ADDRESS = "address";
        static final String STATE="state";
        static final String NAME = "name";
        static final String PHONE = "phone";
        static final String MARK="mark";
        static final String DELIVERY="delivery";
        static final String REMARK="remark";
        static final String EVALUATE="evaluate";
        static final String DELIVERYMARK="deliveryMark";
    }

    private static final class OrderItemsFields {
        static final String ID = "id";
        static final String ORDER_ID = "order_id";
        static final String FOOD_ID = "food_id";
        static final String QUANTITY = "quantity";
        static final String PRICE = "price";
        static final String MARK="mark";
    }
    private static final class AddressFields {
        static final String ID="id";
        static final String USER_ID="user_id";
        static final String NAME = "name";
        static final String ADDRESS = "address";
        static final String PHONE = "phone";

    }

    private static final class MessagesFields {
        static final String ID = "id";
        static final String BUSINESS_ID = "businessId";
        static final String USER_ID = "userId";
        static final String CONTENT = "content";
        static final String DATE = "date";
        static final String SENDER = "sender";
    }

    public DBUntil(Context context) {
        super(context, dbName, null, version,null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=false");

        createUserTable(db);
        createBusinessTable(db);
        createDeliveryTable(db);
        createFoodTable(db);
        createOrdersTable(db);
        createOrderItemsTable(db);
        createAddressTable(db);
        createMessagesTable(db);


        db.execSQL("PRAGMA foreign_keys=true");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUSINESS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DELIVERY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        onCreate(db);
    }

    public void createUserTable(SQLiteDatabase db){

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("CREATE TABLE " + TABLE_USER + " (" +
                UserFields.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserFields.ACCOUNT + " VARCHAR(20) UNIQUE, " +
                UserFields.PWD + " VARCHAR(20), " +
                UserFields.NICKNAME + " VARCHAR(20), " +
                UserFields.GENDER + " VARCHAR(10), " +
                UserFields.ADDRESS + " VARCHAR(100), " +
                UserFields.PHONE + " VARCHAR(20), " +
                UserFields.IMG + " VARCHAR(255)" +
                ")");

    }
    private void insertUser(SQLiteDatabase db, String account, String pwd, String nickName, String gender, String address, String phone, String img) {
        ContentValues values = new ContentValues();
        values.put(UserFields.ACCOUNT, account);
        values.put(UserFields.PWD, pwd);
        values.put(UserFields.NICKNAME, nickName);
        values.put(UserFields.GENDER, gender);
        values.put(UserFields.ADDRESS, address);
        values.put(UserFields.PHONE, phone);
        values.put(UserFields.IMG, img);
        db.insert(TABLE_USER, null, values);
    }

    private void createBusinessTable(SQLiteDatabase db) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUSINESS);
        db.execSQL("CREATE TABLE " + TABLE_BUSINESS + " (" +
                BusinessFields.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BusinessFields.ACCOUNT + " VARCHAR(20) UNIQUE, " +
                BusinessFields.PWD + " VARCHAR(20), " +
                BusinessFields.NAME + " VARCHAR(20), " +
                BusinessFields.DESCRIBE + " VARCHAR(200), " +
                BusinessFields.PHONE + " VARCHAR(50), " +
                BusinessFields.IMG + " VARCHAR(255), " +
                BusinessFields.DELIVERY + " VARCHAR(20), " +
                BusinessFields.MARK + " VARCHAR(10) " +
                ")");


    }



    private void insertBusiness(SQLiteDatabase db, String account, String pwd, String name, String describe, String img,String delivery,String mark,String phone) {
        ContentValues values = new ContentValues();
        values.put(BusinessFields.ACCOUNT, account);
        values.put(BusinessFields.PWD, pwd);
        values.put(BusinessFields.NAME, name);
        values.put(BusinessFields.DESCRIBE, describe);
        values.put(BusinessFields.PHONE, phone);
        values.put(BusinessFields.IMG, img);
        values.put(BusinessFields.DELIVERY,delivery);
        values.put(BusinessFields.MARK,mark);
        db.insert(TABLE_BUSINESS, null, values);
    }

    private void createDeliveryTable(SQLiteDatabase db) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DELIVERY);
        db.execSQL("CREATE TABLE " + TABLE_DELIVERY + " (" +
                DeliveryFields.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DeliveryFields.ACCOUNT + " VARCHAR(20) UNIQUE, " +
                DeliveryFields.PWD + " VARCHAR(20), " +
                DeliveryFields.NAME + " VARCHAR(20), " +
                DeliveryFields.DESCRIBE + " VARCHAR(200), " +
                DeliveryFields.MARK + " VARCHAR(10), " +
                DeliveryFields.PHONE + " VARCHAR(50) " +
                ")");


    }



    private void insertDelivery(SQLiteDatabase db, String account, String pwd, String name, String describe,String mark,String phone) {
        ContentValues values = new ContentValues();
        values.put(DeliveryFields.ACCOUNT, account);
        values.put(DeliveryFields.PWD, pwd);
        values.put(DeliveryFields.NAME, name);
        values.put(DeliveryFields.DESCRIBE, describe);
        values.put(DeliveryFields.MARK,mark);
        values.put(DeliveryFields.PHONE,phone);
        db.insert(TABLE_DELIVERY, null, values);
    }


    private void createFoodTable(SQLiteDatabase db) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD);
        db.execSQL("CREATE TABLE " + TABLE_FOOD + " (" +
                FoodFields.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FoodFields.BUSINESS_ID + " VARCHAR(20), " +
                FoodFields.NAME + " VARCHAR(20), " +
                FoodFields.DESCRIBE + " VARCHAR(200), " +
                FoodFields.NUMBER + " VARCHAR(20), " +
                FoodFields.PRICE + " VARCHAR(20), " +
                FoodFields.IMG + " VARCHAR(255), " +
                FoodFields.MARK + " VARCHAR(20) " +
                ")");


    }
    private void insertFood(SQLiteDatabase db, String id, String businessId, String name, String describe, String number, String price, String img,String mark) {
        ContentValues values = new ContentValues();
        values.put(FoodFields.ID, id);
        values.put(FoodFields.BUSINESS_ID, businessId);
        values.put(FoodFields.NAME, name);
        values.put(FoodFields.DESCRIBE, describe);
        values.put(FoodFields.NUMBER, number);
        values.put(FoodFields.PRICE, price);
        values.put(FoodFields.IMG, img);
        values.put(FoodFields.MARK,mark);
        db.insert(TABLE_FOOD, null, values);
    }

    private void createOrdersTable(SQLiteDatabase db) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        db.execSQL("CREATE TABLE " + TABLE_ORDERS + " (" +
                OrdersFields.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                OrdersFields.USER_ID + " VARCHAR(20), " +
                OrdersFields.BUSINESS_ID + " VARCHAR(20), " +
                OrdersFields.DATE + " VARCHAR(20), " +
                OrdersFields.ADDRESS + " VARCHAR(20), " +
                OrdersFields.STATE + " VARCHAR(20), " +
                OrdersFields.MARK + " VARCHAR(10), " +
                OrdersFields.DELIVERY + " VARCHAR(10), " +
                OrdersFields.NAME + " VARCHAR(20), " +
                OrdersFields.PHONE + " VARCHAR(20), " +
                OrdersFields.REMARK + " VARCHAR(100), " +
                OrdersFields.EVALUATE + " VARCHAR(200), " +
                OrdersFields.DELIVERYMARK + " VARCHAR(20)" +
                ")");
        //state:1、未处理订单，2、退款订单|取消订单，3、完成订单
    }
    private void insertOrder(SQLiteDatabase db, String id, String userId, String businessId, String date, String address, String state,String mark,String delivery,String name,String phone,String remark,String evaluate,String deliveryMark) {
        ContentValues values = new ContentValues();
        values.put(OrdersFields.ID, id);
        values.put(OrdersFields.USER_ID, userId);
        values.put(OrdersFields.BUSINESS_ID, businessId);
        values.put(OrdersFields.DATE, date);
        values.put(OrdersFields.ADDRESS, address);
        values.put(OrdersFields.STATE,state);
        values.put(OrdersFields.MARK,mark);
        values.put(OrdersFields.DELIVERY,delivery);
        values.put(OrdersFields.NAME,name);
        values.put(OrdersFields.PHONE,phone);
        values.put(OrdersFields.REMARK,remark);
        values.put(OrdersFields.EVALUATE,evaluate);
        values.put(OrdersFields.DELIVERYMARK,deliveryMark);

        db.insert(TABLE_ORDERS, null, values);
    }

    private void createOrderItemsTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERFOOD);
        db.execSQL("CREATE TABLE " + TABLE_ORDERFOOD + " (" +
                OrderItemsFields.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                OrderItemsFields.ORDER_ID + " VARCHAR(20), " +
                OrderItemsFields.FOOD_ID + " VARCHAR(20), " +
                OrderItemsFields.QUANTITY + " VARCHAR(20), " +
                OrderItemsFields.PRICE + " VARCHAR(20), " +
                OrderItemsFields.MARK + " VARCHAR(10)" +
                ")");
        // 插入示例数据
    }

    private void insertOrderItem(SQLiteDatabase db, String id, String orderId, String foodId, String quantity,String price,String mark) {
        ContentValues values = new ContentValues();
        values.put(OrderItemsFields.ID, id);
        values.put(OrderItemsFields.ORDER_ID, orderId);
        values.put(OrderItemsFields.FOOD_ID, foodId);
        values.put(OrderItemsFields.QUANTITY, quantity);
        values.put(OrderItemsFields.PRICE, price);
        values.put(OrderItemsFields.MARK,mark);
        db.insert(TABLE_ORDERFOOD, null, values);
    }

    private void createAddressTable(SQLiteDatabase db) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS);
        db.execSQL("CREATE TABLE " + TABLE_ADDRESS + " (" +
                AddressFields.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                AddressFields.USER_ID + " VARCHAR(20), " +
                AddressFields.NAME + " VARCHAR(20), " +
                AddressFields.ADDRESS + " VARCHAR(200), " +
                AddressFields.PHONE + " VARCHAR(20), " +
                "is_default BOOLEAN DEFAULT 0" + // 添加默认地址字段" +
                ")");

    }
    private void insertAddress(SQLiteDatabase db, String id, String userId, String name,String address, String phone,String isDefault) {
        ContentValues values = new ContentValues();
        values.put(AddressFields.ID, id);
        values.put(AddressFields.USER_ID, userId);
        values.put(AddressFields.NAME, name);
        values.put(AddressFields.ADDRESS, address);
        values.put(AddressFields.PHONE,phone);
        values.put("is_default", isDefault);
        db.insert(TABLE_ADDRESS, null, values);
    }

    private void createMessagesTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        db.execSQL("CREATE TABLE " + TABLE_MESSAGES + " (" +
                MessagesFields.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MessagesFields.BUSINESS_ID + " VARCHAR(20), " +
                MessagesFields.USER_ID + " VARCHAR(20), " +
                MessagesFields.CONTENT + " TEXT, " +
                MessagesFields.SENDER + " VARCHAR(20), " +
                MessagesFields.DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ")");
    }

    private void insertMessage(SQLiteDatabase db, String businessId, String userId, String content,String sender) {
        ContentValues values = new ContentValues();
        values.put(MessagesFields.BUSINESS_ID, businessId);
        values.put(MessagesFields.USER_ID, userId);
        values.put(MessagesFields.CONTENT, content);
        values.put(MessagesFields.SENDER, sender);
        db.insert(TABLE_MESSAGES, null, values);
    }


}
