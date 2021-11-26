package com.example.adminarea_realfood.Screen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.developer.kalert.KAlertDialog;
import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.BaoCaoShop;
import com.example.adminarea_realfood.Model.CuaHang;
import com.example.adminarea_realfood.Model.KhachHang;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.TrangThai.TrangThaiBaoCao;
import com.example.adminarea_realfood.TrangThai.TrangThaiCuaHang;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class XuLyShop extends AppCompatActivity {

    ImageView ivAnhToCao;
    CircleImageView civAnhKhachHang, civAnhCuaHang;
    TextView tvIdKhachHang, tvTenKhachHang, tvSdtKhachHang, tvIdCuaHang, tvTenCuaHang, tvSdtCuaHang;
    EditText edtNoiDung;
    Button btnKhoa, btnBoQua;
    BaoCaoShop baoCaoShop;
    KhachHang khachHang;
    CuaHang cuaHang;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    Firebase_Manager firebase_manager = new Firebase_Manager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xulyshop_activity);
        setControl();
        if (getIntent() != null && getIntent().getExtras() != null) {
            Intent intent = getIntent();
            String dataShop = intent.getStringExtra("BaoCao");
            Gson gson = new Gson();
            baoCaoShop = gson.fromJson(dataShop, BaoCaoShop.class);
            load();
        }

        setEvent();
    }

    private void load() {
        storageReference.child("KhachHang").child(baoCaoShop.getIDKhachHang()).child("AvatarKhachHang").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplication())
                        .load(uri.toString())
                        .into(civAnhKhachHang);
            }
        });

        firebase_manager.mDatabase.child("KhachHang").child(baoCaoShop.getIDKhachHang()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                KhachHang khachHang = snapshot.getValue(KhachHang.class);
                tvIdKhachHang.setText(khachHang.getIDKhachHang());
                tvTenKhachHang.setText(khachHang.getTenKhachHang());
                tvSdtKhachHang.setText(khachHang.getSoDienThoai());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        storageReference.child("CuaHang").child(baoCaoShop.getIDCuaHang()).child("Avatar").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplication())
                        .load(uri.toString())
                        .into(civAnhCuaHang);
            }
        });

        firebase_manager.mDatabase.child("CuaHang").child(baoCaoShop.getIDCuaHang()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                CuaHang cuaHang = snapshot.getValue(CuaHang.class);
                tvIdCuaHang.setText(cuaHang.getIDCuaHang());
                tvTenCuaHang.setText(cuaHang.getTenCuaHang());
                tvSdtCuaHang.setText(cuaHang.getSoDienThoai());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        storageReference.child("BaoCao").child(baoCaoShop.getIDBaoCao()).child("ImageBaoCao").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplication())
                        .load(uri.toString())
                        .into(ivAnhToCao);
            }
        });

        edtNoiDung.setText(baoCaoShop.getLyDo());
    }

    private void setEvent() {
        btnKhoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KAlertDialog kAlertDialog = new KAlertDialog(XuLyShop.this, KAlertDialog.PROGRESS_TYPE).setContentText("Loading");
                kAlertDialog.show();
                firebase_manager.mDatabase.child("CuaHang").child(baoCaoShop.getIDCuaHang()).child("trangThaiCuaHang").setValue(TrangThaiCuaHang.BiKhoa).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        kAlertDialog.changeAlertType(KAlertDialog.WARNING_TYPE);
                        kAlertDialog.setContentText("Cửa hàng bị khóa");
                        baoCaoShop.setTrangThaiBaoCao(TrangThaiBaoCao.DaXuLy);
                        finish();
                    }
                });
            }
        });

        btnBoQua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setControl() {
        ivAnhToCao = findViewById(R.id.iv_anh_xulyshop);
        civAnhCuaHang = findViewById(R.id.civ_avatarcuahang_xulyshop);
        civAnhKhachHang = findViewById(R.id.civ_avatarkhachhang_xulyshop);
        tvIdCuaHang = findViewById(R.id.tv_idcuahang_xulyshop);
        tvTenCuaHang = findViewById(R.id.tv_tencuahang_xulyshop);
        tvSdtCuaHang = findViewById(R.id.tv_sdtcuahang_xulyshop);
        tvIdKhachHang = findViewById(R.id.tv_idkhachhang_xulyshop);
        tvTenKhachHang = findViewById(R.id.tv_tenkhachhang_xulyshop);
        tvSdtKhachHang = findViewById(R.id.tv_sdtkhachhang_xulyshop);
        edtNoiDung = findViewById(R.id.edt_noidungtocao_xulyshop);
        btnKhoa = findViewById(R.id.btn_khoataikhoan_xulyshop);
        btnBoQua = findViewById(R.id.btn_boqua_xulyshop);

    }

}