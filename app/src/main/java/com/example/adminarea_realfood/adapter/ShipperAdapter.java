package com.example.adminarea_realfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.adminarea_realfood.Model.Shipper;
import com.example.adminarea_realfood.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ShipperAdapter extends ArrayAdapter {

    Context context;
    int resource;
    ArrayList<Shipper> data;

    public ShipperAdapter(@NonNull Context context, int resource, ArrayList<Shipper> data) {
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

        return convertView;
    }
}
