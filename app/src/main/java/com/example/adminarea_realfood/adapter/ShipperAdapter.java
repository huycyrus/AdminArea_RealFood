package com.example.adminarea_realfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.adminarea_realfood.Model.Shipper;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.Screen.ThongTinChiTietShipper;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ShipperAdapter extends ArrayAdapter implements Filterable {

    Context context;
    int resource;
    ArrayList<Shipper> data;
    ArrayList<Shipper> data1;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public ShipperAdapter(@NonNull Context context, int resource, ArrayList<Shipper> data) {
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



        TextView tvHoten = convertView.findViewById(R.id.tv_hovaten);
        TextView tvTrangthai = convertView.findViewById(R.id.tv_trangthai);
        TextView tvSdt = convertView.findViewById(R.id.tv_sdt);
        TextView tvMaxe = convertView.findViewById(R.id.tv_maxe);
        EditText edtNoidungvipham = convertView.findViewById(R.id.edt_noidungvipham);
        Button btnGui = convertView.findViewById(R.id.btn_gui);
        ImageView ivImage = convertView.findViewById(R.id.image_profile);

        Shipper shipper = data.get(position);
        tvHoten.setText(shipper.getHoVaTen());
        tvTrangthai.setText(shipper.getTrangThaiHoatDong());
        tvSdt.setText(shipper.getSoDienThoai());
        tvMaxe.setText(shipper.getMaSoXe());
        storageReference.child("Shipper").child(shipper.getiDShipper()).child("avatar").getDownloadUrl(  ).addOnSuccessListener(new OnSuccessListener<Uri>() {
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
                Intent intent = new Intent(getContext(), ThongTinChiTietShipper.class);
                Gson gson = new Gson();
                String data = gson.toJson(shipper);
                intent.putExtra("Shipper", data);
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
                    data = data1;
                }else {
                    List<Shipper> list = new ArrayList<>();
                    for (Shipper shipper : data1){
                        if(shipper.getHoVaTen().toLowerCase().contains(keyWord.toLowerCase())){
                            list.add(shipper);
                        }
                    }
                    data = (ArrayList<Shipper>) list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = data;
                return filterResults    ;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                data = (ArrayList<Shipper>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
