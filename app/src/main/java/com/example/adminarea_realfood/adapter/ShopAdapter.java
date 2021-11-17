package com.example.adminarea_realfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.adminarea_realfood.Model.CuaHang;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.Screen.ThongTinChiTietShop;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ShopAdapter extends ArrayAdapter implements Filterable {

    Context context;
    int resource;
    ArrayList<CuaHang> cuaHangs;
    ArrayList<CuaHang> cuaHangs01;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public ShopAdapter(@NonNull Context context, int resource, ArrayList<CuaHang> cuaHangs) {
        super(context, resource, cuaHangs);
        this.context = context;
        this.resource = resource;
        this.cuaHangs = cuaHangs;
        this.cuaHangs01 = cuaHangs;
    }


    @Override
    public int getCount() {
        return cuaHangs.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(resource, null);

        TextView tvTenCuaHang = convertView.findViewById(R.id.tv_tencuahang_shop);
        TextView tvTenChu = convertView.findViewById(R.id.tv_tenchu_shop);
        TextView tvTrangThai = convertView.findViewById(R.id.tv_trangthai_shop);
        TextView tvSdt = convertView.findViewById(R.id.tv_sdt_shop);
        Spinner snNoiDung = convertView.findViewById(R.id.sn_noidungvipham_shop);
        Button btnGui = convertView.findViewById(R.id.btn_gui_shop);
        ImageView ivImage = convertView.findViewById(R.id.image_profile_shop);

        CuaHang cuaHang = cuaHangs.get(position);
        tvTenCuaHang.setText(cuaHang.getTenCuaHang());
        tvTenChu.setText(cuaHang.getChuSoHuu());
        tvSdt.setText(cuaHang.getSoDienThoai());



        storageReference.child("CuaHang").child(cuaHang.getIDCuaHang()).child("Avatar").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context)
                        .load(uri.toString())
                        .into(ivImage);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ThongTinChiTietShop.class);
                Gson gson = new Gson();
                String data = gson.toJson(cuaHang);
                intent.putExtra("CuaHang", data);
                getContext().startActivity(intent);
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
                    cuaHangs = cuaHangs01;
                }else {
                    List<CuaHang> list = new ArrayList<>();
                    for (CuaHang cuaHang : cuaHangs01){
                        if(cuaHang.getTenCuaHang().toLowerCase().contains(keyWord.toLowerCase())){
                            list.add(cuaHang);
                        }
                    }
                    cuaHangs = (ArrayList<CuaHang>) list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = cuaHangs;
                return filterResults    ;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                cuaHangs = (ArrayList<CuaHang>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
