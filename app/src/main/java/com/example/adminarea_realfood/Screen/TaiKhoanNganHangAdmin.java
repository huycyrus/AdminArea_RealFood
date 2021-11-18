package com.example.adminarea_realfood.Screen;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.developer.kalert.KAlertDialog;
import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.NganHang;
import com.example.adminarea_realfood.Model.NganHangAPI;
import com.example.adminarea_realfood.Model.NganHangWrapper;
import com.example.adminarea_realfood.Model.TaiKhoanNganHang;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.Validate;
import com.example.adminarea_realfood.adapter.NganHangAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import gr.escsoft.michaelprimez.searchablespinner.SearchableSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TaiKhoanNganHangAdmin extends AppCompatActivity {

    EditText edtTenChiNhanh, edtSoTaiKhoan, edtTenChuTaiKhoan, edtSoCMND;
    SearchableSpinner spDSNganHang;
    NganHangAdapter nganHangAdapter;
    Button btnLuuThongTin;
    Validate validate = new Validate();
    Firebase_Manager firebase_manager = new Firebase_Manager();
    ArrayList<NganHang> arrayList = new ArrayList<>();
    NganHangAPI nganHangAPI;
    TaiKhoanNganHang nganHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taikhoannnganhang_activity);
        getData();
        setControl();
        setEvent();
        LoadData();
    }

    private void LoadData() {
        firebase_manager.mDatabase.child("TaiKhoanNganHangAdmin").child("admin")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        TaiKhoanNganHang taiKhoanNganHang = snapshot.getValue(TaiKhoanNganHang.class);
                        nganHang = taiKhoanNganHang;
                        SetValueToAllField();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void SetValueToAllField() {
        if (nganHang != null){
            edtSoCMND.setText(nganHang.getSoCMND());
            edtTenChuTaiKhoan.setText(nganHang.getTenChuTaiKhoan());
            edtSoTaiKhoan.setText(nganHang.getSoTaiKhoan());
            edtTenChiNhanh.setText(nganHang.getTenChiNhanh());
            edtSoCMND.setText(nganHang.getSoCMND());
            AtomicInteger positon = new AtomicInteger();
            arrayList.forEach(temp -> {
                if (temp.getName().equals(nganHang.getTenNganHang())) {
                    spDSNganHang.setSelected(true);
                    spDSNganHang.setActivated(true);
                    spDSNganHang.dispatchSetSelected(true);
                    spDSNganHang.setSelectedItem(positon.get());
                }
                positon.getAndIncrement();
            });
        }
    }

    private void setEvent() {
        btnLuuThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate.isBlank(edtTenChiNhanh)
                        && spDSNganHang.getSelectedItem() != null
                        && !validate.isBlank(edtSoTaiKhoan) && !validate.isBlank(edtTenChuTaiKhoan)
                        && !validate.isBlank(edtSoCMND) && validate.isCMND(edtSoCMND)) {

                    if (nganHang == null) {
                        KAlertDialog kAlertDialog = new KAlertDialog(TaiKhoanNganHangAdmin.this, KAlertDialog.PROGRESS_TYPE)
                                .setContentText("Loading");
                        kAlertDialog.show();
                        String tenNganHang = nganHangAdapter.getItem(spDSNganHang.getSelectedPosition()).getName();
                        TaiKhoanNganHang temp =
                                new TaiKhoanNganHang("admin", tenNganHang, edtTenChiNhanh.getText().toString()
                                        , edtSoTaiKhoan.getText().toString(), edtTenChuTaiKhoan.getText().toString(), edtSoCMND.getText().toString(), "admin");
                        nganHang = temp;
                        firebase_manager.Ghi_NganHang(nganHang).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                kAlertDialog.setContentText("Đã lưu thông tin thành công!");
                                kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                                kAlertDialog.showConfirmButton(false);
                            }
                        });
                    } else {
                        KAlertDialog kAlertDialog = new KAlertDialog(TaiKhoanNganHangAdmin.this, KAlertDialog.PROGRESS_TYPE)
                                .setContentText("Loading");
                        kAlertDialog.show();
                        String uuid = nganHang.getId();
                        String tenNganHang = nganHangAdapter.getItem(spDSNganHang.getSelectedPosition()).getName();
                        TaiKhoanNganHang temp =
                                new TaiKhoanNganHang(uuid, tenNganHang, edtTenChiNhanh.getText().toString()
                                        , edtSoTaiKhoan.getText().toString(), edtTenChuTaiKhoan.getText().toString(), edtSoCMND.getText().toString(), "admin");
                        nganHang = temp;
                        firebase_manager.Ghi_NganHang(nganHang).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                kAlertDialog.setContentText("Đã lưu thông tin thành công!");
                                kAlertDialog.changeAlertType(KAlertDialog.SUCCESS_TYPE);
                                kAlertDialog.showConfirmButton(false);
                            }
                        });
                    }
                }
            }
        });
    }

    private void getData() {
        arrayList.clear();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(NganHangAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        nganHangAPI = retrofit.create(NganHangAPI.class);
        Call<NganHangWrapper<NganHang>> call = nganHangAPI.getNganHang();
        call.enqueue(new Callback<NganHangWrapper<NganHang>>() {
            @Override
            public void onResponse(Call<NganHangWrapper<NganHang>> call, Response<NganHangWrapper<NganHang>> response) {
                if (response.isSuccessful()) {
                    NganHangWrapper<NganHang> wrapper = response.body();
                    assert wrapper != null;
                    arrayList.addAll(wrapper.items);
                    arrayList.forEach(nganHang -> Log.d("NganHang", nganHang.getName()));
                    nganHangAdapter = new NganHangAdapter(TaiKhoanNganHangAdmin.this, R.layout.sp_nganhang_item, R.id.tv_tennganhang, arrayList);
                    spDSNganHang.setAdapter(nganHangAdapter);
                    if (nganHang != null) {
                        LoadData();
                    }
                }
            }

            @Override
            public void onFailure(Call<NganHangWrapper<NganHang>> call, Throwable t) {
            }
        });

    }

    private void setControl() {
        edtSoCMND = findViewById(R.id.edtSOCMND);
        edtSoTaiKhoan = findViewById(R.id.edtSoTaiKhoan);
        edtTenChiNhanh = findViewById(R.id.edtTenChiNhanh);
        edtTenChuTaiKhoan = findViewById(R.id.edtTenChuTaiKhoan);
        spDSNganHang = findViewById(R.id.spTenNganHang);
        btnLuuThongTin = findViewById(R.id.btnLuuThongTin);
    }

}