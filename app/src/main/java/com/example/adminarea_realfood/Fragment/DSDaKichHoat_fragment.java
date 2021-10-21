package com.example.adminarea_realfood.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.adminarea_realfood.Model.Shop;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.adapter.ShopAdapter;

import java.util.ArrayList;

public class DSDaKichHoat_fragment extends Fragment {



    public DSDaKichHoat_fragment(Shop shop) {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dsdakichhoat_fragment, container, false);

        ArrayList<Shop> shops = new ArrayList<Shop>();
        ListView lvDanhSach = (ListView) view.findViewById(R.id.lv_danhsachshop);
        ShopAdapter shopAdapter = new ShopAdapter(getContext(), R.layout.shop_listview,shops);
        lvDanhSach.setAdapter(shopAdapter);

        // Inflate the layout for this fragment
        return view;
    }
}