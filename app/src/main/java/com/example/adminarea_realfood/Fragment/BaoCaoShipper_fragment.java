package com.example.adminarea_realfood.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.adminarea_realfood.R;


public class BaoCaoShipper_fragment extends Fragment {

    public BaoCaoShipper_fragment() {
        // Required empty public constructor
    }


    public static BaoCaoShipper_fragment newInstance(String param1, String param2) {
        BaoCaoShipper_fragment fragment = new BaoCaoShipper_fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.baocaoshipper_fragment, container, false);
    }
}