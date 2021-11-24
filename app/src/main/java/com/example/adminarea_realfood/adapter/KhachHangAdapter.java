package com.example.adminarea_realfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.adminarea_realfood.Model.KhachHang;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.Screen.ThongTinChiTietKhachHang;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class KhachHangAdapter extends ArrayAdapter {

    Context context;
    int resource;
    ArrayList<KhachHang> khachHangs;
    ArrayList<KhachHang> khachHangs1;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public KhachHangAdapter(@NonNull Context context, int resource, ArrayList<KhachHang> khachHangs) {
        super(context, resource, khachHangs);
        this.context = context;
        this.resource = resource;
        this.khachHangs = khachHangs;
        this.khachHangs1 = khachHangs;

    }

    @Override
    public int getCount() {
        return khachHangs.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(resource, null);

        CircleImageView civImageKH = convertView.findViewById(R.id.image_danhsachkhachhang);
        TextView tvTenKH = convertView.findViewById(R.id.tv_tenkhachhang_danhsachkhachhang);
        TextView tvSdtKH = convertView.findViewById(R.id.tv_sdt_danhsachkhachhang);
        TextView tvEmailKH = convertView.findViewById(R.id.tv_email_danhsachkhachhang);

        KhachHang khachHang = khachHangs.get(position);
        tvTenKH.setText(khachHang.getTenKhachHang());
        tvSdtKH.setText(khachHang.getSoDienThoai());
        tvEmailKH.setText(khachHang.getEmail());
        storageReference.child("KhachHang").child(khachHang.getIDKhachHang()).child("AvatarKhachHang").getDownloadUrl(  ).addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context)
                        .load(uri.toString())
                        .into(civImageKH);
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ThongTinChiTietKhachHang.class);
                Gson gson = new Gson();
                String data = gson.toJson(khachHang);
                intent.putExtra("KhachHang", data);
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
                    khachHangs = khachHangs1;
                }else {
                    List<KhachHang> list = new ArrayList<>();
                    for (KhachHang khachHang : khachHangs1){
                        if(khachHang.getTenKhachHang().toLowerCase().contains(keyWord.toLowerCase())){
                            list.add(khachHang);
                        }
                    }
                    khachHangs = (ArrayList<KhachHang>) list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = khachHangs;
                return filterResults    ;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                khachHangs = (ArrayList<KhachHang>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
