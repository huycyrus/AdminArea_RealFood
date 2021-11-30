package com.example.adminarea_realfood.adapter;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.CuaHang;
import com.example.adminarea_realfood.Model.DonHang;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.TrangThai.TrangThaiDonHang;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CuaHangAdapter extends RecyclerView.Adapter<CuaHangAdapter.MyViewHolder> {
    Activity context;
    int resrouce;
    ArrayList<CuaHang> cuaHangs = new ArrayList<>();
    Firebase_Manager firebase_manager = new Firebase_Manager();

    public CuaHangAdapter(Activity context, int resrouce, ArrayList<CuaHang> cuaHangs) {
        this.context = context;
        this.resrouce = resrouce;
        this.cuaHangs = cuaHangs;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        CardView linearLayout = (CardView) context.getLayoutInflater().inflate(viewType, parent, false);
        return new MyViewHolder(linearLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        CuaHang cuaHang = cuaHangs.get(position);
        if (cuaHang == null) {
            return;
        }
        holder.tvTenCuaHang.setText(cuaHang.getTenCuaHang());
        firebase_manager.mDatabase.child("SanPham").orderByChild("idcuaHang").equalTo(cuaHang.getIDCuaHang()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                holder.tvTongSanPham.setText(snapshot.getChildrenCount() + "");
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        firebase_manager.storageRef.child("CuaHang").child(cuaHang.getIDCuaHang()).child("Avatar").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    Glide.with(context).load(uri).into(holder.ivCuaHang);
                } catch (Exception ex) {
                }
            }
        });
        firebase_manager.mDatabase.child("DonHang").orderByChild("idcuaHang").equalTo(cuaHang.getIDCuaHang()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                int tong = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DonHang donHang = dataSnapshot.getValue(DonHang.class);
                    if (donHang.getTrangThai() == TrangThaiDonHang.Shipper_GiaoThanhCong ||
                            donHang.getTrangThai() == TrangThaiDonHang.ChoShopXacNhan_Tien ||
                            donHang.getTrangThai() == TrangThaiDonHang.Shipper_DaChuyenTien) {
                        tong += donHang.getTongTien();
                    }

                }
                holder.tvTongDoanhThu.setText(tong + " VNĐ");
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return resrouce;
    }

    @Override
    public int getItemCount() {
        return cuaHangs.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCuaHang;
        TextView tvTenCuaHang, tvTongSanPham, tvTongDoanhThu;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ivCuaHang = itemView.findViewById(R.id.ivCuaHang);
            tvTenCuaHang = itemView.findViewById(R.id.tvTenCuaHang);
            tvTongSanPham = itemView.findViewById(R.id.tvTongSanPham);
            tvTongDoanhThu = itemView.findViewById(R.id.tvTongDoanhThu);
        }
    }
}
