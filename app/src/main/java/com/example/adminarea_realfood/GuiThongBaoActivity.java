package com.example.adminarea_realfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.developer.kalert.KAlertDialog;
import com.example.adminarea_realfood.Model.CuaHang;
import com.example.adminarea_realfood.Model.KhachHang;
import com.example.adminarea_realfood.Model.Shipper;
import com.example.adminarea_realfood.TrangThai.LoaiThongBao;
import com.example.adminarea_realfood.databinding.ActivityGuiThongBaoBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.tapadoo.alerter.Alerter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GuiThongBaoActivity extends AppCompatActivity {
    ActivityGuiThongBaoBinding binding;
    CuaHang cuaHang ;
    Shipper shipper ;
    String noidung;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGuiThongBaoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (getIntent() != null && getIntent().getExtras() != null) {
            Intent intent = getIntent();
            String data = intent.getStringExtra("cuaHang");
            noidung = intent.getStringExtra("noiDung");

            Gson gson = new Gson();
            Object o = gson.fromJson(data, CuaHang.class);
            if (o instanceof CuaHang)
            {
                cuaHang = (CuaHang) o;
                LoadData();
            }
            if (o instanceof Shipper)
            {
                shipper = (Shipper) o;
                LoadData();
            }

        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void LoadData() {
        firebase_manager.storageRef.child("CuaHang").child(cuaHang.getIDCuaHang()).child("Avatar").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(GuiThongBaoActivity.this)
                        .load(uri.toString())
                        .into(binding.ivCuaHang);
            }
        });
        binding.edtNoiDung.setText(noidung);
        binding.tvTenCuaHang.setText(cuaHang.getTenCuaHang());
        binding.tvDiaChi.setText(cuaHang.getDiaChi());
        binding.tvSoDienThoai.setText(cuaHang.getSoDienThoai());
        binding.btnLuuThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebase_manager.Ghi_ThongBao_random(cuaHang.getIDCuaHang(),"Thông báo",binding.edtNoiDung.getText().toString(), LoaiThongBao.NORMAL).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        new KAlertDialog(GuiThongBaoActivity.this,KAlertDialog.SUCCESS_TYPE).setContentText("Đã gửi thông báo đến cửa hàng "+ cuaHang.getTenCuaHang()).setConfirmText("Ok").setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog kAlertDialog) {
                                Date date = new Date();
                                SimpleDateFormat formatter = new SimpleDateFormat("hh:mm dd/MM/yyyy");
                                if (cuaHang.getGhiChu()!=null)
                                {
                                    cuaHang.setGhiChu(cuaHang.getGhiChu()+"\nĐã gửi thông báo yêu cầu đóng phí " +formatter.format(date));
                                }
                                else {
                                    cuaHang.setGhiChu("Đã gửi thông báo yêu cầu đóng phí" +formatter.format(date));
                                }
                                firebase_manager.mDatabase.child("CuaHang").child(cuaHang.getIDCuaHang()).setValue(cuaHang);
                                finish();
                            }
                        }).show();
                    }
                });
            }
        });
    }
    private void LoadDataShipper() {
        firebase_manager.storageRef.child("CuaHang").child(cuaHang.getIDCuaHang()).child("Avatar").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(GuiThongBaoActivity.this)
                        .load(uri.toString())
                        .into(binding.ivCuaHang);
            }
        });
        binding.edtNoiDung.setText(noidung);
        binding.tvTenCuaHang.setText(cuaHang.getTenCuaHang());
        binding.tvDiaChi.setText(cuaHang.getDiaChi());
        binding.tvSoDienThoai.setText(cuaHang.getSoDienThoai());
        binding.btnLuuThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebase_manager.Ghi_ThongBao_random(cuaHang.getIDCuaHang(),"Thông báo",noidung, LoaiThongBao.NORMAL).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        new KAlertDialog(GuiThongBaoActivity.this,KAlertDialog.SUCCESS_TYPE).setContentText("Đã gửi thông báo đến cửa hàng "+ cuaHang.getTenCuaHang()).setConfirmText("Ok").setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog kAlertDialog) {
                                finish();
                            }
                        }).show();
                    }
                });
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}