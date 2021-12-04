package com.example.adminarea_realfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.bumptech.glide.Glide;
import com.developer.kalert.KAlertDialog;
import com.example.adminarea_realfood.Model.CuaHang;
import com.example.adminarea_realfood.Model.Shipper;
import com.example.adminarea_realfood.Model.ThanhToan;
import com.example.adminarea_realfood.databinding.ActivityThongTinThanhToanBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ThongTinThanhToanActivity extends AppCompatActivity {
    ActivityThongTinThanhToanBinding binding;
    ThanhToan thanhToan ;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    NumberFormat formatter = new DecimalFormat("#0.0");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThongTinThanhToanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        if (getIntent() != null && getIntent().getExtras() != null) {
            Intent intent = getIntent();
            String dataShipper = intent.getStringExtra("ThanhToan");
            Gson gson = new Gson();
            thanhToan = gson.fromJson(dataShipper, ThanhToan.class);
        }
        LoadData();
    }

    private void LoadData() {
        binding.tvSoTien.setText(thanhToan.getSoTien()+"VND");
        binding.tvTenNganHang.setText(thanhToan.getTenNganHang());
        binding.tvTenNguoiGui.setText(thanhToan.getTenNguoiGui());
        binding.tvSoTaiKhoan.setText(thanhToan.getSoTaiKhoan());
        binding.tvNoiDung.setText(thanhToan.getNoiDung());
        binding.tvIdBill.setText(thanhToan.getIdBill());
        firebase_manager.mDatabase.child("CuaHang").child(thanhToan.getIdCuaHang()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                CuaHang cuaHang =dataSnapshot.getValue(CuaHang.class);
                binding.tvTenCuaHang.setText(cuaHang.getTenCuaHang());
            }
        });
        firebase_manager.storageRef.child("ThanhToan").child(thanhToan.getIdBill()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    Glide.with(ThongTinThanhToanActivity.this)
                            .load(uri.toString())
                            .into(binding.ivImage);
                }
                catch (Exception e)
                {

                }
            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_thongtinhoadon, menu);



        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Context context = this;

        switch (item.getItemId()) {
            case R.id.mn_huyHoaDon:

                KAlertDialog kAlertDialog = new KAlertDialog(this,KAlertDialog.WARNING_TYPE)
                        .setContentText("Bạn có chắc chắn hủy hóa đơn này?").setCancelText("Không")
                        .setConfirmText("Có");
                kAlertDialog.show();
                kAlertDialog.setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog kAlertDialog) {
                        kAlertDialog.changeAlertType(KAlertDialog.PROGRESS_TYPE);
                        kAlertDialog.setContentText("Loading");
                        firebase_manager.mDatabase.child("ThanhToan").child(thanhToan.getIdBill()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                                kAlertDialog.setContentText("Đã xóa thành công");
                                kAlertDialog.setConfirmText("Oke");
                                kAlertDialog.showCancelButton(false);
                                kAlertDialog.setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                                    @Override
                                    public void onClick(KAlertDialog kAlertDialog) {
                                        finish();
                                    }
                                });
                            }
                        });
                    }
                });
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}