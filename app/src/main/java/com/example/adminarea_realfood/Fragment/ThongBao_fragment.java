package com.example.adminarea_realfood.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.BaoCao;
import com.example.adminarea_realfood.Model.ThongBao;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.TrangThai.TrangThaiThongBao;
import com.example.adminarea_realfood.adapter.ThongBaoAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ThongBao_fragment extends Fragment {

    ArrayList<ThongBao> thongBaos = new ArrayList<>();
    Firebase_Manager firebase_manager = new Firebase_Manager();
    LinearLayoutManager linearLayoutManager;
    ThongBaoAdapter thongBaoAdapter;
    TextView tvAlert;
    LinearLayout lnLayout;
    Button btnDaDanhDau;
    ProgressBar pdLoad;
    RecyclerView rvThongBao;
    BaoCao baoCao = new BaoCao();
    ArrayList<BaoCao> baoCaos = new ArrayList<>();

    public ThongBao_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.thongbao_fragment, container, false);
        tvAlert = view.findViewById(R.id.tv_Alert);
        lnLayout = view.findViewById(R.id.lnLayout);
        btnDaDanhDau = view.findViewById(R.id.btn_DanhDauLaDaDoc);
        pdLoad = view.findViewById(R.id.pd_Load);
        rvThongBao = view.findViewById(R.id.rv_ThongBao);

        thongBaoAdapter = new ThongBaoAdapter(getActivity(), R.layout.thongbao_item, thongBaos);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        LoadData();

        return view;
    }

    private void LoadAlert() {
        if (thongBaos.isEmpty()) {
            tvAlert.setVisibility(View.VISIBLE);
            lnLayout.setVisibility(View.GONE);
        }
    }

    private void LoadData() {
        btnDaDanhDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pdLoad.setVisibility(View.VISIBLE);
                thongBaos.forEach(thongBao -> {
                    if (thongBao.getTrangThaiThongBao() == TrangThaiThongBao.ChuaXem) {
                        thongBao.setTrangThaiThongBao(TrangThaiThongBao.DaXem);
                        firebase_manager.Ghi_ThongBao(thongBao);
                        pdLoad.setVisibility(View.GONE);
                    }
                    thongBaoAdapter.notifyDataSetChanged();
                });
                pdLoad.setVisibility(View.GONE);
            }
        });
        firebase_manager.mDatabase.child("ThongBao").child(firebase_manager.auth.getUid()).orderByChild("trangThaiThongBao").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    thongBaos.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        ThongBao thongBao = postSnapshot.getValue(ThongBao.class);
                        thongBaos.add(thongBao);
                        thongBaoAdapter.notifyDataSetChanged();
                    }
                    rvThongBao.setLayoutManager(linearLayoutManager);
                    rvThongBao.setAdapter(thongBaoAdapter);
                    pdLoad.setVisibility(View.GONE);
                    LoadAlert();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        firebase_manager.mDatabase.child("BaoCao").child(baoCao.getIDCuaHang()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    BaoCao baoCao1 = postSnapshot.getValue(BaoCao.class);
                    baoCaos.add(baoCao1);
                    thongBaoAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
}
