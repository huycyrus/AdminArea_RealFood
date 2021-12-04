package com.example.adminarea_realfood.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.CuaHang;
import com.example.adminarea_realfood.Model.KhachHang;
import com.example.adminarea_realfood.Model.ThongBao;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.Screen.XuLyShipper;
import com.example.adminarea_realfood.Screen.XuLyShop;
import com.example.adminarea_realfood.SetOnLongClick;
import com.example.adminarea_realfood.TrangThai.TrangThaiBaoCao;
import com.example.adminarea_realfood.TrangThai.TrangThaiThongBao;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ThongBaoAdapter  extends RecyclerView.Adapter<ThongBaoAdapter.MyViewHolder>{

    private Activity context;
    private int resource;
    private ArrayList<ThongBao> arrayList;
    public SetOnLongClick setOnLongClick;
    CuaHang cuaHang = new CuaHang();
    KhachHang khachHang = new KhachHang();

    public SetOnLongClick getSetOnLongClick() {
        return setOnLongClick;
    }

    public void setSetOnLongClick(SetOnLongClick setOnLongClick) {
        this.setOnLongClick = setOnLongClick;
    }

    Firebase_Manager firebase_manager = new Firebase_Manager();
    public ThongBaoAdapter(Activity context, int resource, ArrayList<ThongBao> arrayList) {
        this.context = context;
        this.resource = resource;
        this.arrayList = arrayList;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.thongbao_item, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        ThongBao thongBao = arrayList.get(position);

        holder.tvTieuDe.setText(thongBao.getTieuDe());
        holder.tvNoiDung.setText(thongBao.getNoiDung());
        Log.d("Thông báo",thongBao.getNoiDung());
        if (thongBao.getTrangThaiThongBao()== TrangThaiThongBao.DaXem)
        {
            holder.linearLayout.setBackgroundColor(Color.WHITE);
        }

        if(holder.imageView == null){
            holder.progressBar.setVisibility(View.VISIBLE);
        }else {
            Glide.with(context)
                    .load(thongBao.getImage())
                    .into(holder.imageView);
            holder.progressBar.setVisibility(View.GONE);
        }

        Log.d("Uri", thongBao.getImage());
        Date now = new Date();
        if (thongBao.getDate().getDate()== now.getDate())
        {

            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
            holder.tvThoiGian.setText(formatter.format(thongBao.getDate().getTime()));

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (thongBao.getLoaiThongBao())
                {
                    case BaoCaoShipper:
                        if (thongBao.getBaoCaoShipper()!=null)
                        {
                            Intent intent = new Intent(context, XuLyShipper.class);
                            Gson gson = new Gson();
                            String data = gson.toJson(thongBao.getBaoCaoShipper());
                            intent.putExtra("BaoCaoShipper", data);
                            context.startActivity(intent);
                            thongBao.setTrangThaiThongBao(TrangThaiThongBao.DaXem);
                            firebase_manager.Ghi_ThongBao(thongBao);
                        }
                        break;
                    case BaoCaoCuaHang:
                        if (thongBao.getBaoCaoShop()!=null)
                        {
                            Intent intent = new Intent(context, XuLyShop.class);
                            Gson gson = new Gson();
                            String data = gson.toJson(thongBao.getBaoCaoShop());
                            intent.putExtra("BaoCao", data);
                            context.startActivity(intent);
                            thongBao.setTrangThaiThongBao(TrangThaiThongBao.DaXem);
                            firebase_manager.Ghi_ThongBao(thongBao);
                        }
                        break;
                }
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
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_noti);
            tvTieuDe = itemView.findViewById(R.id.tv_TieuDe_ThongBao);
            tvNoiDung = itemView.findViewById(R.id.tv_NoiDung_ThongBao);
            linearLayout = itemView.findViewById(R.id.lnLayout);
            tvThoiGian = itemView.findViewById(R.id.tv_ThoiGian_ThongBao);
            progressBar = itemView.findViewById(R.id.progessbar);
        }
    }
}
