package com.example.adminarea_realfood.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.adminarea_realfood.Model.LoaiSanPham;
import com.example.adminarea_realfood.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class LoaiSanPhamAdapter extends ArrayAdapter {

    Context context;
    int resource;
    ArrayList<LoaiSanPham> data;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public LoaiSanPhamAdapter(@NonNull Context context, int resource, ArrayList<LoaiSanPham> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(resource, null);

        TextView tvStt =  convertView.findViewById(R.id.tv_soTT);
        TextView tvTenloai =  convertView.findViewById(R.id.tv_tenLoai);
        ImageView ivImage = convertView.findViewById(R.id.image_loaisanpham);

        LoaiSanPham loaiSanPham = data.get(position);
        tvStt.setText(loaiSanPham.getsTT());
        tvTenloai.setText(loaiSanPham.getTenLoai());
        storageReference.child("LoaiSanPham").child(loaiSanPham.getiDLoai()).child("Loại sản phẩm").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
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
