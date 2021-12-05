package com.example.adminarea_realfood.Screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.CuaHang;
import com.example.adminarea_realfood.Model.ThanhToan;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.ThanhToanActivity;
import com.example.adminarea_realfood.adapter.CuaHang_Spinner_Adapter;
import com.example.adminarea_realfood.adapter.ThanhToanAdapter;
import com.example.adminarea_realfood.databinding.ActivityDanhSachHoaDonBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import gr.escsoft.michaelprimez.searchablespinner.interfaces.OnItemSelectedListener;

public class DanhSachHoaDonActivity extends AppCompatActivity {
    ActivityDanhSachHoaDonBinding binding;
    ArrayList<ThanhToan>thanhToans = new ArrayList<>();
    ThanhToanAdapter thanhToanAdapter;
    ArrayList<CuaHang> cuaHangs = new ArrayList<>();
    CuaHang_Spinner_Adapter cuaHang_spinner_adapter;
    LinearLayoutManager linearLayoutManager ;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDanhSachHoaDonBinding.inflate(getLayoutInflater());
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        cuaHang_spinner_adapter = new CuaHang_Spinner_Adapter(this, R.layout.sp_cuahang_item, R.id.tvTenCuaHang, cuaHangs);
        thanhToanAdapter = new ThanhToanAdapter(this,R.layout.hoadon_item,thanhToans);
        setContentView(binding.getRoot());
        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.swipeLayout);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadData();
                thanhToanAdapter.getFilter().filter("");
                pullToRefresh.setRefreshing(false);
            }
        });
        setEvent();
        LoadData();
    }

    private void LoadData() {
        firebase_manager.mDatabase.child("ThanhToan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                thanhToans.clear();
                for (DataSnapshot dataSnapshot :snapshot.getChildren())
                {
                    ThanhToan thanhToan = dataSnapshot.getValue(ThanhToan.class);
                    thanhToans.add(thanhToan);
                }
                thanhToanAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhSachHoaDonActivity.this, ThanhToanActivity.class);
                startActivity(intent);
            }
        });
        firebase_manager.mDatabase.child("CuaHang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CuaHang cuaHang = dataSnapshot.getValue(CuaHang.class);
                    cuaHangs.add(cuaHang);
                }
                binding.spCuaHang.setAdapter(cuaHang_spinner_adapter);
                cuaHang_spinner_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void setEvent() {
        binding.rcDanhsachshipper.setAdapter(thanhToanAdapter);
        binding.rcDanhsachshipper.setLayoutManager(linearLayoutManager);
        binding.spCuaHang.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(View view, int position, long id) {
                thanhToanAdapter.getFilter().filter("");

                CuaHang temp = cuaHangs.get(position);
                thanhToanAdapter.getFilter().filter(temp.getIDCuaHang());
            }

            @Override
            public void onNothingSelected() {

            }
        });
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
                thanhToanAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                thanhToanAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


}