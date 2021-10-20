package com.example.adminarea_realfood.Screen;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.adminarea_realfood.Fragment.BaoCaoShipper_fragment;
import com.example.adminarea_realfood.Fragment.ThongTinChiTietShipper_fragment;
import com.example.adminarea_realfood.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class ThongTinChiTietShipper extends AppCompatActivity {

    FrameLayout frameLayout;
    BottomBar bottomBar;
    public static Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ttctshipper_activity);
        setTitle("Nguyễn Văn A");
        setControl();
        setEvent();
    }

    private void loadFragment(Fragment   fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_shipper, fragment);
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
                    case R.id.tab_ttct:
                        ThongTinChiTietShipper_fragment thongTinChiTietShipper_fragment = new ThongTinChiTietShipper_fragment();
                        loadFragment(thongTinChiTietShipper_fragment);
                        break;
                    case R.id.tab_baocao:
                        BaoCaoShipper_fragment baoCaoShipper_fragment = new BaoCaoShipper_fragment();
                        loadFragment(baoCaoShipper_fragment);
                        break;
                }
            }
        });
    }

    private void setControl() {
        bottomBar = (BottomBar) findViewById(R.id.bottomBar_shipper);
        frameLayout = findViewById(R.id.fragment_shipper);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_save,menu);

        MenuItem menuItem = menu.findItem(R.id.action_Save);


        return super.onCreateOptionsMenu(menu);
    }
}