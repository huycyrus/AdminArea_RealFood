package com.example.adminarea_realfood.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.Shipper;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.adapter.ShipperAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class DanhSachShipper extends AppCompatActivity {

    ArrayList<Shipper> shippers;
    ListView lvDanhsach;
    ShipperAdapter shipperAdapter;
    Shipper shipper;
    FloatingActionButton fabThem;
    Firebase_Manager firebase_manager = new Firebase_Manager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danhsachshipper_activity);
        shippers = new ArrayList<Shipper>();

        setControl();

        setEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDanhsachshipper();
    }

    private void setEvent() {
        shipperAdapter = new ShipperAdapter(this, R.layout.shipper_listview, shippers);
        lvDanhsach.setAdapter(shipperAdapter);
        fabThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhSachShipper.this, TaoTaiKhoanShipper.class);
                startActivity(intent);
            }
        });

    }

    public void getDanhsachshipper() {
        firebase_manager.mDatabase.child("Shipper").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shippers.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Shipper shipper = postSnapshot.getValue(Shipper.class);
                    shippers.add(shipper);
                    shipperAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
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
                shipperAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                shipperAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void setControl() {
        lvDanhsach = findViewById(R.id.lv_danhsachshipper);
        fabThem = findViewById(R.id.fab_add);
    }
}
