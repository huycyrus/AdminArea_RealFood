package com.example.adminarea_realfood.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.adminarea_realfood.Model.CuaHang;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.TrangThai.TrangThaiCuaHang;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class ThongTinChiTietShop_fragment extends Fragment {

    ImageView ivWallpaper, ivTruoc, ivSau;
    CircleImageView civAvatar;
    TextView tvTenCuaHang, tvTenQuanLi, tvSDT, tvDiaChi, tvEmail;
    Button btnKhoa, btnGoKhoa, btnKichHoat, btnChan;
    CuaHang cuaHang;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public ThongTinChiTietShop_fragment(CuaHang cuaHang) {
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
        View view =  inflater.inflate(R.layout.thongtinchitietshop_fragment, container, false);

        ivWallpaper = view.findViewById(R.id.iv_wallpaper);
        civAvatar = view.findViewById(R.id.civ_avatar_ttshop);
        ivTruoc = view.findViewById(R.id.ib_idtruoc_ttshop);
        ivSau = view.findViewById(R.id.ib_idsau_ttshop);
        tvTenCuaHang = view.findViewById(R.id.tv_tencuahang_ttshop);
        tvTenQuanLi = view.findViewById(R.id.tv_tenquanli_ttshop);
        tvSDT = view.findViewById(R.id.tv_sdt_ttshop);
        tvDiaChi = view.findViewById(R.id.tv_diachi_ttshop);
        tvEmail = view.findViewById(R.id.tv_email_ttshop);
        btnKhoa = view.findViewById(R.id.btn_khoa_ttshop);
        btnGoKhoa = view.findViewById(R.id.btn_gokhoa_ttshop);
        btnKichHoat = view.findViewById(R.id.btn_kichhoat_ttshop);
        btnChan = view.findViewById(R.id.btn_chan_ttshop);

        storageReference.child("CuaHang").child(cuaHang.getIDCuaHang()).child("WallPaper").getDownloadUrl(  ).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext())
                        .load(uri.toString())
                        .into(ivWallpaper);
            }
        });
        storageReference.child("CuaHang").child(cuaHang.getIDCuaHang()).child("Avatar").getDownloadUrl(  ).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext())
                        .load(uri.toString())
                        .into(civAvatar);
            }
        });
        tvTenCuaHang.setText(cuaHang.getTenCuaHang());
        tvTenQuanLi.setText(cuaHang.getChuSoHuu());
        tvSDT.setText(cuaHang.getSoDienThoai());
        tvDiaChi.setText(cuaHang.getDiaChi());
        tvEmail.setText(cuaHang.getEmail());
        storageReference.child("CuaHang").child(cuaHang.getIDCuaHang()).child("CMND_MatTruoc").getDownloadUrl(  ).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext())
                        .load(uri.toString())
                        .into(ivTruoc);
            }
        });
        storageReference.child("CuaHang").child(cuaHang.getIDCuaHang()).child("CMND_MatSau").getDownloadUrl(  ).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getContext())
                        .load(uri.toString())
                        .into(ivSau);
            }
        });

        if(cuaHang.getTrangThaiCuaHang() == TrangThaiCuaHang.ChuaKichHoat){
            btnKhoa.setVisibility(View.GONE);
            btnGoKhoa.setVisibility(View.GONE);
        }
        if(cuaHang.getTrangThaiCuaHang() == TrangThaiCuaHang.DaKichHoat){
            btnChan.setVisibility(View.GONE);
            btnGoKhoa.setVisibility(View.GONE);
            btnKichHoat.setVisibility(View.GONE);
        }

        btnKhoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnKhoa.setVisibility(View.GONE);
                btnGoKhoa.setVisibility(View.VISIBLE);
            }
        });

        btnGoKhoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnGoKhoa.setVisibility(View.GONE);
                btnKhoa.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }
}