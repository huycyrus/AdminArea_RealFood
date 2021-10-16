package com.example.adminarea_realfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adminarea_realfood.Model.Shipper;
import com.example.adminarea_realfood.adapter.ShipperAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class DanhSachShipper extends AppCompatActivity {

    ArrayList<Shipper> data = new ArrayList<>();
    ListView lvDanhsach;
    Shipper shipper;
    FloatingActionButton fabThem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danhsachshipper_activity);
        setControl();
        setEvent();

    }

    private void setEvent() {
        khoiTao();
        ShipperAdapter shipperAdapter = new ShipperAdapter(this, R.layout.shipper_listview, data);
        lvDanhsach.setAdapter(shipperAdapter);

        fabThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhSachShipper.this,TaoTaiKhoanShipper.class);
                startActivity(intent);
            }
        });

    }

    private void khoiTao() {
        Shipper ql1 = new Shipper("","","","Van a","","","","ACx_132","Đang hoạt động","21324124124","");
        Shipper ql2 = new Shipper("","","","Van b","","","","ACx_132","Đang hoạt động","21324124124","");
        Shipper ql3 = new Shipper("","","","Van c","","","","ACx_132","Đang hoạt động","21324124124","");
        Shipper ql4 = new Shipper("","","","Van d","","","","ACx_132","Đang hoạt động","21324124124","");
        Shipper ql5 = new Shipper("","","","Van e","","","","ACx_132","Đang hoạt động","21324124124","");
        Shipper ql6 = new Shipper("","","","Van f","","","","ACx_132","Đang hoạt động","21324124124","");
        Shipper ql7 = new Shipper("","","","Van j","","","","ACx_132","Đang hoạt động","21324124124","");
        Shipper ql8 = new Shipper("","","","Van h","","","","ACx_132","Đang hoạt động","21324124124","");
        Shipper ql9 = new Shipper("","","","Van g","","","","ACx_132","Đang hoạt động","21324124124","");
        Shipper ql10= new Shipper("","","","Van k","","","","ACx_132","Đang hoạt động","21324124124","");
        Shipper ql11 = new Shipper("","","","Van l","","","","ACx_132","Đang hoạt động","21324124124","");
        Shipper ql12 = new Shipper("","","","Van o","","","","ACx_132","Đang hoạt động","21324124124","");
        Shipper ql13 = new Shipper("","","","Van p","","","","ACx_132","Đang hoạt động","21324124124","");
        Shipper ql14 = new Shipper("","","","Van a","","","","ACx_132","Đang hoạt động","21324124124","");
        Shipper ql15 = new Shipper("","","","Van a","","","","ACx_132","Đang hoạt động","21324124124","");
        Shipper ql16 = new Shipper("","","","Van a","","","","ACx_132","Đang hoạt động","21324124124","");
        Shipper ql17 = new Shipper("","","","Van a","","","","ACx_132","Đang hoạt động","21324124124","");
        Shipper ql18 = new Shipper("","","","Van a","","","","ACx_132","Đang hoạt động","21324124124","");
        data.add(ql1);
        data.add(ql2);
        data.add(ql3);
        data.add(ql4);
        data.add(ql5);
        data.add(ql6);
        data.add(ql7);
        data.add(ql8);
        data.add(ql9);
        data.add(ql10);
        data.add(ql11);
        data.add(ql12);
        data.add(ql13);
        data.add(ql14);
        data.add(ql15);
        data.add(ql16);
        data.add(ql17);
        data.add(ql18);


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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

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
