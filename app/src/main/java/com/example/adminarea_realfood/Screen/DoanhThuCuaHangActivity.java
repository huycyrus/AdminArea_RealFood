package com.example.adminarea_realfood.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.CuaHang;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.TrangThai.TrangThaiCuaHang;
import com.example.adminarea_realfood.Adapter.Shop_ThongKe_Adapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DoanhThuCuaHangActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout ;
    RecyclerView recyclerView;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    ArrayList<CuaHang>cuaHangs = new ArrayList<>();
    Shop_ThongKe_Adapter shop_thongKe_adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanh_thu_cua_hang);
        shop_thongKe_adapter = new Shop_ThongKe_Adapter(this,R.layout.shop_thongke_item,cuaHangs);
        setControl();
        setEvent();
        LoadData();
    }

    private void LoadData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(shop_thongKe_adapter);
        firebase_manager.mDatabase.child("CuaHang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CuaHang cuaHang = dataSnapshot.getValue(CuaHang.class);
                    if (cuaHang.getTrangThaiCuaHang()!= TrangThaiCuaHang.ChuaKichHoat)
                    {
                        cuaHangs.add(cuaHang);
                    }

                }
                shop_thongKe_adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }



    private void setEvent() {

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void setControl() {
        swipeRefreshLayout = findViewById(R.id.swipeLayout);
        recyclerView = findViewById(R.id.rcDanhSachCuaHang);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        MenuItem menuItem = menu.findItem(R.id.action_Search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Tìm kiếm");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                shop_thongKe_adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                shop_thongKe_adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}