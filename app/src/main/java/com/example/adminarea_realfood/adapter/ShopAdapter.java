package com.example.adminarea_realfood.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.adminarea_realfood.Model.Shop;
import com.example.adminarea_realfood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.util.ArrayList;

public class ShopAdapter extends ArrayAdapter {

    Context context;
    int resource;
    ArrayList<Shop> shops;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public ShopAdapter(@NonNull Context context, int resource, ArrayList<Shop> shops) {
        super(context, resource, shops);
        this.context = context;
        this.resource = resource;
        this.shops = shops;
    }

    @Override
    public int getCount() {
        return shops.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(resource, null);

        TextView tvTenCuaHang = convertView.findViewById(R.id.tv_tencuahang_shop);
        TextView tvTenChu = convertView.findViewById(R.id.tv_tenchu_shop);
        TextView tvTrangThai = convertView.findViewById(R.id.tv_trangthai_shop);
        TextView tvSdt = convertView.findViewById(R.id.tv_sdt_shop);
        PowerSpinnerView snNoiDung = convertView.findViewById(R.id.sn_noidungvipham_shop);
        Button btnGui = convertView.findViewById(R.id.btn_gui_shop);
        ImageView ivImage = convertView.findViewById(R.id.image_profile_shop);

        Shop shop = shops.get(position);
        tvTenCuaHang.setText(shop.getTenCuaHang());
        tvTenChu.setText(shop.getChuSoHuu());
        tvSdt.setText(shop.getSoDienThoai());
        storageReference.child("Cuahang").child(shop.getIDCuaHang()).child("avatar").getDownloadUrl(  ).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Glide.with(context)
                        .load(task.getResult().toString())
                        .into(ivImage);
            }
        });

        return convertView;
    }
}
