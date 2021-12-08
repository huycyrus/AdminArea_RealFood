package com.example.adminarea_realfood.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.Admin;
import com.example.adminarea_realfood.Screen.DanhSachHoaDonActivity;
import com.example.adminarea_realfood.Screen.DoanhThuCuaHangActivity;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.Screen.DanhSachKhachHang;
import com.example.adminarea_realfood.Screen.DanhSachLoaiSanPham;
import com.example.adminarea_realfood.Screen.DanhSachShipper;
import com.example.adminarea_realfood.Screen.DanhSachShop;
import com.example.adminarea_realfood.Screen.DanhSachThongKeShipper;
import com.example.adminarea_realfood.Screen.DoanhThuHeThong;
import com.example.adminarea_realfood.Screen.SuKienActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class TrangChu_fragment extends Fragment {
    CircleImageView circleImageView;
    LinearLayout lnDanhsachcuahang,lnSuKien, lnShipper,lnThanhKhoan, lnDanhsachkhanhhang, lnDoanhthuhethong, lnDoanhthucuahang, lnThongke, lnLoaisanpham;
    TextView txtTenadmin,txtSoCuaHang,txtDonHang,txtShipper,txtKhachHnag;
    public TrangChu_fragment() {
        // Required empty public constructor
    }
    Firebase_Manager firebase_manager = new Firebase_Manager();

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
        lnThanhKhoan = view.findViewById(R.id.lnThanhKhoan);
        lnSuKien = view.findViewById(R.id.lnSuKien);
        txtDonHang = view.findViewById(R.id.txtDonHang);
        txtTenadmin = view.findViewById(R.id.txtTenadmin);
        txtSoCuaHang = view.findViewById(R.id.txtSoCuaHang);
        txtDonHang = view.findViewById(R.id.txtDonHang);
        txtShipper = view.findViewById(R.id.txtShipper);
        txtKhachHnag = view.findViewById(R.id.txtKhachHnag);
        circleImageView = view.findViewById(R.id.profile_image_admin);
        firebase_manager.storageRef.child("Admin").child("AvatarAdmin").getDownloadUrl(  ).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    Glide.with(getContext())
                            .load(uri.toString())
                            .into(circleImageView);
                }catch (Exception e)
                {

                }

            }
        });
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
        lnDoanhthuhethong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DoanhThuHeThong.class);
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
        lnThongke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DanhSachThongKeShipper.class);
                startActivity(intent);
            }
        });
        lnThanhKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DanhSachHoaDonActivity.class);
                startActivity(intent);
            }
        });
        lnSuKien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SuKienActivity.class);
                startActivity(intent);
            }
        });
        firebase_manager.mDatabase.child("Admin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
               Admin admin = snapshot.getValue(Admin.class);
                txtTenadmin.setText("Admin "+ admin.getHoTen());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        firebase_manager.mDatabase.child("Shipper").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                txtShipper.setText(snapshot.getChildrenCount()+"");
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        firebase_manager.mDatabase.child("DonHang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                txtDonHang.setText(snapshot.getChildrenCount()+"");
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        firebase_manager.mDatabase.child("CuaHang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                txtSoCuaHang.setText(snapshot.getChildrenCount()+"");
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        firebase_manager.mDatabase.child("KhachHang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                txtKhachHnag.setText(snapshot.getChildrenCount()+"");
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        return view;
    }
}
