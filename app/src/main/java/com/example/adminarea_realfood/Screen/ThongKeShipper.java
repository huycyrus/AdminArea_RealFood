package com.example.adminarea_realfood.Screen;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.DonHang;
import com.example.adminarea_realfood.Model.Shipper;
import com.example.adminarea_realfood.Fragment.PieChartShipper_Fragment;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.TrangThai.TrangThaiDonHang;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

public class ThongKeShipper extends AppCompatActivity {
    int tongDonHang;
    int donHangGiaoThanhCong = 0, donHangGiaoKhongThanhCong = 0, donHangDangDiGiao = 0,choGiao = 0;
    String[] info = {"Đơn hàng giao thành công", "Đơn hàng giao không thành công", "Đơn hàng đang đi giao"};
    TextView tvDonHangGiaoThanhCong, tvDonHangGiaoKhongThanhCong, tvDonHangDangGiao, tvTongTien, tvTongDonHang, tvTyLe;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    Button btnNgayBatDau, btnNgayKetThuc;
    ProgressBar progressBar;
    LinearLayout lnLayout;
    ArrayList<DonHang> donHangs = new ArrayList<>();
    ArrayList<DonHang> display = new ArrayList<>();
    Date dateBatDau = new Date(), dateKetThuc = new Date();
    Shipper shipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thongkeshipper_activity);
        setControl();
        if (getIntent() != null && getIntent().getExtras() != null) {
            Intent intent = getIntent();
            String dataShipper = intent.getStringExtra("Shipper");
            Gson gson = new Gson();
            shipper = gson.fromJson(dataShipper, Shipper.class);
        }
        setEvent();
        LoadData();
        lnLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefesh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadData();
                pullToRefresh.setRefreshing(false);
            }
        });

    }

    private void LoadData() {


        firebase_manager.mDatabase.child("DonHang").orderByChild("idshipper").equalTo(shipper.getiDShipper()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                donHangs.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    DonHang donHang = postSnapshot.getValue(DonHang.class);
                    if (donHang.getTrangThai()!=TrangThaiDonHang.SHOP_ChoShipperLayHang&&donHang.getTrangThai()!=TrangThaiDonHang.SHOP_DangGiaoShipper)
                    {
                        donHangs.add(donHang);
                    }
                }
                display = donHangs;
                GetThongKe_DonHang(donHangs);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setEvent() {
        btnNgayKetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateKetThuc);

                DatePickerDialog dpd = new DatePickerDialog(ThongKeShipper.this,
                        (view1, year, month, dayOfMonth) -> {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, month, dayOfMonth);
                            DateFormat formatter = new SimpleDateFormat("dd / MM /yyyy");
                            Date date = calendar.getTime();
                            dateKetThuc = date;
                            btnNgayKetThuc.setText(formatter.format(date));
                            display = (ArrayList<DonHang>) donHangs.stream().filter(donHang -> donHang.getNgayTao().compareTo(date) < 0).collect(Collectors.toList());
                            display = (ArrayList<DonHang>) display.stream().filter(donHang -> donHang.getNgayTao().compareTo(dateBatDau) > 0).collect(Collectors.toList());

                            GetThongKe_DonHang(display);
                            LoadPieChart();
                        }
                        , cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                dpd.show();
            }
        });

        btnNgayBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateBatDau);
                DatePickerDialog dpd = new DatePickerDialog(ThongKeShipper.this,
                        (view1, year, month, dayOfMonth) -> {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, month, dayOfMonth);
                            DateFormat formatter = new SimpleDateFormat("dd / MM /yyyy");
                            Date date = calendar.getTime();
                            dateBatDau = date;
                            btnNgayBatDau.setText(formatter.format(date));
                            display = (ArrayList<DonHang>) donHangs.stream().filter(donHang -> donHang.getNgayTao().compareTo(date) > 0).collect(Collectors.toList());
                            display = (ArrayList<DonHang>) display.stream().filter(donHang -> donHang.getNgayTao().compareTo(dateKetThuc) < 0).collect(Collectors.toList());
                            GetThongKe_DonHang(display);
                            LoadPieChart();

                        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                dpd.show();
            }
        });
    }

    public void GetThongKe_DonHang(ArrayList<DonHang> donHangs) {
        donHangGiaoThanhCong = 0;
        donHangGiaoKhongThanhCong = 0;
        donHangDangDiGiao = 0;
        int tong = 0;
        float tyle = 0f;
        lnLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        for (DonHang donHang : donHangs) {
            if (donHang.getTrangThai() == TrangThaiDonHang.Shipper_DaLayHang ||
                    donHang.getTrangThai() == TrangThaiDonHang.Shipper_DangGiaoHang) {
                donHangDangDiGiao++;
                tongDonHang++;
            }
            if (donHang.getTrangThai() == TrangThaiDonHang.Shipper_DaLayHang ||
                    donHang.getTrangThai() == TrangThaiDonHang.Shipper_DangGiaoHang) {
                donHangDangDiGiao++;
                tongDonHang++;
            }
            if (donHang.getTrangThai() == TrangThaiDonHang.Shipper_GiaoKhongThanhCong ||
                    donHang.getTrangThai() == TrangThaiDonHang.ChoShopXacNhan_TraHang ||
                    donHang.getTrangThai() == TrangThaiDonHang.Shipper_DaTraHang ||
                    donHang.getTrangThai() == TrangThaiDonHang.KhachHang_HuyDon) {
                donHangGiaoKhongThanhCong++;
                tongDonHang++;
            }
            if (donHang.getTrangThai() == TrangThaiDonHang.Shipper_GiaoThanhCong ||
                    donHang.getTrangThai() == TrangThaiDonHang.ChoShopXacNhan_Tien ||
                    donHang.getTrangThai() == TrangThaiDonHang.Shipper_DaChuyenTien) {
                donHangGiaoThanhCong++;
                tong += donHang.getTongTien();
                tongDonHang++;
            }
        }
        LoadPieChart();
        tvTongTien.setText(tong + " VND");
        tvTongDonHang.setText(tongDonHang + "");
        tvDonHangGiaoThanhCong.setText(donHangGiaoThanhCong + "");
        tvDonHangGiaoKhongThanhCong.setText(donHangGiaoKhongThanhCong + "");
        tvDonHangDangGiao.setText(donHangDangDiGiao + "");
        tyle = ((Float.parseFloat(donHangGiaoThanhCong + "") / Float.parseFloat(tongDonHang + "")) * 100);
        tvTyLe.setText(tyle + "%");
        lnLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout_tks, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void LoadPieChart() {
        loadFragment(new PieChartShipper_Fragment(donHangGiaoThanhCong, donHangGiaoKhongThanhCong, donHangDangDiGiao));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setControl() {
        tvTongTien = findViewById(R.id.txtTongTien_tks);
        tvDonHangGiaoThanhCong = findViewById(R.id.txtGiaoThanhCong_tks);
        tvDonHangGiaoKhongThanhCong = findViewById(R.id.txtGiaoKoThanhCong_tks);
        tvDonHangDangGiao = findViewById(R.id.txtDangGiao_tks);
        tvTongDonHang = findViewById(R.id.tvTongDonHang_tks);
        tvTyLe = findViewById(R.id.tvTiLeGiaoHang_tks);
        lnLayout = findViewById(R.id.lnLayout_tks);
        progressBar = findViewById(R.id.progessbar_tks);
        btnNgayBatDau = findViewById(R.id.btnNgayBatDau_tks);
        btnNgayKetThuc = findViewById(R.id.btnNgayKetThuc_tks);

    }
}