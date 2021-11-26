package com.example.adminarea_realfood.Screen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.developer.kalert.KAlertDialog;
import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.BaoCaoShipper;
import com.example.adminarea_realfood.Model.CuaHang;
import com.example.adminarea_realfood.Model.Shipper;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.TrangThai.TrangThaiBaoCao;
import com.example.adminarea_realfood.TrangThai.TrangThaiShipper;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class XuLyShipper extends AppCompatActivity {

    CircleImageView civAnhCuaHang, civAnhShipper;
    TextView tvIdCuaHang, tvTenCuaHang, tvSdtCuaHang, tvIdShipper, tvTenShipper, tvSdtShipper;
    EditText edtNoiDung;
    Button btnKhoa, btnBoQua;
    BaoCaoShipper baoCaoShipper;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    Firebase_Manager firebase_manager = new Firebase_Manager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xulyshipper_activity);
        setControl();
        if (getIntent() != null && getIntent().getExtras() != null) {
            Intent intent = getIntent();
            String dataShop = intent.getStringExtra("BaoCaoShipper");
            Gson gson = new Gson();
            baoCaoShipper = gson.fromJson(dataShop, BaoCaoShipper.class);
            load();
        }

        setEvent();
    }

    private void load() {
        storageReference.child("CuaHang").child(baoCaoShipper.getIdCuaHang()).child("Avatar").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplication())
                        .load(uri.toString())
                        .into(civAnhCuaHang);
            }
        });

        firebase_manager.mDatabase.child("CuaHang").child(baoCaoShipper.getIdCuaHang()).addValueEventListener(new ValueEventListener() {
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

        storageReference.child("Shipper").child(baoCaoShipper.getIdShipper()).child("avatar").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplication())
                        .load(uri.toString())
                        .into(civAnhShipper);
            }
        });

        firebase_manager.mDatabase.child("Shipper").child(baoCaoShipper.getIdShipper()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Shipper shipper = snapshot.getValue(Shipper.class);
                tvIdShipper.setText(shipper.getiDShipper());
                tvTenShipper.setText(shipper.getHoVaTen());
                tvSdtShipper.setText(shipper.getSoDienThoai());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        edtNoiDung.setText(baoCaoShipper.getNoiDung());
    }

    private void setEvent() {
        btnKhoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KAlertDialog kAlertDialog = new KAlertDialog(XuLyShipper.this, KAlertDialog.PROGRESS_TYPE).setContentText("Loading");
                kAlertDialog.show();
                firebase_manager.mDatabase.child("Shipper").child(baoCaoShipper.getIdShipper()).child("trangThaiShipper").setValue(TrangThaiShipper.BiKhoa).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        kAlertDialog.changeAlertType(KAlertDialog.WARNING_TYPE);
                        kAlertDialog.setContentText("Shipper bị khóa");
                        baoCaoShipper.setTrangThaiBaoCao(TrangThaiBaoCao.DaXuLy);
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
        civAnhShipper = findViewById(R.id.civ_avatarshipper_xulyshipper);
        civAnhCuaHang = findViewById(R.id.civ_avatarcuahang_xulyshipper);
        tvIdShipper = findViewById(R.id.tv_idshipper_xulyshipper);
        tvTenShipper = findViewById(R.id.tv_tenshipper_xulyshipper);
        tvSdtShipper = findViewById(R.id.tv_sdtshipper_xulyshipper);
        tvIdCuaHang = findViewById(R.id.tv_idcuahang_xulyshipper);
        tvTenCuaHang = findViewById(R.id.tv_tencuahang_xulyshipper);
        tvSdtCuaHang= findViewById(R.id.tv_sdtcuahang_xulyshipper);
        edtNoiDung = findViewById(R.id.edt_noidungtocao_xulyshipper);
        btnKhoa = findViewById(R.id.btn_khoataikhoan_xulyshipper);
        btnBoQua = findViewById(R.id.btn_boqua_xulyshipper);

    }

}