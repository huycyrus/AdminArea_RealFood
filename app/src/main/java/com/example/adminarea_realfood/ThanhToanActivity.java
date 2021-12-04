package com.example.adminarea_realfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.developer.kalert.KAlertDialog;
import com.example.adminarea_realfood.Model.CuaHang;
import com.example.adminarea_realfood.Model.DonHang;
import com.example.adminarea_realfood.Model.TaiKhoanNganHang;
import com.example.adminarea_realfood.Model.ThanhToan;
import com.example.adminarea_realfood.TrangThai.LoaiThongBao;
import com.example.adminarea_realfood.TrangThai.TrangThaiDonHang;
import com.example.adminarea_realfood.TrangThai.TrangThaiThanhToan;
import com.example.adminarea_realfood.adapter.CuaHang_Spinner_Adapter;
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
import java.text.SimpleDateFormat;
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
        setEvent();
        LoadData();

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
                if (cuaHang.getNgayThanhToan() != null) {
                    long noDay = (new Date().getTime() - cuaHang.getNgayThanhToan().getTime()) / (24 * 3600 * 1000);
                    ;
                    if (noDay < 30) {
                        Alerter.create(ThanhToanActivity.this)
                                .setTitle("Thông báo")
                                .setText("Cửa hàng " + cuaHang.getTenCuaHang() + " đã thanh toán " + noDay + " trước")
                                .setBackgroundColorRes(R.color.warning_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                                .show();
                    }
                    if (noDay > 30) {
                        Alerter.create(ThanhToanActivity.this)
                                .setTitle("Thông báo")
                                .setText("Cửa hàng " + cuaHang.getTenCuaHang() + " chưa thanh toán trong vòng " + noDay + " ngày")
                                .setBackgroundColorRes(R.color.error_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                                .show();
                    }
                } else {
                    Alerter.create(ThanhToanActivity.this)
                            .setTitle("Thông báo")
                            .setText("Cửa hàng " + cuaHang.getTenCuaHang() + " chưa tạo hóa đơn nào !")
                            .setBackgroundColorRes(R.color.warning_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                            .show();
                }
                firebase_manager.mDatabase.child("DonHang").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            //Tổng =0
                            tongtien = 0;
                            //nếu đơn hàng của cửa hàng có tồn tại thì check == true
                            check = true;
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                DonHang donHang = snapshot.getValue(DonHang.class);
                                if (donHang.getTrangThai() == TrangThaiDonHang.Shipper_DaChuyenTien&&donHang.getIDCuaHang().equals(cuaHang.getIDCuaHang())) {
                                    //nếu chưa tạo hóa đơn
                                    if (cuaHang.getNgayThanhToan() == null) {
                                        tongtien += donHang.getTongTien();
                                        //đã tạo hóa đơn thì lấy những đơn hàng bé hơn ngày thanh toán vừa rồi cảu cửa hàng
                                    } else {
                                        Date date = new Date();
                                        if (donHang.getNgayGiaoHang() != null) {
                                            if (donHang.getNgayGiaoHang().after(cuaHang.getNgayThanhToan())) {
                                                tongtien += donHang.getTongTien();
                                                Log.d("ID",donHang.getIDDonHang());
                                            }
                                        }
                                    }

                                }
                            }
                            firebase_manager.mDatabase.child("LoiNhuan").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                @Override
                                public void onSuccess(DataSnapshot dataSnapshot) {
                                    Long hoaHong  = dataSnapshot.getValue(Long.class);
                                    binding.edtSoTien.setText(formatter.format(tongtien*((double)hoaHong/100))+"");

                                }
                            });


                        } else {
                            Alerter.create(ThanhToanActivity.this)
                                    .setTitle("Thông báo")
                                    .setText("Cửa hàng chưa có đơn hàng nào !")
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
                if (cuaHang != null) {
                    if (avaTar != null) {
                        if (!validate.isBlank(binding.edtSoTien) && !validate.isBlank(binding.edtNoiDung)
                                && !validate.isBlank(binding.edtSoTaiKhoan) && !validate.isBlank(binding.edtTenNguoiGui)
                                && !validate.isBlank(binding.edtTenNganHang)) {
                            if (check=true)
                            {
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
                                firebase_manager.mDatabase.child("CuaHang").child(cuaHang.getIDCuaHang()).setValue(cuaHang);
                                Alerter.create(ThanhToanActivity.this)
                                        .setTitle("Thông báo")
                                        .setText("Đã xác nhận hóa đơn cho cửa hàng : " + cuaHang.getTenCuaHang() + " với giá trị : " + binding.edtSoTien.getText().toString() + " Ngày thanh toán:  " + new Date().toString())
                                        .setBackgroundColorRes(R.color.success_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                                        .show();
                                cuaHang.setGhiChu(null);
                                firebase_manager.mDatabase.child("CuaHang").child(cuaHang.getIDCuaHang()).setValue(cuaHang);
                                finish();
                            }
                            else {
                                Alerter.create(ThanhToanActivity.this)
                                        .setTitle("Lỗi")
                                        .setText("Cửa hàng này chưa có đơn hàng nào")
                                        .setBackgroundColorRes(R.color.error_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                                        .show();
                            }


                        }
                    } else {
                        Alerter.create(ThanhToanActivity.this)
                                .setTitle("Lỗi")
                                .setText("Vui lòng chọn ảnh hóa đơn")
                                .setBackgroundColorRes(R.color.error_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                                .show();
                    }
                } else {
                    Alerter.create(ThanhToanActivity.this)
                            .setTitle("Lỗi")
                            .setText("Vui lòng chọn cửa hàng")
                            .setBackgroundColorRes(R.color.error_stroke_color) // or setBackgroundColorInt(Color.CYAN)
                            .show();
                }

            }
        });
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
                    String dataDonHang = intent.getStringExtra("cuaHang");
                    Gson gson = new Gson();
                    cuaHang = gson.fromJson(dataDonHang, CuaHang.class);
                    AtomicInteger positon = new AtomicInteger();
                    cuaHangs.forEach(temp -> {
                        if (temp.getIDCuaHang().equals(cuaHang.getIDCuaHang()))
                        {
                            binding.spCuaHang.setSelected(true);
                            binding.spCuaHang.setActivated(true);
                            binding.spCuaHang.dispatchSetSelected(true);
                            binding.spCuaHang.setSelectedItem(positon.get());
                        }
                        positon.getAndIncrement();
                    });



                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

}