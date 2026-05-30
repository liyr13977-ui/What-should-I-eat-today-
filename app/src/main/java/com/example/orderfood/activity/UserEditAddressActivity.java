package com.example.orderfood.activity;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.orderfood.R;
import com.example.orderfood.adapter.UserEditAddressAdapter;
import com.example.orderfood.dao.AddressDAO;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Address;
import com.example.orderfood.until.UserUntil;

import java.util.List;

public class UserEditAddressActivity extends BaseActivity implements UserEditAddressAdapter.OnEditAddressListener {
    private ListView addressList;
    private Button btnNewAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_user_edit_address;
    }

    @Override
    protected void initView() {
        addressList=findViewById(R.id.lv_user_editAddress_addressList);
        btnNewAddress=findViewById(R.id.btn_user_editAddress_newAddress);
    }

    @Override
    protected void initData() {
        loadData();
        btnNewAddress.setOnClickListener(v->{
            newAddress();
        });
        addressList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Address address = (Address) adapterView.getItemAtPosition(i);
                new AlertDialog.Builder(UserEditAddressActivity.this)
                        .setItems(new CharSequence[]{"删除"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    AddressDAO.deleteAddress(String.valueOf(address.getId()));
                                    loadData();
                                }

                            }
                        })
                        .show();


                return true; // 表示事件已处理
            }
        });
        btnNewAddress.setOnClickListener(v -> newAddress());

        // 设置ListView的点击事件
        addressList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Address selectedAddress = (Address) adapterView.getItemAtPosition(position);
                String selectedAddressId = String.valueOf(selectedAddress.getId());

                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedAddressId", selectedAddressId);
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    private void loadData(){
        List<Address> List= UserDAO.getAddressById(String.valueOf(UserUntil.getUserId(this)));
        UserEditAddressAdapter adapter=new UserEditAddressAdapter(this,List);
        addressList.setAdapter(adapter);
        adapter.setEditAddressListener(this);
    }


    @Override
    public void onEditAddress() {
        loadData();
    }
    private void newAddress(){
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_edit_address, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        EditText etReceiver = dialogView.findViewById(R.id.et_dialog_editAddress_receiver);
        EditText etPhone = dialogView.findViewById(R.id.et_dialog_editAddress_phone);
        EditText etRegion = dialogView.findViewById(R.id.et_dialog_editAddress_region);

        builder.setPositiveButton("保存", (dialog, which) -> {
            String receiver2 = etReceiver.getText().toString();
            String phone2 = etPhone.getText().toString();
            String region2 = etRegion.getText().toString();

            if (receiver2.isEmpty() || phone2.isEmpty() || region2.isEmpty()) {
                Toast.makeText(this, "请填写完整的地址信息", Toast.LENGTH_SHORT).show();
                return;
            }
            String userId=String.valueOf(UserUntil.getUserId(this));

            UserDAO.newUserAddress(userId,receiver2,region2,phone2);
            loadData();

            dialog.dismiss();
        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();

        dialog.show();
    }
}