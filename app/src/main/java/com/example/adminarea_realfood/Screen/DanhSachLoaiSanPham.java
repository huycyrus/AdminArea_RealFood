package com.example.adminarea_realfood.Screen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.LoaiSanPham;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.adapter.LoaiSanPhamAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DanhSachLoaiSanPham extends AppCompatActivity {
    ArrayList<LoaiSanPham> loaiSanPhams;
    ListView lvDanhsachloai;
    FloatingActionButton fabThem;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    LoaiSanPhamAdapter loaiSanPhamAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loaisanpham_activity);
        loaiSanPhams = new ArrayList<LoaiSanPham>();
        setControl();
        getDanhSachLoaiSanPham();
        setEvent();
    }

    private void setEvent() {
        loaiSanPhamAdapter = new LoaiSanPhamAdapter(this, R.layout.loaisanpham_listview, loaiSanPhams);
        lvDanhsachloai.setAdapter(loaiSanPhamAdapter);

        fabThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhSachLoaiSanPham.this, TaoLoaiSanPham.class);
                startActivity(intent);
            }
        });
    }

    public void getDanhSachLoaiSanPham() {
        firebase_manager.mDatabase.child("LoaiSanPham").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loaiSanPhams.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    LoaiSanPham loaiSanPham = postSnapshot.getValue(LoaiSanPham.class);
                    loaiSanPhams.add(loaiSanPham);
                    loaiSanPhamAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void setControl() {
        lvDanhsachloai = findViewById(R.id.lv_danhsachloaisanpham);
        fabThem = findViewById(R.id.fab_add);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);

        MenuItem menuItem = menu.findItem(R.id.action_Search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Tìm kiếm");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loaiSanPhamAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loaiSanPhamAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
