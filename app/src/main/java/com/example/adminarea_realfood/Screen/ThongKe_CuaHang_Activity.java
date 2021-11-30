package com.example.adminarea_realfood.Screen;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.CuaHang;
import com.example.adminarea_realfood.Model.DonHang;

import com.example.adminarea_realfood.Fragment.PieChartCuaHang_Fragment;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.TrangThai.TrangThaiDonHang;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

public class ThongKe_CuaHang_Activity extends AppCompatActivity {
    int choXacNhanCoc = 0, dangXuLy = 0, dangGiaoHang = 0, giaoHangThanhCong = 0, giaoHangThatBai = 0;
    String[] info = {"Đã nhận đơn", "Đang xử lý", "Đang giao hàng", "Giao hàng thành công", "Giao hàng thất bạt"};
    TextView txtTongSoDonHang, txtTongDoanhThu, txtDaGiaoThanhCong, txtChoChuyenCoc, txtDangXuLy, txtDangGiaoHang, txtGiaoHangKhongThanhCong;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    Button btnNgayBatDau, btnNgayKetThuc;
    ProgressBar progressBar;
    LinearLayout lnLayout;
    ArrayList<DonHang> donHangs = new ArrayList<>();
    ArrayList<DonHang> display = new ArrayList<>();
    Date dateBatDau = new Date(), dateKetThuc=new Date();
    CuaHang cuaHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cua_hang_thong_ke);
        if (getIntent() != null && getIntent().getExtras() != null) {
            Intent intent = getIntent();
            String dataCuaHang = intent.getStringExtra("CuaHang");
            Gson gson = new Gson();
            cuaHang = gson.fromJson(dataCuaHang, CuaHang.class);
        }
        setControl();
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

    private void setControl() {
        txtTongSoDonHang = findViewById(R.id.txtTongDonHang);
        txtChoChuyenCoc = findViewById(R.id.txtChoChuyenCoc);
        txtDaGiaoThanhCong = findViewById(R.id.txtDaGiaoThanhCong);
        txtTongDoanhThu = findViewById(R.id.txtTongDoanhThu);
        txtDangXuLy = findViewById(R.id.txtDangXuLy);
        txtDangGiaoHang = findViewById(R.id.txtDangGiaoHang);
        txtGiaoHangKhongThanhCong = findViewById(R.id.txtGiaoHangKhongThanhCong);
        lnLayout = findViewById(R.id.lnLayout);
        progressBar = findViewById(R.id.progessbar);
        btnNgayBatDau = findViewById(R.id.btnNgayBatDau);
        btnNgayKetThuc = findViewById(R.id.btnNgayKetThuc);
    }

    private void LoadData() {
        firebase_manager.mDatabase.child("DonHang").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                donHangs.clear();
                Log.d("data",dataSnapshot.toString());
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DonHang donHang = postSnapshot.getValue(DonHang.class);
                    if (donHang.getIDCuaHang().equals(cuaHang.getIDCuaHang()))
                    {
                        donHangs.add(donHang);
                    }
                }
                display = donHangs;
                GetThongKe_DonHang(donHangs);
            }
        });
    }

    private void setEvent() {
        btnNgayKetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateKetThuc);

                DatePickerDialog dpd = new DatePickerDialog(ThongKe_CuaHang_Activity.this,
                        (view1, year, month, dayOfMonth) -> {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, month, dayOfMonth);
                            DateFormat formatter = new SimpleDateFormat("dd / MM /yyyy");
                            Date date = calendar.getTime();
                            dateKetThuc = date;
                            btnNgayKetThuc.setText(formatter.format(date));
                            display = (ArrayList<DonHang>) donHangs.stream().filter(donHang -> donHang.getNgayTao().compareTo(date) < 0).collect(Collectors.toList());
                            display = (ArrayList<DonHang>) display.stream().filter(donHang -> donHang.getNgayTao().compareTo(dateBatDau) >0).collect(Collectors.toList());
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
                DatePickerDialog dpd = new DatePickerDialog(ThongKe_CuaHang_Activity.this,
                        (view1, year, month, dayOfMonth) -> {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, month, dayOfMonth);
                            DateFormat formatter = new SimpleDateFormat("dd / MM /yyyy");
                            Date date = calendar.getTime();
                            dateBatDau = date;
                            btnNgayBatDau.setText(formatter.format(date));
                            display = (ArrayList<DonHang>) donHangs.stream().filter(donHang -> donHang.getNgayTao().compareTo(date) > 0).collect(Collectors.toList());
                            display = (ArrayList<DonHang>) display.stream().filter(donHang -> donHang.getNgayTao().compareTo(dateKetThuc) <0).collect(Collectors.toList());
                            GetThongKe_DonHang(display);
                            LoadPieChart();

                        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                dpd.show();
            }
        });
    }

    public void GetThongKe_DonHang(ArrayList<DonHang> donHangs) {
        choXacNhanCoc = 0;
        dangXuLy = 0;
        dangGiaoHang = 0;
        giaoHangThanhCong = 0;
        int tong = 0;
        giaoHangThatBai = 0;
        lnLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        for (DonHang donHang : donHangs) {
            if (donHang.getTrangThai() == TrangThaiDonHang.SHOP_ChoXacNhanChuyenTien) {
                choXacNhanCoc++;
            }
            if (donHang.getTrangThai() == TrangThaiDonHang.SHOP_DaGiaoChoBep ||
                    donHang.getTrangThai() == TrangThaiDonHang.Bep_DaHuyDonHang ||
                    donHang.getTrangThai() == TrangThaiDonHang.SHOP_DangChuanBihang ||
                    donHang.getTrangThai() == TrangThaiDonHang.SHOP_DaChuanBiXong ||
                    donHang.getTrangThai() == TrangThaiDonHang.SHOP_DangGiaoShipper ||
                    donHang.getTrangThai() == TrangThaiDonHang.SHOP_ChoShipperLayHang ||
                    donHang.getTrangThai() == TrangThaiDonHang.Shipper_KhongNhanGiaoHang ||
                    donHang.getTrangThai() == TrangThaiDonHang.SHOP_ChoXacNhanGiaoHangChoShipper) {
                dangXuLy++;
            }
            if (donHang.getTrangThai() == TrangThaiDonHang.Shipper_DaLayHang ||
                    donHang.getTrangThai() == TrangThaiDonHang.Shipper_DangGiaoHang) {
                dangGiaoHang++;
            }
            if (donHang.getTrangThai() == TrangThaiDonHang.Shipper_GiaoKhongThanhCong ||
                    donHang.getTrangThai() == TrangThaiDonHang.ChoShopXacNhan_TraHang ||
                    donHang.getTrangThai() == TrangThaiDonHang.Shipper_DaTraHang ||
                    donHang.getTrangThai() == TrangThaiDonHang.KhachHang_HuyDon ||
                    donHang.getTrangThai() == TrangThaiDonHang.SHOP_HuyDonHang) {
                giaoHangThatBai++;
            }
            if (donHang.getTrangThai() == TrangThaiDonHang.Shipper_GiaoThanhCong ||
                    donHang.getTrangThai() == TrangThaiDonHang.ChoShopXacNhan_Tien ||
                    donHang.getTrangThai() == TrangThaiDonHang.Shipper_DaChuyenTien) {
                giaoHangThanhCong++;
                tong += donHang.getTongTien();
            }

        }
        LoadPieChart();
        txtTongDoanhThu.setText(tong + " VND");
        txtChoChuyenCoc.setText(choXacNhanCoc + "");
        txtDaGiaoThanhCong.setText(giaoHangThanhCong + "");
        txtDangGiaoHang.setText(dangGiaoHang + "");
        txtGiaoHangKhongThanhCong.setText(giaoHangThatBai + "");
        txtDangXuLy.setText(dangXuLy + "");
        lnLayout.setVisibility(View.VISIBLE);
        txtTongSoDonHang.setText(display.size() + "");
        progressBar.setVisibility(View.GONE);
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void LoadPieChart() {
        loadFragment(new PieChartCuaHang_Fragment(choXacNhanCoc, dangXuLy, dangGiaoHang, giaoHangThanhCong, giaoHangThatBai));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}


