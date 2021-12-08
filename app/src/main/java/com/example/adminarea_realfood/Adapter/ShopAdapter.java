package com.example.adminarea_realfood.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.adminarea_realfood.Firebase_Manager;
import com.example.adminarea_realfood.Screen.GuiThongBaoActivity;
import com.example.adminarea_realfood.Model.CuaHang;
import com.example.adminarea_realfood.Model.DonHang;
import com.example.adminarea_realfood.Model.TaiKhoanNganHang;
import com.example.adminarea_realfood.R;
import com.example.adminarea_realfood.Screen.ThongTinChiTietShop;
import com.example.adminarea_realfood.Screen.ThanhToanActivity;
import com.example.adminarea_realfood.TrangThai.TrangThaiCuaHang;
import com.example.adminarea_realfood.TrangThai.TrangThaiDonHang;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.tapadoo.alerter.Alerter;

import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

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
        final String[] noiDung = {""};
        final int[] TYPE = {2};

        convertView = LayoutInflater.from(context).inflate(resource, null);
        NumberFormat formatter = new DecimalFormat("#0.0");
        TextView tvTenCuaHang = convertView.findViewById(R.id.tv_tencuahang_shop);
        TextView tvTenChu = convertView.findViewById(R.id.tv_tenchu_shop);
        TextView tvTrangThai = convertView.findViewById(R.id.tv_trangthai_shop);
        TextView tvSdt = convertView.findViewById(R.id.tv_sdt_shop);
        TextView tvTrangThai_dongtien = convertView.findViewById(R.id.tvTrangThai);
        TextView tvGhiChu = convertView.findViewById(R.id.tvGhiChu);
        ImageButton btnDongPhi = convertView.findViewById(R.id.btn_moidongphi_shop);
        ImageView ivImage = convertView.findViewById(R.id.image_profile_shop);
        CuaHang cuaHang = cuaHangs.get(position);
        tvTenCuaHang.setText(cuaHang.getTenCuaHang());
        tvTenChu.setText(cuaHang.getChuSoHuu());
        tvSdt.setText(cuaHang.getSoDienThoai());

        if (cuaHang.getTrangThaiCuaHang() != TrangThaiCuaHang.ChuaKichHoat) {
            if (cuaHang.getGhiChu()!=null)
            {
                tvGhiChu.setText(cuaHang.getGhiChu());
                tvGhiChu.setVisibility(View.VISIBLE);
            }
            else {
                tvGhiChu.setVisibility(View.GONE);
            }
            firebase_manager.SetColorTrangThaiCuaHang(cuaHang.getTrangThaiCuaHang(), tvTrangThai);
            tvTrangThai.setText(firebase_manager.GetStringTrangThaiCuaHang(cuaHang.getTrangThaiCuaHang()));
            btnDongPhi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(context, btnDongPhi);
                    popup.inflate(R.menu.popupmenu_cuahang);
                    Menu menu = popup.getMenu();
                    // com.android.internal.view.menu.MenuBuilder
                    Log.i("LOG_TAG", "Menu class: " + menu.getClass().getName());

                    // Register Menu Item Click event.
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {

                            switch (item.getItemId()) {
                                case R.id.idTaoHoaDon: {
                                    if (TYPE[0]==0)
                                    {
                                        Alerter.create((Activity) context)
                                                .setTitle("Thông báo")
                                                .setText("Cửa hàng chưa có đơn hàng mới nào")
                                                .setBackgroundColorRes(R.color.error_stroke_color)
                                                .setDuration(5000)// or setBackgroundColorInt(Color.CYAN)
                                                .show();
                                    }
                                    else if (TYPE[0]==1)
                                    {
                                        Alerter.create((Activity) context)
                                                .setTitle("Thông báo")
                                                .setText("Cửa hàng không có đơn hàng")
                                                .setBackgroundColorRes(R.color.error_stroke_color)
                                                .setDuration(5000)// or setBackgroundColorInt(Color.CYAN)
                                                .show();
                                    }
                                    else {
                                        Intent intent = new Intent(context, ThanhToanActivity.class);
                                        Gson gson = new Gson();
                                        String data = gson.toJson(cuaHang);
                                        intent.putExtra("cuaHang", data);
                                        context.startActivity(intent);
                                    }
                                    break;
                                }
                                case R.id.idMoiDongPhi: {
                                    if (TYPE[0]==0)
                                    {
                                        Alerter.create((Activity) context)
                                                .setTitle("Thông báo")
                                                .setText("Cửa hàng chưa có đơn hàng mới nào")
                                                .setBackgroundColorRes(R.color.error_stroke_color)
                                                .setDuration(5000)// or setBackgroundColorInt(Color.CYAN)
                                                .show();
                                    }
                                    else if (TYPE[0]==1)
                                    {
                                        Alerter.create((Activity) context)
                                                .setTitle("Thông báo")
                                                .setText("Cửa hàng không có đơn hàng")
                                                .setBackgroundColorRes(R.color.error_stroke_color)
                                                .setDuration(5000)// or setBackgroundColorInt(Color.CYAN)
                                                .show();
                                    }
                                    else {
                                        Intent intent = new Intent(context, GuiThongBaoActivity.class);
                                        Gson gson = new Gson();
                                        String data = gson.toJson(cuaHang);
                                        String nd = noiDung[0];
                                        intent.putExtra("noiDung", nd);
                                        intent.putExtra("cuaHang", data);
                                        context.startActivity(intent);
                                        break;
                                    }


                                }
                                case R.id.idGuiThongBao: {

                                    Intent intent = new Intent(context, GuiThongBaoActivity.class);
                                    Gson gson = new Gson();
                                    String data = gson.toJson(cuaHang);
                                    String nd = "";
                                    intent.putExtra("noiDung", nd);
                                    intent.putExtra("cuaHang", data);
                                    context.startActivity(intent);
                                    break;
                                }
                            }
                            return true;
                        }
                    });

                    // Show the PopupMenu.
                    popup.show();
                }
            });

            final int[] tong = new int[1];
            firebase_manager.mDatabase.child("DonHang").orderByChild("idcuaHang").equalTo(cuaHang.getIDCuaHang()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    tong[0] = 0;
                    if (snapshot.exists()) {
                        //Tổng =0
                        //nếu đơn hàng của cửa hàng có tồn tại thì check == true
                        ArrayList<DonHang> donHangs = new ArrayList<>();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            DonHang donHang = dataSnapshot.getValue(DonHang.class);
                            if (donHang.getTrangThai() == TrangThaiDonHang.Shipper_DaChuyenTien) {
                                //nếu chưa tạo hóa đơn
                                if (cuaHang.getNgayThanhToan() == null) {
                                    donHangs.add(donHang);
                                    tong[0] += donHang.getTongTien();
                                    //đã tạo hóa đơn thì lấy những đơn hàng bé hơn ngày thanh toán vừa rồi cảu cửa hàng
                                } else {
                                    Date date = new Date();
                                    if (donHang.getNgayGiaoHang() != null) {
                                        if (donHang.getNgayGiaoHang().after(cuaHang.getNgayThanhToan())) {
                                            donHangs.add(donHang);
                                            tong[0] += donHang.getTongTien();
                                        }
                                    }
                                }

                            }
                        }

                        Collections.sort(donHangs, new Comparator<DonHang>() {
                            @Override
                            public int compare(DonHang o1, DonHang o2) {
                                return o2.getNgayTao().compareTo(o1.getNgayTao());
                            }
                        });

                        firebase_manager.mDatabase.child("LoiNhuan").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                            @Override
                            public void onSuccess(DataSnapshot dataSnapshot) {
                                double tongtien = 0;
                                Long hoaHong = dataSnapshot.getValue(Long.class);
                                tongtien = tong[0] * ((double) hoaHong / 100);
                                if (tongtien==0)
                                {
                                    TYPE[0] = 0;
                                }
                                double finalTongtien = tongtien;
                                firebase_manager.mDatabase.child("TaiKhoanNganHangAdmin").child("admin")
                                        .addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                TaiKhoanNganHang taiKhoanNganHang = snapshot.getValue(TaiKhoanNganHang.class);
                                                noiDung[0] = "Xin chào cửa hàng " + cuaHang.getTenCuaHang() + "\n\n Vui lòng chuyển phí hoạt động " + formatter.format(finalTongtien) + "VND (" + hoaHong + "% trên mỗi đơn hàng) : \nVới cú pháp :\n\n ThanhToan " + formatter.format(finalTongtien) + " " + cuaHang.getEmail() +"\n\nĐến tài khoản \n\nSố tài khoản: "+taiKhoanNganHang.getIdTaiKhoan()+  "\nChi Nhánh: "+taiKhoanNganHang.getTenChiNhanh()+"\nNgân hàng: "+taiKhoanNganHang.getTenNganHang()+"\nChủ tài khoản: "+taiKhoanNganHang.getTenChuTaiKhoan()+  "\n\nNgày thông báo:  " + new Date().toString();

                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                            }
                        });
                        if (cuaHang.getNgayThanhToan() != null) {
                            long date = (new Date().getTime() - cuaHang.getNgayThanhToan().getTime()) / (24 * 3600 * 1000);
                            tvTrangThai_dongtien.setText("Chưa thanh toán trong vòng " + date + " ngày");
                            if (date < 30) {
                                tvTrangThai_dongtien.setText("Chưa thanh toán " + date + " ngày");
                                tvTrangThai_dongtien.setTextColor(Color.parseColor("#19AC3C"));
                            }
                            if (date > 30) {
                                tvTrangThai_dongtien.setText("Chưa thanh toán trong vòng " + date + " ngày");
                                tvTrangThai_dongtien.setTextColor(Color.parseColor("#BBBBBB"));

                            }
                            if (date == 0) {
                                tvTrangThai_dongtien.setText("Vừa thanh toán hôm nay");
                                tvTrangThai_dongtien.setTextColor(Color.parseColor("#17B2FF"));
                            }

                        } else {
                            if (donHangs.size() != 0) {
                                long noDay = (new Date().getTime() - donHangs.get(0).getNgayTao().getTime()) / (24 * 3600 * 1000);
                                if (noDay < 30) {
                                    tvTrangThai_dongtien.setText("Chưa thanh toán " + noDay + " ngày");
                                    tvTrangThai_dongtien.setTextColor(Color.parseColor("#19AC3C"));

                                }
                                if (noDay > 30) {
                                    tvTrangThai_dongtien.setTextColor(Color.parseColor("#BBBBBB"));
                                    tvTrangThai_dongtien.setText("Chưa thanh toán trong vòng " + noDay + " ngày");
                                }
                            } else {

                                tvTrangThai_dongtien.setText("Chưa có đơn hàng nào");
                                tvTrangThai_dongtien.setTextColor(Color.parseColor("#3A7394"));
                            }

                        }

                    } else {
                        tvTrangThai_dongtien.setText("Chưa có đơn hàng mới");
                        TYPE[0] = 1;
                        tvTrangThai_dongtien.setTextColor(Color.parseColor("#3A7394"));
                    }
                }

                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

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
