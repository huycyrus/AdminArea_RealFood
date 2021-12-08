package com.example.adminarea_realfood.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.CuaHang;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.Screen.ThongKe_CuaHang_Activity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Shop_ThongKe_Adapter extends RecyclerView.Adapter<Shop_ThongKe_Adapter.MyViewHolder>implements Filterable {
    private Activity context;
    private int resource;
    private ArrayList<CuaHang> arrayList;
    private ArrayList<CuaHang> source;
    Firebase_Manager firebase_manager = new Firebase_Manager();
    StorageReference storageRef  = FirebaseStorage.getInstance().getReference();
    public Shop_ThongKe_Adapter(Activity context, int resource, ArrayList<CuaHang> arrayList) {
        this.context = context;
        this.resource = resource;
        this.arrayList = arrayList;
        this.source = arrayList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        CardView cardView = (CardView) layoutInflater.inflate(viewType, parent, false);
        return new MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (arrayList.size()>0)
        {
            CuaHang cuaHang = arrayList.get(position);
            holder.tv_trangthai_shop.setText(firebase_manager.GetStringTrangThaiCuaHang(cuaHang.getTrangThaiCuaHang()));
            firebase_manager.SetColorTrangThaiCuaHang(cuaHang.getTrangThaiCuaHang(),holder.tv_trangthai_shop);
            holder.tvTenCuaHang.setText(cuaHang.getTenCuaHang());
            holder.tvSoDienThoai.setText(cuaHang.getSoDienThoai());
            holder.tvTenCuaHang.setText(cuaHang.getTenCuaHang());
            firebase_manager.mDatabase.child("SanPham").orderByChild("idcuaHang").equalTo(cuaHang.getIDCuaHang()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    holder.tvSoSanPham.setText(snapshot.getChildrenCount()+"");
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

            firebase_manager.mDatabase.child("DanhGia").orderByChild("idcuaHang").equalTo(cuaHang.getIDCuaHang()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    holder.tvLuotDanhGia.setText(snapshot.getChildrenCount()+"");
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

            firebase_manager.storageRef.child("CuaHang").child(cuaHang.getIDCuaHang()).child("Avatar").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(context)
                            .load(uri.toString())
                            .into(holder.image_profile_shop);
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ThongKe_CuaHang_Activity.class);
                    Gson gson = new Gson();
                    String data = gson.toJson(cuaHang);
                    intent.putExtra("CuaHang", data);
                    context.startActivity(intent);
                }
            });
        }

    }

    //Hàm để get layout type
    @Override
    public int getItemViewType(int position) {
        return resource;
    }

    //trả về số phần tử
    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    //Define RecylerVeiw Holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTenCuaHang,tvSoSanPham,tvSoDienThoai,tvLuotDanhGia,tv_trangthai_shop;
        ImageView image_profile_shop;
        ProgressBar pdLoad;
        public MyViewHolder(View itemView) {
            super(itemView);
            image_profile_shop = itemView.findViewById(R.id.image_profile_shop);
            tvTenCuaHang = itemView.findViewById(R.id.tv_tencuahang_shop);
            tvSoSanPham = itemView.findViewById(R.id.txtSoSanPham);
            tvSoDienThoai = itemView.findViewById(R.id.tv_sdt_shop);
            tvLuotDanhGia = itemView.findViewById(R.id.txtLuotDanhGia);
            tv_trangthai_shop = itemView.findViewById(R.id.tv_trangthai_shop);
        }
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String keyWord = constraint.toString();
                if(keyWord.isEmpty()){
                    arrayList = source;
                }else {
                    List<CuaHang> list = new ArrayList<>();
                    for (CuaHang cuaHang : arrayList){
                        if(cuaHang.getTenCuaHang().toLowerCase().contains(keyWord.toLowerCase())){
                            list.add(cuaHang);
                        }
                    }
                    arrayList = (ArrayList<CuaHang>) list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = arrayList;
                return filterResults    ;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                arrayList = (ArrayList<CuaHang>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
