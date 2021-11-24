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
import com.example.adminarea_realfood.Model.BaoCaoShop;
import com.example.adminarea_realfood.Model.CuaHang;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.adapter.BaoCaoShopAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BaoCaoShop_fragment extends Fragment {

    ArrayList<BaoCaoShop> baoCaoShops = new ArrayList<>();
    Firebase_Manager firebase_manager = new Firebase_Manager();
    LinearLayoutManager linearLayoutManager;
    BaoCaoShopAdapter baoCaoShopAdapter;
    BaoCaoShop baoCaoShop;
    TextView tvAlert;
    LinearLayout lnLayout;
    ProgressBar pdLoad;
    RecyclerView rvBaoCaoShop;
    CuaHang cuaHang;

    public BaoCaoShop_fragment(CuaHang cuaHang) {
        // Required empty public constructor
        this.cuaHang = cuaHang;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.baocaoshop_fragment, container, false);

        tvAlert = view.findViewById(R.id.tv_Alert_baocaoshop);
        lnLayout = view.findViewById(R.id.lnBaoCaoShop);
        pdLoad = view.findViewById(R.id.pd_Load_baocaoshop);
        rvBaoCaoShop = view.findViewById(R.id.rv_baocaoshop);

        baoCaoShopAdapter = new BaoCaoShopAdapter(getActivity(), R.layout.baocaoshop_item, baoCaoShops);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvBaoCaoShop.setLayoutManager(linearLayoutManager);
        rvBaoCaoShop.setAdapter(baoCaoShopAdapter);
        LoadData();

        return view;
    }

    private void LoadAlert() {
        if (!baoCaoShops.isEmpty()) {
            tvAlert.setVisibility(View.GONE);
            lnLayout.setVisibility(View.VISIBLE);
        }
    }

    private void LoadData() {

        firebase_manager.mDatabase.child("BaoCao").orderByChild("idcuaHang").equalTo(cuaHang.getIDCuaHang()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    baoCaoShops.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        BaoCaoShop baoCaoShop = postSnapshot.getValue(BaoCaoShop.class);
                        baoCaoShops.add(baoCaoShop);
                        baoCaoShopAdapter.notifyDataSetChanged();
                    }
                    LoadAlert();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    public BaoCaoShop_fragment() {
    }

    public static BaoCaoShop_fragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("id", id);
        BaoCaoShop_fragment f = new BaoCaoShop_fragment();
        f.setArguments(args);
        return f;
    }
}