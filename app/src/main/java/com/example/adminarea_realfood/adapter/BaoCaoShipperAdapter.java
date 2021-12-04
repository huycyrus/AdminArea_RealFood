package com.example.adminarea_realfood.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.BaoCaoShipper;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.Screen.XuLyShipper;
import com.example.adminarea_realfood.SetOnLongClick;
import com.example.adminarea_realfood.TrangThai.TrangThaiBaoCao;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BaoCaoShipperAdapter extends RecyclerView.Adapter<BaoCaoShipperAdapter.MyViewHolder>{
    private Activity context;
    private int resource;
    private ArrayList<BaoCaoShipper> arrayList;
    public SetOnLongClick setOnLongClick;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();


    public SetOnLongClick getSetOnLongClick() {
        return setOnLongClick;
    }

    public void setSetOnLongClick(SetOnLongClick setOnLongClick) {
        this.setOnLongClick = setOnLongClick;
    }

    Firebase_Manager firebase_manager = new Firebase_Manager();

    public BaoCaoShipperAdapter(Activity context, int resource, ArrayList<BaoCaoShipper> arrayList) {
        this.context = context;
        this.resource = resource;
        this.arrayList = arrayList;
    }

    @NotNull
    @Override
    public BaoCaoShipperAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.baocaoshipper_item, parent,false);
        return new BaoCaoShipperAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        BaoCaoShipper baoCaoShipper = arrayList.get(position);

        holder.tvTieuDe.setText(baoCaoShipper.getTieuDe());
        holder.tvNoiDung.setText(baoCaoShipper.getNoiDung());
        Log.d("Thông báo",baoCaoShipper.getNoiDung());

        storageReference.child("CuaHang").child(baoCaoShipper.getIdCuaHang()).child("Avatar").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context)
                        .load(uri.toString())
                        .into(holder.imageView);
                holder.progressBar.setVisibility(View.GONE);
            }
        });

        Date now = new Date();
        if (baoCaoShipper.getNgayBaoCao().getDate() == now.getDate())
        {

            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
            holder.tvThoiGian.setText(formatter.format(baoCaoShipper.getNgayBaoCao().getTime()));

        }

        if (baoCaoShipper.getTrangThaiBaoCao()== TrangThaiBaoCao.DaXem)
        {
            holder.linearLayout.setBackgroundColor(Color.WHITE);
        }

        holder.btnXuLy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, XuLyShipper.class);
                Gson gson = new Gson();
                String data = gson.toJson(baoCaoShipper);
                intent.putExtra("BaoCaoShipper", data);
                context.startActivity(intent);
                baoCaoShipper.setTrangThaiBaoCao(TrangThaiBaoCao.DaXem);
                firebase_manager.Ghi_BaoCaoShipper(baoCaoShipper);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTieuDe;
        ImageView imageView;
        TextView tvNoiDung;
        TextView tvThoiGian;
        LinearLayout linearLayout;
        ProgressBar progressBar;
        Button btnXuLy;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_baocaoshipper);
            tvTieuDe = itemView.findViewById(R.id.tv_TieuDe_baocaoshipper);
            tvNoiDung = itemView.findViewById(R.id.tv_NoiDung_baocaoshipper);
            linearLayout = itemView.findViewById(R.id.lnLayout_baocaoshipper);
            tvThoiGian = itemView.findViewById(R.id.tv_ThoiGian_baocaoshipper);
            progressBar = itemView.findViewById(R.id.progessbar_baocaoshipper);
            btnXuLy = itemView.findViewById(R.id.btn_xuly_baocaoshipper);
        }
    }
}
