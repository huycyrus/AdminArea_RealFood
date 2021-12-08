package com.example.adminarea_realfood.Adapter;

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
import com.example.adminarea_realfood.Model.SanPham;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.Model.SetOnLongClick;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.NumberFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.MyViewHolder> {
    private Activity context;
    private int resource;
    private ArrayList<SanPham> arrayList;
    public SetOnLongClick setOnLongClick;

    public SetOnLongClick getSetOnLongClick() {
        return setOnLongClick;
    }

    public void setSetOnLongClick(SetOnLongClick setOnLongClick) {
        this.setOnLongClick = setOnLongClick;
    }

    StorageReference storageRef  = FirebaseStorage.getInstance().getReference();


    public SanPhamAdapter(Activity context, int resource, ArrayList<SanPham> arrayList) {
        this.context = context;
        this.resource = resource;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        LinearLayout cardView = (LinearLayout) layoutInflater.inflate(viewType, parent, false);
        return new MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (arrayList.size()>0)
        {
            SanPham sanPham = arrayList.get(position);

            NumberFormat format = NumberFormat.getCurrencyInstance();
            format.setMaximumFractionDigits(0);

            holder.tvTenSanPham.setText(sanPham.getTenSanPham());
            float parseFloat = Float.parseFloat(sanPham.getGia());
            String price =format.format(parseFloat);
            holder.tvGia.setText(price);
            storageRef.child("SanPham").child(sanPham.getIDCuaHang()).child(sanPham.getIDSanPham()).child(sanPham.getImages().get(0)).
                    getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(context)
                            .load(uri.toString())
                            .into(holder.ivAnhSP);
                    holder.pdLoad.setVisibility(View.GONE);
                    Log.d("link",uri.toString());

                }
            });

        }

    }

    //Hàm để get layout type
    @Override
    public int getItemViewType(int position) {
        //1 list có 2 view
//        if(position%2==0)
//        {
//            ID layout A
//        }
//        else
//        {
//            ID layout B
//        }
        return resource;
    }

    //trả về số phần tử
    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    //Define RecylerVeiw Holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenSanPham;
        TextView tvGia;
        ImageView ivAnhSP;
        ProgressBar pdLoad;
        public MyViewHolder(View itemView) {
            super(itemView);
            ivAnhSP = itemView.findViewById(R.id.iv_anh_item_sp);
            tvTenSanPham = itemView.findViewById(R.id.tv_tensanpham_sp);
            tvGia = itemView.findViewById(R.id.tv_gia_sp);
            pdLoad = itemView.findViewById(R.id.pb_load_sp);
        }
    }
}
