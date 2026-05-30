package com.example.orderfood.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.orderfood.R;
import com.example.orderfood.entity.Order;

import java.util.List;

public class OrderListAdapter extends ArrayAdapter<Order> {
    private List<Order> list;
    private Context context;
    public OrderListAdapter(@NonNull Context context, List<Order> list) {
        super(context, R.layout.adapter_orderlist);
        this.context=context;
        this.list=list;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(getContext());
        view=inflater.inflate(R.layout.adapter_orderlist,parent,false);


        return view;
    }
}
