package com.example.adminarea_realfood.Screen;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.KhachHang;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.adapter.KhachHangAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DanhSachKhachHang extends AppCompatActivity {
    
    ListView lvDanhSachKhachHang;
    KhachHangAdapter khachHangAdapter ;
    ArrayList<KhachHang> khachHangs = new ArrayList<>();
    Firebase_Manager firebase_manager = new Firebase_Manager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danhsachkhachhang_activity);
        setControl();
        setEvent();
        getDanhsachkhachhang();
    }

    private void setEvent() {
        khachHangAdapter = new KhachHangAdapter(this, R.layout.khachhang_listview, khachHangs);
        lvDanhSachKhachHang.setAdapter(khachHangAdapter);
    }

    public void getDanhsachkhachhang() {
        firebase_manager.mDatabase.child("KhachHang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                khachHangs.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    KhachHang khachHang = postSnapshot.getValue(KhachHang.class);
                    khachHangs.add(khachHang);
                    khachHangAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void setControl() {
        lvDanhSachKhachHang = findViewById(R.id.lv_danhsachkhachhang);
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
                khachHangAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                khachHangAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}