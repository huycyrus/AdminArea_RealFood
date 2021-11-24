package com.example.adminarea_realfood;

import android.net.Uri;

import com.example.adminarea_realfood.Model.Admin;
import com.example.adminarea_realfood.Model.BaoCaoShop;
import com.example.adminarea_realfood.Model.CuaHang;
import com.example.adminarea_realfood.Model.LoaiSanPham;
import com.example.adminarea_realfood.Model.SanPham;
import com.example.adminarea_realfood.Model.Shipper;
import com.example.adminarea_realfood.Model.TaiKhoanNganHang;
import com.example.adminarea_realfood.Model.ThongBao;
import com.example.adminarea_realfood.adapter.SanPhamAdapter;
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

    public Task<Void> Ghi_BaoCao(BaoCaoShop baoCaoShop)
    {
        return  mDatabase.child("BaoCao").child(baoCaoShop.getIDBaoCao()).setValue(baoCaoShop);
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
        return storageRef.child("LoaiSanPham").child(loaiSanPham).child("Loại sản phẩm").putFile(imageLoaiSanPham);
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

}
