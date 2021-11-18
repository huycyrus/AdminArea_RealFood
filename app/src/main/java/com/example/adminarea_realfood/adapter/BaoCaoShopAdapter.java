package com.example.adminarea_realfood.adapter;

import android.app.Activity;
import android.net.Uri;
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
import com.example.adminarea_realfood.Model.BaoCaoShop;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.SetOnLongClick;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BaoCaoShopAdapter extends RecyclerView.Adapter<BaoCaoShopAdapter.MyViewHolder> {
    private Activity context;
    private int resource;
    private ArrayList<BaoCaoShop> arrayList;
    public SetOnLongClick setOnLongClick;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();


    public SetOnLongClick getSetOnLongClick() {
        return setOnLongClick;
    }

    public void setSetOnLongClick(SetOnLongClick setOnLongClick) {
        this.setOnLongClick = setOnLongClick;
    }

    Firebase_Manager firebase_manager = new Firebase_Manager();

    public BaoCaoShopAdapter(Activity context, int resource, ArrayList<BaoCaoShop> arrayList) {
        this.context = context;
        this.resource = resource;
        this.arrayList = arrayList;
    }

    @NotNull
    @Override
    public BaoCaoShopAdapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.baocaoshop_item, parent,false);
        return new BaoCaoShopAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        BaoCaoShop baoCaoShop = arrayList.get(position);

        holder.tvTieuDe.setText(baoCaoShop.getTieuDe());
        holder.tvNoiDung.setText(baoCaoShop.getLyDo());
        Log.d("Báo cáo : ",baoCaoShop.getLyDo());

        storageReference.child("KhachHang").child(baoCaoShop.getIDKhachHang()).child("AvatarKhachHang").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context)
                        .load(uri.toString())
                        .into(holder.imageView);
                holder.progressBar.setVisibility(View.GONE);
            }
        });

        Date now = new Date();
        if (baoCaoShop.getNgayBaoCao().getDate() == now.getDate())
        {

            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
            holder.tvThoiGian.setText(formatter.format(baoCaoShop.getNgayBaoCao().getTime()));

        }
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
            imageView = itemView.findViewById(R.id.image_baocaoshop);
            tvTieuDe = itemView.findViewById(R.id.tv_TieuDe_baocaoshop);
            tvNoiDung = itemView.findViewById(R.id.tv_NoiDung_baocaoshop);
            linearLayout = itemView.findViewById(R.id.lnLayout_baocaoshop);
            tvThoiGian = itemView.findViewById(R.id.tv_ThoiGian_baocaoshop);
            progressBar = itemView.findViewById(R.id.progessbar_baocaoshop);
        }
    }
}
