package com.example.adminarea_realfood.Screen;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.Shipper;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.Adapter.ThongKeShipperAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DanhSachThongKeShipper extends AppCompatActivity {

    ArrayList<Shipper> shippers;
    ListView lvDanhsach;
    ThongKeShipperAdapter thongKeShipperAdapter;
    Firebase_Manager firebase_manager = new Firebase_Manager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danhsachthongkeshipper_activity);
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
        thongKeShipperAdapter = new ThongKeShipperAdapter(this, R.layout.shipper_tks_listview, shippers);
        lvDanhsach.setAdapter(thongKeShipperAdapter);

    }

    public void getDanhsachshipper() {
        firebase_manager.mDatabase.child("Shipper").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shippers.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Shipper shipper = postSnapshot.getValue(Shipper.class);
                    shippers.add(shipper);
                    thongKeShipperAdapter.notifyDataSetChanged();

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
                thongKeShipperAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                thongKeShipperAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void setControl() {
        lvDanhsach = findViewById(R.id.lv_danhsachshipper_TKS);
    }
}