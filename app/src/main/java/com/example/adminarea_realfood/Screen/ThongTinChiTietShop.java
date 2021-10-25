package com.example.adminarea_realfood.Screen;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.adminarea_realfood.Fragment.BaoCaoShop_fragment;
import com.example.adminarea_realfood.Fragment.ThongTinChiTietShop_fragment;
import com.example.adminarea_realfood.Model.CuaHang;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.TrangThai.TrangThaiCuaHang;
import com.google.gson.Gson;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class ThongTinChiTietShop extends AppCompatActivity {

    FrameLayout frameLayout;
    BottomBar bottomBar;
    public static Fragment fragment;
    CuaHang cuaHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ttctshop_activity);
        setTitle("Cua Hang");
        setControl();
        if (getIntent() != null && getIntent().getExtras() != null) {
            Intent intent = getIntent();
            String dataShop = intent.getStringExtra("CuaHang");
            Gson gson = new Gson();
            cuaHang = gson.fromJson(dataShop, CuaHang.class);
        }
        setEvent();
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_shop, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setEvent() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (cuaHang.getTrangThaiCuaHang() == TrangThaiCuaHang.ChuaKichHoat){
            bottomBar.setVisibility(View.GONE);
        }
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_ttct:
                        ThongTinChiTietShop_fragment thongTinChiTietShop_fragment = new ThongTinChiTietShop_fragment(cuaHang);
                        loadFragment(thongTinChiTietShop_fragment);
                        break;
                    case R.id.tab_baocao:
                        BaoCaoShop_fragment baoCaoShop_fragment = new BaoCaoShop_fragment();
                        loadFragment(baoCaoShop_fragment);
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            finish();
        } else if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

    }

    private void setControl() {
        bottomBar = (BottomBar) findViewById(R.id.bottomBar_shop);
        frameLayout = findViewById(R.id.fragment_shop);
    }
}