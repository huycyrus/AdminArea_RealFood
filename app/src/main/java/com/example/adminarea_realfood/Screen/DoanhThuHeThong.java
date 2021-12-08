package com.example.adminarea_realfood.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Fragment.DoanhThuHeThong_fragment;
import com.example.adminarea_realfood.Model.CuaHang;
import com.example.adminarea_realfood.Model.DonHang;
import com.example.adminarea_realfood.Model.ThanhToan;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.TrangThai.TrangThaiDonHang;
import com.example.adminarea_realfood.TrangThai.TrangThaiThanhToan;
import com.example.adminarea_realfood.Adapter.CuaHangAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

public class DoanhThuHeThong extends AppCompatActivity {
    int giaoThanhCong = 0, giaoKhongThanhCong = 0;
    NumberFormat formatter = new DecimalFormat("#0.0");

    String[] info = {"Giao thành công", "Giao không thành công"};
    Firebase_Manager firebase_manager = new Firebase_Manager();
    TextView tvTongDoanhThu, tvGiaoThanhCong,tvDathu, tvGiaoKhongThanhCong, tvTongCuaHang,tvHoaHong,tvShipper;
    Button btnNgayBatDau, btnNgayKetThuc;
    ProgressBar progressBar;
    LinearLayout linearLayout;
    ArrayList<DonHang> donHangs = new ArrayList<>();
    ArrayList<DonHang> display = new ArrayList<>();
    Date ngayBatDau = new Date();
    Date ngayKetThuc = new Date();
    RecyclerView rcvDanhSachCuaHang;
    ArrayList<CuaHang> cuaHangs = new ArrayList<>();
    CuaHangAdapter cuaHangAdapter = new CuaHangAdapter(this,R.layout.item_cuahang,cuaHangs);
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanhthu_hethong);
        setControl();
        setEvent();
        LoadData();
        linearLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        final SwipeRefreshLayout refreshLayout = findViewById(R.id.pullToRefesh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadData();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void LoadData() {
        firebase_manager.mDatabase.child("DonHang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                donHangs.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DonHang donHang = dataSnapshot.getValue(DonHang.class);
                    donHangs.add(donHang);
                }
                display = donHangs;
                GetDoanhThuHeThong(donHangs);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        firebase_manager.mDatabase.child("CuaHang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                tvTongCuaHang.setText(snapshot.getChildrenCount() + "");
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private void setEvent() {
        btnNgayBatDau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(ngayBatDau);
                DatePickerDialog dpd = new DatePickerDialog(DoanhThuHeThong.this,
                        (view1, year, month, dayOfMonth) -> {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, month, dayOfMonth);
                            DateFormat formatter = new SimpleDateFormat("dd / MM /yyyy");
                            Date date = calendar.getTime();
                            ngayBatDau = date;
                            btnNgayBatDau.setText(formatter.format(date));
                            display = (ArrayList<DonHang>) donHangs.stream().filter(donHang -> donHang.getNgayTao().compareTo(date) > 0).collect(Collectors.toList());
                            display = (ArrayList<DonHang>) display.stream().filter(donHang -> donHang.getNgayTao().compareTo(ngayKetThuc) < 0).collect(Collectors.toList());
                            GetDoanhThuHeThong(display);
                            LoadPieChart();

                        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                dpd.show();
            }
        });
        btnNgayKetThuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(ngayKetThuc);

                DatePickerDialog dpd = new DatePickerDialog(DoanhThuHeThong.this,
                        (view1, year, month, dayOfMonth) -> {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, month, dayOfMonth);
                            DateFormat formatter = new SimpleDateFormat("dd / MM /yyyy");
                            Date date = calendar.getTime();
                            ngayKetThuc = date;
                            btnNgayKetThuc.setText(formatter.format(date));
                            display = (ArrayList<DonHang>) donHangs.stream().filter(donHang -> donHang.getNgayTao().compareTo(date) < 0).collect(Collectors.toList());
                            display = (ArrayList<DonHang>) display.stream().filter(donHang -> donHang.getNgayTao().compareTo(ngayBatDau) > 0).collect(Collectors.toList());
                            GetDoanhThuHeThong(display);
                            LoadPieChart();
                        }
                        , cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
                dpd.show();
            }
        });

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvDanhSachCuaHang.setLayoutManager(linearLayoutManager);
        rcvDanhSachCuaHang.setAdapter(cuaHangAdapter);
        LoadItemCuaHang();
    }

    private void LoadItemCuaHang(){
        firebase_manager.mDatabase.child("CuaHang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot :snapshot.getChildren()){
                    CuaHang cuaHang = dataSnapshot.getValue(CuaHang.class);
                    cuaHangs.add(cuaHang);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void GetDoanhThuHeThong(ArrayList<DonHang> donHangs) {
        giaoThanhCong = 0;
        giaoKhongThanhCong = 0;
        int tong = 0;
        linearLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        for (DonHang donHang : donHangs) {
            if ( donHang.getTrangThai() == TrangThaiDonHang.Shipper_DaChuyenTien) {
                giaoThanhCong++;
                tong += donHang.getTongTien();
            }
            if (donHang.getTrangThai() == TrangThaiDonHang.Shipper_GiaoKhongThanhCong ||
                    donHang.getTrangThai() == TrangThaiDonHang.ChoShopXacNhan_TraHang ||
                    donHang.getTrangThai() == TrangThaiDonHang.Shipper_DaTraHang ||
                    donHang.getTrangThai() == TrangThaiDonHang.KhachHang_HuyDon ||
                    donHang.getTrangThai() == TrangThaiDonHang.SHOP_HuyDonHang) {
                giaoKhongThanhCong++;
            }

        }
        LoadPieChart();
        tvTongDoanhThu.setText(tong + " VNĐ");
        double finalTong = tong;
        firebase_manager.mDatabase.child("LoiNhuan").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Long hoaHong = dataSnapshot.getValue(Long.class);
                tvHoaHong.setText(formatter.format(finalTong * ((double) hoaHong / 100)) + " VND");
            }
        });
        firebase_manager.mDatabase.child("Shipper").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                tvShipper.setText(dataSnapshot.getChildrenCount()+"");
            }
        });
        firebase_manager.mDatabase.child("ThanhToan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                int tong =0;
                for (DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    ThanhToan thanhToan = dataSnapshot.getValue(ThanhToan.class);
                    if (thanhToan.getTrangThaiThanhToan()== TrangThaiThanhToan.DaXacNhan)
                    {
                        tong+=  thanhToan.getSoTien();
                    }
                }
                tvDathu.setText(tong+" VND");
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        tvGiaoThanhCong.setText(giaoThanhCong + "");
        tvGiaoKhongThanhCong.setText(giaoKhongThanhCong + "");
        linearLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    private void LoadPieChart() {
        loadFragment(new DoanhThuHeThong_fragment(giaoThanhCong, giaoKhongThanhCong));
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setControl() {
        tvTongDoanhThu = findViewById(R.id.tvTongDoanhThu);
        tvGiaoThanhCong = findViewById(R.id.tvGiaoThanhCong);
        tvGiaoKhongThanhCong = findViewById(R.id.tvGiaoKhongThanhCong);
        tvTongCuaHang = findViewById(R.id.tvTongCuaHang);
        btnNgayBatDau = findViewById(R.id.btnNgayBatDau);
        btnNgayKetThuc = findViewById(R.id.btnNgayKetThuc);
        linearLayout = findViewById(R.id.lnLayout);
        progressBar = findViewById(R.id.progessbar);
        rcvDanhSachCuaHang = findViewById(R.id.rcvDanhSachCuaHang);
        tvHoaHong = findViewById(R.id.tvHoaHong);
        tvShipper = findViewById(R.id.tvTongShipper);
        tvDathu = findViewById(R.id.tvDathu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}