package com.example.adminarea_realfood.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.CuaHang;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.adapter.ShopAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DSDaKichHoat_fragment extends Fragment {

    Firebase_Manager firebase_manager = new Firebase_Manager();
    ArrayList<CuaHang> cuaHangs = new ArrayList<CuaHang>();
    ShopAdapter shopAdapter;

    public DSDaKichHoat_fragment(CuaHang cuaHang) {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dsdakichhoat_fragment, container, false);

        ListView lvDanhSach = (ListView) view.findViewById(R.id.lv_danhsachshop);

        shopAdapter = new ShopAdapter(getContext(), R.layout.shop_dakichhoat_listview, cuaHangs);
        lvDanhSach.setAdapter(shopAdapter);
        getDanhsachshop();


        // Inflate the layout for this fragment
        return view;
    }

    public void getDanhsachshop() {
        firebase_manager.mDatabase.child("CuaHang").orderByChild("trangThaiCuaHang").equalTo("DaKichHoat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cuaHangs.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    CuaHang shop = postSnapshot.getValue(CuaHang.class);
                    cuaHangs.add(shop);
                    shopAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}

