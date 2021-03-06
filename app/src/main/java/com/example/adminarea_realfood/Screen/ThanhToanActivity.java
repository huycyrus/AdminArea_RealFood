package com.example.adminarea_realfood.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.CuaHang;
import com.example.adminarea_realfood.Model.DonHang;
import com.example.adminarea_realfood.Model.TaiKhoanNganHang;
import com.example.adminarea_realfood.Model.ThanhToan;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.TrangThai.TrangThaiDonHang;
import com.example.adminarea_realfood.TrangThai.TrangThaiThanhToan;
import com.example.adminarea_realfood.Adapter.CuaHang_Spinner_Adapter;
import com.example.adminarea_realfood.Validate;
import com.example.adminarea_realfood.databinding.ActivityThanhToanBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.tapadoo.alerter.Alerter;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;

public class ThanhToanActivity extends AppCompatActivity {
    ActivityThanhToanBinding binding;
    ArrayList<CuaHang> cuaHangs = new ArrayList<>();
    Firebase_Manager firebase_manager = new Firebase_Manager();
    CuaHang_Spinner_Adapter cuaHang_spinner_adapter;
    private Uri avaTar;
    Validate validate = new Validate();
    CuaHang cuaHang;
    ThanhToan thanhToan;
    NumberFormat formatter = new DecimalFormat("#0.0");
    boolean check = false;
    int tongtien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThanhToanBinding.inflate(getLayoutInflater());
        cuaHang_spinner_adapter = new CuaHang_Spinner_Adapter(this, R.layout.sp_cuahang_item, R.id.tvTenCuaHang, cuaHangs);
        setContentView(binding.getRoot());
        LoadData();

        setEvent();


    }

    private void setEvent() {
        binding.btnEditWallPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup())
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                binding.ibImage.setImageBitmap(r.getBitmap());
                                avaTar = r.getUri();
                            }
                        })
                        .setOnPickCancel(new IPickCancel() {
                            @Override
                            public void onCancelClick() {
                                //TODO: do what you have to if user clicked cancel
                            }
                        }).show(ThanhToanActivity.this);
            }
        });
        binding.spCuaHang.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                CuaHang temp = cuaHang_spinner_adapter.getItem(position);
                cuaHang = temp;
                if (thanhToan==null)
                {
                    if (cuaHang.getNgayThanhToan() != null) {
                        long noDay = (new Date().getTime() - cuaHang.getNgayThanhToan().getTime()) / (24 * 3600 * 1000);
                        ;
                        if (noDay < 30) {
                            Alerter.create(ThanhToanActivity.this)
                                    .setTitle("Th??ng b??o")
                                    .setText("C???a h??ng " + cuaHang.getTenCuaHang() + " ???? thanh to??n " + noDay + " ng??y tr?????c")
                                    .setBackgroundColorRes(R.color.warning_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                                    .show();
                        }
                        if (noDay > 30) {
                            Alerter.create(ThanhToanActivity.this)
                                    .setTitle("Th??ng b??o")
                                    .setText("C???a h??ng " + cuaHang.getTenCuaHang() + " ch??a thanh to??n trong v??ng " + noDay + " ng??y")
                                    .setBackgroundColorRes(R.color.error_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                                    .show();
                        }
                    } else {
                        Alerter.create(ThanhToanActivity.this)
                                .setTitle("Th??ng b??o")
                                .setText("C???a h??ng " + cuaHang.getTenCuaHang() + " ch??a t???o h??a ????n n??o !")
                                .setBackgroundColorRes(R.color.warning_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                                .show();
                    }
                }
                firebase_manager.mDatabase.child("DonHang").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            //T???ng =0
                            tongtien = 0;
                            //n???u ????n h??ng c???a c???a h??ng c?? t???n t???i th?? check == true
                            check = true;
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                DonHang donHang = snapshot.getValue(DonHang.class);
                                if (donHang.getTrangThai() == TrangThaiDonHang.Shipper_DaChuyenTien && donHang.getIDCuaHang().equals(cuaHang.getIDCuaHang())) {
                                    //n???u ch??a t???o h??a ????n
                                    if (cuaHang.getNgayThanhToan() == null) {
                                        tongtien += donHang.getTongTien();
                                        //???? t???o h??a ????n th?? l???y nh???ng ????n h??ng b?? h??n ng??y thanh to??n v???a r???i c???u c???a h??ng
                                    } else {
                                        Date date = new Date();
                                        if (donHang.getNgayGiaoHang() != null) {
                                            if (donHang.getNgayGiaoHang().after(cuaHang.getNgayThanhToan())) {
                                                tongtien += donHang.getTongTien();
                                                Log.d("ID", donHang.getIDDonHang());
                                            }
                                        }
                                    }

                                }
                            }
                            firebase_manager.mDatabase.child("LoiNhuan").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                @Override
                                public void onSuccess(DataSnapshot dataSnapshot) {
                                    Long hoaHong = dataSnapshot.getValue(Long.class);
                                    binding.edtSoTien.setText(formatter.format(tongtien * ((double) hoaHong / 100)) + "");
                                    if (thanhToan!=null)
                                    {
                                        binding.edtSoTien.setText(thanhToan.getSoTien()+"");
                                    }
                                }
                            });
                        } else {
                            Alerter.create(ThanhToanActivity.this)
                                    .setTitle("Th??ng b??o")
                                    .setText("C???a h??ng ch??a c?? ????n h??ng n??o !")
                                    .setBackgroundColorRes(R.color.success_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                                    .show();
                        }
                    }
                });
                firebase_manager.mDatabase.child("TaiKhoanNganHang").orderByChild("idTaiKhoan").equalTo(temp.getIDCuaHang()).limitToFirst(1).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            TaiKhoanNganHang taiKhoanNganHang = dataSnapshot.getValue(TaiKhoanNganHang.class);
                            binding.edtTenNganHang.setText(taiKhoanNganHang.getTenNganHang());
                            binding.edtTenNguoiGui.setText(taiKhoanNganHang.getTenChuTaiKhoan());
                            binding.edtSoTaiKhoan.setText(taiKhoanNganHang.getSoTaiKhoan());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onNothingSelected() {

            }
        });
        binding.btnLuuThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thanhToan==null)
                {
                    if (cuaHang != null) {
                        if (avaTar != null) {
                            if (!validate.isBlank(binding.edtSoTien) && !validate.isBlank(binding.edtNoiDung)
                                    && !validate.isBlank(binding.edtSoTaiKhoan) && !validate.isBlank(binding.edtTenNguoiGui)
                                    && !validate.isBlank(binding.edtTenNganHang)) {
                                if (check = true) {
                                    String uuid = UUID.randomUUID().toString().replace("-", "");
                                    firebase_manager.storageRef.child("ThanhToan").child(uuid).putFile(avaTar).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.

                                        }
                                    });
                                    if (thanhToan == null) {
                                        ThanhToan temp = new ThanhToan(uuid, cuaHang.getIDCuaHang(),
                                                binding.edtNoiDung.getText().toString(), Float.parseFloat(binding.edtSoTien.getText().toString()), binding.edtTenNguoiGui.getText().toString(),
                                                binding.edtSoTaiKhoan.getText().toString(), binding.edtTenNganHang.getText().toString(), new Date(), null, TrangThaiThanhToan.ChoXacNhan);
                                        thanhToan = temp;
                                    }

                                    firebase_manager.mDatabase.child("ThanhToan").child(uuid).setValue(thanhToan);
                                    cuaHang.setNgayThanhToan(new Date());
                                    cuaHang.setGhiChu(null);
                                    firebase_manager.mDatabase.child("CuaHang").child(cuaHang.getIDCuaHang()).setValue(cuaHang);
                                    Alerter.create(ThanhToanActivity.this)
                                            .setTitle("Th??ng b??o")
                                            .setText("???? x??c nh???n h??a ????n cho c???a h??ng : " + cuaHang.getTenCuaHang() + " v???i gi?? tr??? : " + binding.edtSoTien.getText().toString() + " Ng??y thanh to??n:  " + new Date().toString())
                                            .setBackgroundColorRes(R.color.success_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                                            .show();

                                    finish();
                                } else {
                                    Alerter.create(ThanhToanActivity.this)
                                            .setTitle("L???i")
                                            .setText("C???a h??ng n??y ch??a c?? ????n h??ng n??o")
                                            .setBackgroundColorRes(R.color.error_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                                            .show();
                                }

                            }
                        } else {
                            Alerter.create(ThanhToanActivity.this)
                                    .setTitle("L???i")
                                    .setText("Vui l??ng ch???n ???nh h??a ????n")
                                    .setBackgroundColorRes(R.color.error_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                                    .show();
                        }
                    } else {
                        Alerter.create(ThanhToanActivity.this)
                                .setTitle("L???i")
                                .setText("Vui l??ng ch???n c???a h??ng")
                                .setBackgroundColorRes(R.color.error_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                                .show();
                    }
                }
                else {
                    if (cuaHang != null) {
                        if (avaTar != null) {
                            if (!validate.isBlank(binding.edtSoTien) && !validate.isBlank(binding.edtNoiDung)
                                    && !validate.isBlank(binding.edtSoTaiKhoan) && !validate.isBlank(binding.edtTenNguoiGui)
                                    && !validate.isBlank(binding.edtTenNganHang)) {

                                    ThanhToan temp = new ThanhToan(thanhToan.getIdBill(), cuaHang.getIDCuaHang(),
                                    binding.edtNoiDung.getText().toString(), Float.parseFloat(binding.edtSoTien.getText().toString()), binding.edtTenNguoiGui.getText().toString(),
                                            binding.edtSoTaiKhoan.getText().toString(), binding.edtTenNganHang.getText().toString(), thanhToan.getNgayThanhToan(), null, TrangThaiThanhToan.ChoXacNhan);
                                    thanhToan = temp;
                                    firebase_manager.storageRef.child("ThanhToan").child(thanhToan.getIdBill()).putFile(avaTar).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.

                                    }
                                    });
                                    firebase_manager.mDatabase.child("ThanhToan").child(thanhToan.getIdBill()).setValue(thanhToan);
                                    cuaHang.setNgayThanhToan(thanhToan.getNgayThanhToan());
                                    cuaHang.setGhiChu(null);
                                    firebase_manager.mDatabase.child("CuaHang").child(cuaHang.getIDCuaHang()).setValue(cuaHang);
                                    Alerter.create(ThanhToanActivity.this)
                                            .setTitle("Th??ng b??o")
                                            .setText("???? c???p nh???t h??a ????n cho c???a h??ng : " + cuaHang.getTenCuaHang() + " v???i gi?? tr??? : " + binding.edtSoTien.getText().toString() + " Ng??y thanh to??n:  " + new Date().toString())
                                            .setBackgroundColorRes(R.color.success_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                                            .show();

                                    firebase_manager.mDatabase.child("CuaHang").child(cuaHang.getIDCuaHang()).setValue(cuaHang);
                                } else {
                                    Alerter.create(ThanhToanActivity.this)
                                            .setTitle("L???i")
                                            .setText("C???a h??ng n??y ch??a c?? ????n h??ng n??o")
                                            .setBackgroundColorRes(R.color.error_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                                            .show();
                                }


                }



                    }}}}
        );
    }


    private void LoadData() {
        firebase_manager.mDatabase.child("CuaHang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CuaHang cuaHang = dataSnapshot.getValue(CuaHang.class);
                    cuaHangs.add(cuaHang);
                }
                binding.spCuaHang.setAdapter(cuaHang_spinner_adapter);
                cuaHang_spinner_adapter.notifyDataSetChanged();
                if (getIntent() != null && getIntent().getExtras() != null) {

                    Intent intent = getIntent();
                    if (intent.getStringExtra("cuaHang")!=null)
                    {
                        String dataDonHang = intent.getStringExtra("cuaHang");
                        Gson gson = new Gson();
                        cuaHang = gson.fromJson(dataDonHang, CuaHang.class);
                        AtomicInteger positon = new AtomicInteger();
                        cuaHangs.forEach(temp -> {
                            if (temp.getIDCuaHang().equals(cuaHang.getIDCuaHang())) {
                                binding.spCuaHang.setSelected(true);
                                binding.spCuaHang.setActivated(true);
                                binding.spCuaHang.dispatchSetSelected(true);
                                binding.spCuaHang.setSelectedItem(positon.get());
                            }
                            positon.getAndIncrement();
                        });
                    }
                    if (intent.getStringExtra("thanhToan") != null) {
                        String data = intent.getStringExtra("thanhToan");
                        Gson gsons = new Gson();
                        thanhToan = gsons.fromJson(data, ThanhToan.class);
                        AtomicInteger positons = new AtomicInteger();
                        cuaHangs.forEach(temp -> {
                            if (temp.getIDCuaHang().equals(thanhToan.getIdCuaHang())) {
                                binding.spCuaHang.setSelected(true);
                                binding.spCuaHang.setActivated(true);
                                binding.spCuaHang.dispatchSetSelected(true);
                                binding.spCuaHang.setSelectedItem(positons.get());
                            }
                            positons.getAndIncrement();
                        });
                        binding.edtSoTien.setText(thanhToan.getSoTien()+"");
                        binding.edtNoiDung.setText(thanhToan.getNoiDung()+"");
                        binding.edtTenNganHang.setText(thanhToan.getTenNganHang()+"");
                        binding.edtSoTaiKhoan.setText(thanhToan.getSoTaiKhoan()+"");
                        binding.edtTenNguoiGui.setText(thanhToan.getTenNguoiGui()+"");
                        firebase_manager.storageRef.child("ThanhToan").child(thanhToan.getIdBill()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                try {
                                    Glide.with(ThanhToanActivity.this)
                                            .load(uri.toString())
                                            .into(binding.ibImage);
                                    avaTar =uri;
                                }
                                catch (Exception e)
                                {

                                }
                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

}