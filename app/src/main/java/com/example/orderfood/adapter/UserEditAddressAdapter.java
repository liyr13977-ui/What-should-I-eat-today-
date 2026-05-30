package com.example.orderfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.orderfood.R;
import com.example.orderfood.activity.UserEditAddressActivity;
import com.example.orderfood.dao.AddressDAO;
import com.example.orderfood.dao.UserDAO;
import com.example.orderfood.entity.Address;
import com.example.orderfood.until.UserUntil;

import java.util.List;
import java.util.Objects;

public class UserEditAddressAdapter extends ArrayAdapter<Address> {
    private List<Address> addressList;
    private OnEditAddressListener onEditAddressListener;


    public UserEditAddressAdapter(@NonNull Context context, List<Address> addressList) {
        super(context, R.layout.adapter_user_edit_address,addressList);
        this.addressList=addressList;
    }

    public interface OnEditAddressListener{
        void onEditAddress();
    }
    public void setEditAddressListener(OnEditAddressListener onEditAddressListener){
        this.onEditAddressListener=onEditAddressListener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.adapter_user_edit_address,parent,false);
        }
        Address addressEntity=addressList.get(position);

        TextView name=convertView.findViewById(R.id.tv_user_editAddress_name);
        TextView phone=convertView.findViewById(R.id.tv_user_editAddress_phone);
        TextView address=convertView.findViewById(R.id.tv_user_editAddress_address);
        Button edit=convertView.findViewById(R.id.btn_user_editAddress_edit);
        CheckBox checkBox=convertView.findViewById(R.id.cb_user_editAddress_defaultAddress);

        // 设置为未选中状态
        checkBox.setChecked("1".equals(addressEntity.getIs_default())); // 设置为选中状态

        name.setText(addressEntity.getName());
        phone.setText(addressEntity.getPhone());
        address.setText(addressEntity.getAddress());

        checkBox.setOnClickListener(v -> {
            // 获取当前地址项的 ID
            String currentAddressId = String.valueOf(addressEntity.getId());
            String userId = String.valueOf(UserUntil.getUserId(getContext())); // 假设当前用户的 ID 是 1

            // 调用 setDefaultAddress 方法，将当前地址设置为默认地址
            int result = AddressDAO.setDefaultAddress(userId, addressEntity.getId());
            if (result == 1) {
                Toast.makeText(getContext(), "默认地址设置成功", Toast.LENGTH_SHORT).show();
                if (onEditAddressListener != null) {
                    onEditAddressListener.onEditAddress();
                }
            } else {
                Toast.makeText(getContext(), "默认地址设置失败", Toast.LENGTH_SHORT).show();
                checkBox.setChecked(false); // 设置回未选中状态
            }
        });

        edit.setOnClickListener(v->{
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View dialogView = inflater.inflate(R.layout.dialog_edit_address, null);

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setView(dialogView);

            EditText etReceiver = dialogView.findViewById(R.id.et_dialog_editAddress_receiver);
            EditText etPhone = dialogView.findViewById(R.id.et_dialog_editAddress_phone);
            EditText etRegion = dialogView.findViewById(R.id.et_dialog_editAddress_region);

            etReceiver.setText(addressEntity.getName());
            etPhone.setText(addressEntity.getPhone());
            etRegion.setText(addressEntity.getAddress());

            builder.setPositiveButton("保存", (dialog, which) -> {
                String receiver2 = etReceiver.getText().toString();
                String phone2 = etPhone.getText().toString();
                String region2 = etRegion.getText().toString();

                if (receiver2.isEmpty() || phone2.isEmpty() || region2.isEmpty()) {
                    Toast.makeText(getContext(), "请填写完整的地址信息", Toast.LENGTH_SHORT).show();
                    return;
                }

                int responseCode= UserDAO.updateUserAddress(addressEntity.getId(),receiver2,region2,phone2);
                if(responseCode==1){
                    Toast.makeText(getContext(), "地址保存成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "地址保存失败", Toast.LENGTH_SHORT).show();
                }

                if(onEditAddressListener!=null){
                    onEditAddressListener.onEditAddress();
                }

                dialog.dismiss();
            });

            builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = builder.create();

            dialog.show();

        });

        return convertView;
    }

}
