package com.example.adminarea_realfood;

import android.graphics.Color;
import android.net.Uri;
import android.widget.TextView;

import com.example.adminarea_realfood.Model.Admin;
import com.example.adminarea_realfood.Model.BaoCaoShipper;
import com.example.adminarea_realfood.Model.BaoCaoShop;
import com.example.adminarea_realfood.Model.CuaHang;
import com.example.adminarea_realfood.Model.LoaiSanPham;
import com.example.adminarea_realfood.Model.SanPham;
import com.example.adminarea_realfood.Model.Shipper;
import com.example.adminarea_realfood.Model.TaiKhoanNganHang;
import com.example.adminarea_realfood.Model.ThongBao;
import com.example.adminarea_realfood.TrangThai.LoaiThongBao;
import com.example.adminarea_realfood.TrangThai.TrangThaiCuaHang;
import com.example.adminarea_realfood.TrangThai.TrangThaiShipper;
import com.example.adminarea_realfood.TrangThai.TrangThaiThanhToan;
import com.example.adminarea_realfood.TrangThai.TrangThaiThongBao;
import com.example.adminarea_realfood.Adapter.SanPhamAdapter;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class    Firebase_Manager {
    public  DatabaseReference mDatabase ;
    public StorageReference storageRef ;
    public FirebaseAuth auth;

    public Firebase_Manager() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        storageRef = FirebaseStorage.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
    }

    public Task<Void> Ghi_Shipper (Shipper shipper){
        return mDatabase.child("Shipper").child(shipper.getiDShipper()).setValue(shipper);
    }

    public Task<Void> Ghi_Admin (Admin admin){
        return mDatabase.child("Admin").setValue(admin);
    }

    public Task<Void> Ghi_LoaiSanPham (LoaiSanPham loaiSanPham){
        return mDatabase.child("LoaiSanPham").child(loaiSanPham.getiDLoai()).setValue(loaiSanPham);
    }
    public Task<Void> Ghi_NganHang(TaiKhoanNganHang taiKhoanNganHangAdmin)
    {
        return  mDatabase.child("TaiKhoanNganHangAdmin").child(taiKhoanNganHangAdmin.getId()).setValue(taiKhoanNganHangAdmin);
    }
    public Task<Void> Ghi_ThongBao(ThongBao thongBao)
    {
        return  mDatabase.child("ThongBao").child("admin").child(thongBao.getIDThongBao()).setValue(thongBao);
    }

    public Task<Void> Ghi_ThongBao_CuaHang(ThongBao thongBao, String cuaHang)
    {
        return  mDatabase.child("ThongBao").child(cuaHang).child(thongBao.getIDThongBao()).setValue(thongBao);
    }

    public Task<Void> Ghi_ThongBao_Shipper(ThongBao thongBao, String shipper)
    {
        return  mDatabase.child("ThongBao").child(shipper).child(thongBao.getIDThongBao()).setValue(thongBao);
    }

    public Task<Void> Ghi_BaoCao(BaoCaoShop baoCaoShop)
    {
        return  mDatabase.child("BaoCao").child(baoCaoShop.getIDBaoCao()).setValue(baoCaoShop);
    }

    public Task<Void> Ghi_BaoCaoShipper(BaoCaoShipper baoCaoShipper)
    {
        return  mDatabase.child("BaoCao_CuaHang_Shipper").child(baoCaoShipper.getIdBaoCao()).setValue(baoCaoShipper);
    }
    public Task<Void> Ghi_ThongBao_random(String IDUser, String title, String noiDung, LoaiThongBao normal)
    {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        ThongBao thongBao = new ThongBao(uuid,noiDung,title,"",IDUser,"", TrangThaiThongBao.ChuaXem,new Date());
        thongBao.setLoaiThongBao(normal);
        return  mDatabase.child("ThongBao").child(IDUser).child(uuid).setValue(thongBao);
    }
    public void Up2MatCMND(Uri cmndTrc, Uri cmndSau, String iDShipper)
    {
        storageRef.child("Shipper").child(iDShipper).child("CMND_MatTruoc").putFile(cmndTrc);
        storageRef.child("Shipper").child(iDShipper).child("CMND_MatSau").putFile(cmndSau);
    }

    public UploadTask UpAvatar(Uri avatar, String shipper)
    {
        return storageRef.child("Shipper").child(shipper).child("avatar").putFile(avatar);
    }

    public UploadTask UpAvatarAdmin(Uri AvatarAdmin)
    {
        return storageRef.child("Admin").child("AvatarAdmin").putFile(AvatarAdmin);
    }

    public UploadTask UpImageLoaiSanPham(Uri imageLoaiSanPham, String loaiSanPham)
    {
        return storageRef.child("LoaiSanPham").child(loaiSanPham).child("Lo???i s???n ph???m").putFile(imageLoaiSanPham);
    }

    public void GetSanPham(ArrayList arrayList, SanPhamAdapter sanPhamAdapter, CuaHang cuaHang) {
        mDatabase.child("SanPham").orderByChild("idcuaHang").equalTo(cuaHang.getIDCuaHang()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    SanPham sanPham = postSnapshot.getValue(SanPham.class);
                    arrayList.add(sanPham);
                    if (sanPhamAdapter != null) {
                        sanPhamAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public String GetStringTrangThaiCuaHang(TrangThaiCuaHang trangThaiCuaHang){
        String res = "";
        if(trangThaiCuaHang == TrangThaiCuaHang.DaKichHoat){
            res = "???? k??ch ho???t";
        }
        if(trangThaiCuaHang == TrangThaiCuaHang.ChuaKichHoat){
            res = "Ch??a k??ch ho???t";
        }
        if(trangThaiCuaHang == TrangThaiCuaHang.BiKhoa){
            res = "???? b??? kh??a";
        }
        if(trangThaiCuaHang == TrangThaiCuaHang.DangHoatDong){
            res = "??ang ho???t ?????ng";
        }
        if(trangThaiCuaHang == TrangThaiCuaHang.DongCuaHang){
            res = "????ng c???a h??ng";
        }
        return res;
    }

    public void SetColorTrangThaiCuaHang(TrangThaiCuaHang trangThai, TextView textView){
        String res = "";
        if (trangThai == TrangThaiCuaHang.DangHoatDong)
        {
            res = "??ang ho???t ?????ng";
            textView.setTextColor(Color.parseColor("#00FF00"));
        }
        if (trangThai== TrangThaiCuaHang.BiKhoa)
        {
            res = "???? b??? kh??a";
            textView.setTextColor(Color.parseColor("#FF3333"));
        }
        if (trangThai== TrangThaiCuaHang.DongCuaHang)
        {
            res = "????ng c???a h??ng";
            textView.setTextColor(Color.parseColor("#BBBBBB"));
        }


    }

    public String GetStringTrangThaiShipper(TrangThaiShipper trangThaiShipper){
        String res = "";
        if(trangThaiShipper == TrangThaiShipper.DangHoatDong){
            res = "??ang ho???t ?????ng";
        }
        if(trangThaiShipper == TrangThaiShipper.KhongHoatDong){
            res = "Kh??ng ho???t ?????ng";
        }
        if(trangThaiShipper == TrangThaiShipper.BiKhoa){
            res = "???? b??? kh??a";
        }
        if(trangThaiShipper == TrangThaiShipper.DangGiaoHang){
            res = "??ang giao h??ng";
        }
        return res;
    }

    public String GetStringTrangThaiHoaDon(TrangThaiThanhToan trangThaiThanhToan){
        String res = "";
        if(trangThaiThanhToan == TrangThaiThanhToan.ChoXacNhan){
            res = "Ch??? shop x??c nh???n";
        }
        if(trangThaiThanhToan == TrangThaiThanhToan.DaXacNhan){
            res = "???? x??c nh???n";
        }
        return res;
    }

    public void SetColorTrangThaiHoaDonr(TrangThaiThanhToan trangThai, TextView textView){

        if (trangThai == TrangThaiThanhToan.ChoXacNhan)
        {

            textView.setTextColor(Color.parseColor("#F0BE00"));
        }
        if (trangThai==  TrangThaiThanhToan.DaXacNhan)
        {

            textView.setTextColor(Color.parseColor("#00D772"));
        }
    }
    public void SetColorTrangThaiShipper(TrangThaiShipper trangThai, TextView textView){
        String res = "";
        if (trangThai == TrangThaiShipper.DangHoatDong)
        {
            res = "??ang ho???t ?????ng";
            textView.setTextColor(Color.parseColor("#00FF00"));
        }
        if (trangThai== TrangThaiShipper.BiKhoa)
        {
            res = "???? b??? kh??a";
            textView.setTextColor(Color.parseColor("#FF3333"));
        }
        if (trangThai== TrangThaiShipper.KhongHoatDong)
        {
            res = "Kh??ng hoat ?????ng";
            textView.setTextColor(Color.parseColor("#BBBBBB"));
        }
        if (trangThai== TrangThaiShipper.DangGiaoHang)
        {
            res = "??ang giao h??ng";
            textView.setTextColor(Color.parseColor("#FFFF66"));
        }
    }

}
