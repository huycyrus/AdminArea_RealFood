package com.example.adminarea_realfood.Adapter;

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
import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.CuaHang;
import com.example.adminarea_realfood.R;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

import gr.escsoft.michaelprimez.searchablespinner.interfaces.ISpinnerSelectedView;

public class CuaHang_Spinner_Adapter extends ArrayAdapter implements Filterable, ISpinnerSelectedView{

    ArrayList<CuaHang> objects;
    ArrayList<CuaHang> source;
    Context context;
    int resource;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    public CuaHang_Spinner_Adapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull ArrayList<CuaHang> objects) {
        super(context, resource, textViewResourceId, objects);
        this.objects = objects;
        this.context = context;
        this.resource = resource;
        this.source = objects;
    }

    @Nullable
    @Override
    public CuaHang getItem(int position) {
        return objects.get(position);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initVeiw(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable  View convertView, @NonNull  ViewGroup parent) {
        return initVeiw(position, convertView, parent);
    }
    private View initVeiw(int position,   View convertView,   ViewGroup parent)
    {
        convertView  = LayoutInflater.from(context).inflate(resource,null);
        CuaHang cuaHang = objects.get(position);

        TextView tvTenCuaHang = convertView.findViewById(R.id.tvTenCuaHang);
        TextView tvDiaChi = convertView.findViewById(R.id.tvDiaChi);
        TextView tvSoDienThoai = convertView.findViewById(R.id.tvSoDienThoai);
        ImageView ivCuaHang = convertView.findViewById(R.id.iv_logo);

        tvTenCuaHang.setText(cuaHang.getTenCuaHang());
        tvDiaChi.setText(cuaHang.getDiaChi());
        tvSoDienThoai.setText(cuaHang.getSoDienThoai());

        firebase_manager.storageRef.child("CuaHang").child(cuaHang.getIDCuaHang()).child("Avatar").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    Glide.with(context).load(uri).into(ivCuaHang);
                } catch (Exception ex) {
                }
            }
        });
        return convertView;

    }



    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    objects = source;
                } else {
                    ArrayList<CuaHang> list = new ArrayList<>();
                    for (CuaHang cuaHang : source) {
                        if (cuaHang.getTenCuaHang().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(cuaHang);
                        }
                    }
                    objects = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = objects;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                objects = (ArrayList<CuaHang>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public View getNoSelectionView() {
        return null;
    }

    @Override
    public View getSelectedView(int position) {
        View  convertView  = LayoutInflater.from(context).inflate(resource,null);
        CuaHang cuaHang = objects.get(position);

        TextView tvTenCuaHang = convertView.findViewById(R.id.tvTenCuaHang);
        TextView tvDiaChi = convertView.findViewById(R.id.tvDiaChi);
        TextView tvSoDienThoai = convertView.findViewById(R.id.tvSoDienThoai);
        ImageView ivCuaHang = convertView.findViewById(R.id.iv_logo);

        tvTenCuaHang.setText(cuaHang.getTenCuaHang());
        tvDiaChi.setText(cuaHang.getDiaChi());
        tvSoDienThoai.setText(cuaHang.getSoDienThoai());

        firebase_manager.storageRef.child("CuaHang").child(cuaHang.getIDCuaHang()).child("Avatar").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    Glide.with(context).load(uri).into(ivCuaHang);
                } catch (Exception ex) {
                }
            }
        });
        return convertView;
    }

}
