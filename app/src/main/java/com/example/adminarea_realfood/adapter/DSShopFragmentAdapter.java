package com.example.adminarea_realfood.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.adminarea_realfood.Fragment.DSChuaKichHoat_fragment;
import com.example.adminarea_realfood.Fragment.DSDaKichHoat_fragment;
import com.example.adminarea_realfood.Model.Shop;

public class DSShopFragmentAdapter extends FragmentStateAdapter {

    Shop shops;

    public DSShopFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position)
        {
            case 1 :
                return new DSChuaKichHoat_fragment();
        }

        return new DSDaKichHoat_fragment(shops);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
