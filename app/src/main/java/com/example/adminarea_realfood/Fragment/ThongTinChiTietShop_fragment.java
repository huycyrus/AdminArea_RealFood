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
import com.developer.kalert.KAlertDialog;
import com.example.adminarea_realfood.Firebase_Manager;
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
    Button btnKhoa, btnGoKhoa, btnKichHoat;
    CuaHang cuaHang;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    Firebase_Manager firebase_manager = new Firebase_Manager();

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

        LoadButton();

        btnKichHoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KAlertDialog kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE).setContentText("Loading");
                kAlertDialog.show();
                cuaHang.setTrangThaiCuaHang(TrangThaiCuaHang.DaKichHoat);
                firebase_manager.mDatabase.child("CuaHang").child(cuaHang.getIDCuaHang()).setValue(cuaHang).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        LoadButton();
                        kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                        kAlertDialog.setContentText("Đã kích hoạt thành công");
                    }
                });
            }
        });

        btnKhoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KAlertDialog kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE).setContentText("Loading");
                kAlertDialog.show();
                cuaHang.setTrangThaiCuaHang(TrangThaiCuaHang.BiKhoa);
                firebase_manager.mDatabase.child("CuaHang").child(cuaHang.getIDCuaHang()).setValue(cuaHang).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        LoadButton();
                        kAlertDialog.changeAlertType(KAlertDialog.WARNING_TYPE);
                        kAlertDialog.setContentText("Cửa hàng bị khóa");
                    }
                });
            }
        });

        btnGoKhoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KAlertDialog kAlertDialog = new KAlertDialog(getContext(), KAlertDialog.PROGRESS_TYPE).setContentText("Loading");
                kAlertDialog.show();
                cuaHang.setTrangThaiCuaHang(TrangThaiCuaHang.DaKichHoat);
                firebase_manager.mDatabase.child("CuaHang").child(cuaHang.getIDCuaHang()).setValue(cuaHang).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        LoadButton();
                        kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                        kAlertDialog.setContentText("Cửa hàng được gỡ khóa");
                    }
                });
            }
        });

        return view;
    }

    private void LoadButton() {
        btnKhoa.setVisibility(View.GONE);
        btnKichHoat.setVisibility(View.GONE);
        btnGoKhoa.setVisibility(View.GONE);
        if(cuaHang.getTrangThaiCuaHang() == TrangThaiCuaHang.ChuaKichHoat){
            btnKichHoat.setVisibility(View.VISIBLE);
        }
        if(cuaHang.getTrangThaiCuaHang() == TrangThaiCuaHang.DaKichHoat){
            btnKhoa.setVisibility(View.VISIBLE);
        }
        if(cuaHang.getTrangThaiCuaHang() == TrangThaiCuaHang.BiKhoa){
            btnGoKhoa.setVisibility(View.VISIBLE);
        }
    }


}