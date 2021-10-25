package com.example.adminarea_realfood.Screen;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.adapter.DSShopFragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class DanhSachShop extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 pager2;
    DSShopFragmentAdapter dsShopFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danhsachshop_activity);
        setControl();
        setEvent();
    }

    private void setEvent() {

        FragmentManager fm = getSupportFragmentManager();
        dsShopFragmentAdapter = new DSShopFragmentAdapter(fm, getLifecycle());
        pager2.setAdapter(dsShopFragmentAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("Đã kích hoạt"));
        tabLayout.addTab(tabLayout.newTab().setText("Chưa kích hoạt"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
    }

    private void setControl() {
        tabLayout = findViewById(R.id.tab_layout);
        pager2 = findViewById(R.id.view_pager2);
    }

}