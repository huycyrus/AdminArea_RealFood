package com.example.adminarea_realfood.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.adminarea_realfood.Screen.DoanhThuCuaHangActivity;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.Screen.DanhSachKhachHang;
import com.example.adminarea_realfood.Screen.DanhSachLoaiSanPham;
import com.example.adminarea_realfood.Screen.DanhSachShipper;
import com.example.adminarea_realfood.Screen.DanhSachShop;
import com.example.adminarea_realfood.Screen.DanhSachThongKeShipper;

public class TrangChu_fragment extends Fragment {

    LinearLayout lnDanhsachcuahang, lnShipper, lnDanhsachkhanhhang, lnDoanhthuhethong, lnDoanhthucuahang, lnThongke, lnLoaisanpham;

    public TrangChu_fragment() {
        // Required empty public constructor
    }


    public static TrangChu_fragment newInstance(String param1, String param2) {
        TrangChu_fragment fragment = new TrangChu_fragment();
        Bundle args = new Bundle();
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
        View view =  inflater.inflate(R.layout.home_fragment, container, false);
        lnDanhsachcuahang = view.findViewById(R.id.ln_danhsachcuahang);
        lnShipper = view.findViewById(R.id.ln_danhsachshipper);
        lnDanhsachkhanhhang = view.findViewById(R.id.ln_danhsachkhachhang);
        lnDoanhthuhethong = view.findViewById(R.id.ln_dthethong);
        lnDoanhthucuahang = view.findViewById(R.id.ln_dtcuahang);
        lnThongke = view.findViewById(R.id.lnThongke);
        lnLoaisanpham = view.findViewById(R.id.ln_loaisanpham);

        lnShipper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DanhSachShipper.class);
                startActivity(intent);
            }
        });
        lnDoanhthucuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DoanhThuCuaHangActivity.class);
                startActivity(intent);
            }
        });

        lnLoaisanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DanhSachLoaiSanPham.class);
                startActivity(intent);
            }
        });

        lnDanhsachcuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DanhSachShop.class);
                startActivity(intent);
            }
        });

        lnDanhsachkhanhhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DanhSachKhachHang.class);
                startActivity(intent);
            }
        });

        lnThongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DanhSachThongKeShipper.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
