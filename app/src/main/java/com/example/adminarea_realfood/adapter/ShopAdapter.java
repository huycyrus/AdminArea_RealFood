package com.example.adminarea_realfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.developer.kalert.KAlertDialog;
import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Model.CuaHang;
import com.example.adminarea_realfood.Model.TaiKhoanNganHang;
import com.example.adminarea_realfood.Model.ThongBao;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.Screen.ThongTinChiTietShop;
import com.example.adminarea_realfood.TrangThai.TrangThaiCuaHang;
import com.example.adminarea_realfood.TrangThai.TrangThaiThongBao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ShopAdapter extends ArrayAdapter implements Filterable {

    Context context;
    int resource;
    ArrayList<CuaHang> cuaHangs;
    ArrayList<CuaHang> cuaHangs01;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    Firebase_Manager firebase_manager = new Firebase_Manager();


    public ShopAdapter(@NonNull Context context, int resource, ArrayList<CuaHang> cuaHangs) {
        super(context, resource, cuaHangs);
        this.context = context;
        this.resource = resource;
        this.cuaHangs = cuaHangs;
        this.cuaHangs01 = cuaHangs;
    }


    @Override
    public int getCount() {
        return cuaHangs.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(resource, null);

        TextView tvTenCuaHang = convertView.findViewById(R.id.tv_tencuahang_shop);
        TextView tvTenChu = convertView.findViewById(R.id.tv_tenchu_shop);
        TextView tvTrangThai = convertView.findViewById(R.id.tv_trangthai_shop);
        TextView tvSdt = convertView.findViewById(R.id.tv_sdt_shop);
        Button btnDongPhi = convertView.findViewById(R.id.btn_moidongphi_shop);
        ImageView ivImage = convertView.findViewById(R.id.image_profile_shop);

        CuaHang cuaHang = cuaHangs.get(position);
        firebase_manager.SetColorTrangThaiCuaHang(cuaHang.getTrangThaiCuaHang(), tvTrangThai);
        tvTenCuaHang.setText(cuaHang.getTenCuaHang());
        tvTenChu.setText(cuaHang.getChuSoHuu());
        tvSdt.setText(cuaHang.getSoDienThoai());
        tvTrangThai.setText(firebase_manager.GetStringTrangThaiCuaHang(cuaHang.getTrangThaiCuaHang()));
        if (cuaHang.getTrangThaiCuaHang() == TrangThaiCuaHang.DaKichHoat) {
            btnDongPhi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    firebase_manager.mDatabase.child("TaiKhoanNganHangAdmin").child("admin")
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    TaiKhoanNganHang taiKhoanNganHang = snapshot.getValue(TaiKhoanNganHang.class);
                                    String uuid_ThongBao = UUID.randomUUID().toString().replace("-", "");
                                    ThongBao thongBao = new ThongBao(uuid_ThongBao, "Vui lòng đóng tiền cho hệ thống để duy trì tài khoản, với số tiền là 99.999 VND vào số tài khoản :" + taiKhoanNganHang.getSoTaiKhoan() + ". Xin cảm ơn !!!", "Đóng phí duy trì", "", cuaHang.getIDCuaHang(),
                                            "", TrangThaiThongBao.ChuaXem, new Date());
                                    firebase_manager.Ghi_ThongBao_CuaHang(thongBao, cuaHang.getIDCuaHang()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                                            KAlertDialog kAlertDialog = new KAlertDialog(context, KAlertDialog.SUCCESS_TYPE).setContentText("Đã gửi thành công !");
                                            kAlertDialog.show();
                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                }
            });
        }


        storageReference.child("CuaHang").child(cuaHang.getIDCuaHang()).child("Avatar").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
                Intent intent = new Intent(getContext(), ThongTinChiTietShop.class);
                Gson gson = new Gson();
                String data = gson.toJson(cuaHang);
                intent.putExtra("CuaHang", data);
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
                if (keyWord.isEmpty()) {
                    cuaHangs = cuaHangs01;
                } else {
                    List<CuaHang> list = new ArrayList<>();
                    for (CuaHang cuaHang : cuaHangs01) {
                        if (cuaHang.getTenCuaHang().toLowerCase().contains(keyWord.toLowerCase())) {
                            list.add(cuaHang);
                        }
                    }
                    cuaHangs = (ArrayList<CuaHang>) list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = cuaHangs;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                cuaHangs = (ArrayList<CuaHang>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
