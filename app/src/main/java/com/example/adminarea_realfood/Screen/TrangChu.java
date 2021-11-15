package com.example.adminarea_realfood.Screen;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.adminarea_realfood.Fragment.CaiDat_fragment;
import com.example.adminarea_realfood.Fragment.ThongBao_fragment;
import com.example.adminarea_realfood.Fragment.TrangChu_fragment;
import com.example.adminarea_realfood.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class TrangChu extends AppCompatActivity {

    FrameLayout frameLayout;
    BottomBar bottomBar;
    public static Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().hide();
        setContentView(R.layout.trangchu_activity);
        setControl();
        setEvent();
    }

    private void loadFragment(Fragment   fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setEvent() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId)
                {
                    case R.id.tab_trangchu:
                        TrangChu_fragment trangChuFragment = new TrangChu_fragment();
                        loadFragment(trangChuFragment);
                        break;
                    case R.id.tab_thongbao:
                        ThongBao_fragment thongBaoFragment = new ThongBao_fragment();
                        loadFragment(thongBaoFragment);
                        break;
                    case R.id.tab_caidat:
                        CaiDat_fragment caiDatFragment = new CaiDat_fragment();
                        loadFragment(caiDatFragment);
                        break;
                }
            }
        });
    }

    private void setControl() {
        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        frameLayout = findViewById(R.id.fragment);
    }
}