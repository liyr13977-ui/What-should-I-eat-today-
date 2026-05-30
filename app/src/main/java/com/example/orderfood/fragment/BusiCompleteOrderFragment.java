package com.example.orderfood.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.orderfood.R;


public class BusiCompleteOrderFragment extends Fragment {
    private View view;

    public BusiCompleteOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_complete_order, container, false);

        initView();
        initData();

        return view;
    }
    public void initView(){

    }
    public void initData(){

    }
}