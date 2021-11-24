package com.example.adminarea_realfood.Screen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.adminarea_realfood.Model.KhachHang;
import com.example.adminarea_realfood.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import de.hdodenhof.circleimageview.CircleImageView;

public class ThongTinChiTietKhachHang extends AppCompatActivity {

    KhachHang khachHang;
    CircleImageView civAvatarProKH;
    EditText edtTenKH, edtSdtKH, edtEmailKH, edtNgaySinh, edtDiaChiKH;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thongtinchitietkhachhang_activity);
        setControl();
        if (getIntent() != null && getIntent().getExtras() != null) {
            Intent intent = getIntent();
            String dataKhachHang = intent.getStringExtra("KhachHang");
            Gson gson = new Gson();
            khachHang = gson.fromJson(dataKhachHang, KhachHang.class);
        }
        setEvent();
    }

    private void setEvent() {
        edtTenKH.setText(khachHang.getTenKhachHang());
        edtSdtKH.setText(khachHang.getSoDienThoai());
        edtEmailKH.setText(khachHang.getEmail());
        edtNgaySinh.setText(khachHang.getNgaySinh());
        edtDiaChiKH.setText(khachHang.getDiaChi());
        storageReference.child("KhachHang").child(khachHang.getIDKhachHang()).child("AvatarKhachHang").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplication())
                        .load(uri.toString())
                        .into(civAvatarProKH);
            }
        });
    }

    private void setControl() {
        civAvatarProKH = findViewById(R.id.avatar_ttctkhachhang);
        edtTenKH = findViewById(R.id.edt_hovaten_ttctkhachhang);
        edtSdtKH = findViewById(R.id.edt_sdt_ttctkhachhang);
        edtEmailKH = findViewById(R.id.edt_email_ttctkhachhang);
        edtNgaySinh = findViewById(R.id.edt_ngaysinh_ttctkhachhang);
        edtDiaChiKH = findViewById(R.id.edt_diachi_ttctkhachhang);
    }
}