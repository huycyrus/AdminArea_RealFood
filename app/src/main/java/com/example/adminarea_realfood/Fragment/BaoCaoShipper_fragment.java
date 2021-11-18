package com.example.adminarea_realfood.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.BaoCaoShipper;
import com.example.adminarea_realfood.Model.Shipper;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.adapter.BaoCaoShipperAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class BaoCaoShipper_fragment extends Fragment {

    ArrayList<BaoCaoShipper> baoCaoShippers = new ArrayList<>();
    Firebase_Manager firebase_manager = new Firebase_Manager();
    LinearLayoutManager linearLayoutManager;
    BaoCaoShipperAdapter baoCaoShipperAdapter;
    BaoCaoShipper baoCaoShipper;
    TextView tvAlert;
    LinearLayout lnLayout;
    ProgressBar pdLoad;
    RecyclerView rvBaoCaoShipper;
    Shipper shipper;

    public BaoCaoShipper_fragment(Shipper shipper) {
        // Required empty public constructor
        this.shipper = shipper;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.baocaoshipper_fragment, container, false);

        tvAlert = view.findViewById(R.id.tv_Alert_baocaoshipper);
        lnLayout = view.findViewById(R.id.lnBaoCaoShipper);
        pdLoad = view.findViewById(R.id.pd_Load_baocaoshipper);
        rvBaoCaoShipper = view.findViewById(R.id.rvBaoCaoShipper);

        baoCaoShipperAdapter = new BaoCaoShipperAdapter(getActivity(), R.layout.thongbao_item, baoCaoShippers);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvBaoCaoShipper.setLayoutManager(linearLayoutManager);
        rvBaoCaoShipper.setAdapter(baoCaoShipperAdapter);
        LoadData();

        return view;
    }

    private void LoadAlert() {
        if (!baoCaoShippers.isEmpty()) {
            tvAlert.setVisibility(View.GONE);
            lnLayout.setVisibility(View.VISIBLE);
        }
    }

    private void LoadData() {

        firebase_manager.mDatabase.child("BaoCao_CuaHang_Shipper").orderByChild("idShipper").equalTo(shipper.getiDShipper()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    baoCaoShippers.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        BaoCaoShipper baoCaoShipper = postSnapshot.getValue(BaoCaoShipper.class);
                        baoCaoShippers.add(baoCaoShipper);
                        baoCaoShipperAdapter.notifyDataSetChanged();
                    }
                    LoadAlert();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

}