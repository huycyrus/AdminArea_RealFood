package com.example.adminarea_realfood.Adapter;

import android.app.Activity;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.developer.kalert.KAlertDialog;
import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.SuKien;
import com.example.adminarea_realfood.R;
import com.google.android.gms.tasks.OnSuccessListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SuKienAdapter extends RecyclerView.Adapter<SuKienAdapter.MyViewHolder> {
    Activity context;
    int resource;
    ArrayList<SuKien> suKiens;
    ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    Firebase_Manager firebase_manager = new Firebase_Manager();
    KAlertDialog kAlertDialog;

    public SuKienAdapter(Activity context, int resource, ArrayList<SuKien> suKiens) {
        this.context = context;
        this.resource = resource;
        this.suKiens = suKiens;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = context.getLayoutInflater().inflate(viewType, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        SuKien suKien = suKiens.get(position);
        if (suKien == null) {
            return;
        }
        viewBinderHelper.bind(holder.swipeRevealLayout, suKien.getIdSuKien());
        firebase_manager.storageRef.child("SuKien").child(suKien.getIdSuKien()).child("ImageSuKien").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into(holder.ivSuKien);
            }
        });
        holder.tvTenSuKien.setText(suKien.getTenSuKien());
        holder.lnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kAlertDialog = new KAlertDialog(context, KAlertDialog.SUCCESS_TYPE).setTitleText("Thông báo")
                        .setContentText("Bạn có muốn xóa không")
                        .setConfirmText("Có")
                        .setCancelText("Không")
                        .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog kAlertDialog) {
                                firebase_manager.mDatabase.child("SuKien").child(suKien.getIdSuKien()).removeValue();
                                firebase_manager.storageRef.child("SuKien").child(suKien.getIdSuKien()).child("ImageSuKien").delete();
                                kAlertDialog.dismiss();
                            }
                        }).setCancelClickListener(new KAlertDialog.KAlertClickListener() {
                            @Override
                            public void onClick(KAlertDialog kAlertDialog) {
                                kAlertDialog.dismiss();
                            }
                        });
                kAlertDialog.show();
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return resource;
    }

    @Override
    public int getItemCount() {
        return suKiens.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        SwipeRevealLayout swipeRevealLayout;
        LinearLayout lnDelete;
        ImageView ivSuKien;
        TextView tvTenSuKien;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            swipeRevealLayout = itemView.findViewById(R.id.swipeRevealLayout);
            lnDelete = itemView.findViewById(R.id.lnDelete);
            ivSuKien = itemView.findViewById(R.id.ivSuKien);
            tvTenSuKien = itemView.findViewById(R.id.tvTenSuKien);
        }
    }
}
