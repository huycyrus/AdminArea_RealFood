package com.example.adminarea_realfood.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.adminarea_realfood.Model.LoaiSanPham;
import com.example.adminarea_realfood.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class LoaiSanPhamAdapter extends ArrayAdapter implements Filterable {

    Context context;
    int resource;
    ArrayList<LoaiSanPham> data;
    ArrayList<LoaiSanPham> data1;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public LoaiSanPhamAdapter(@NonNull Context context, int resource, ArrayList<LoaiSanPham> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
        this.data1 = data;
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
        storageReference.child("LoaiSanPham").child(loaiSanPham.getiDLoai()).child("Loại sản phẩm").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context)
                        .load(uri.toString())
                        .into(ivImage);
            }
        });

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String keyWord = constraint.toString();
                if(keyWord.isEmpty()){
                    data = data1;
                }else {
                    ArrayList<LoaiSanPham> list = new ArrayList<>();
                    for (LoaiSanPham loaiSanPham : data1){
                        if(loaiSanPham.getTenLoai().toLowerCase().contains(keyWord.toLowerCase())){
                            list.add(loaiSanPham);
                        }
                    }
                    data = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = data;
                return filterResults    ;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                data = (ArrayList<LoaiSanPham>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
