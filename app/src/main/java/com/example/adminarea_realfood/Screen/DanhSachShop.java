package com.example.adminarea_realfood.Screen;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
}