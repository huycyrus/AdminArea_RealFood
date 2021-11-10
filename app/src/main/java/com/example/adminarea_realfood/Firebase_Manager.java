package com.example.adminarea_realfood;

import android.net.Uri;
import android.util.Log;

import com.example.adminarea_realfood.Model.Admin;
import com.example.adminarea_realfood.Model.LoaiSanPham;
import com.example.adminarea_realfood.Model.Shipper;
import com.example.adminarea_realfood.Model.TaiKhoanNganHang;
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

    public ArrayList<Shipper> getDanhsachshipper()
    {
        ArrayList<Shipper> shippers = new ArrayList<>();
        mDatabase.child("Shipper").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shippers.clear();
                Log.d("a",dataSnapshot.toString());
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Shipper shipper = postSnapshot.getValue(Shipper.class);
                    shippers.add(shipper);
                    Log.d("a",shipper.getiDShipper()+shippers.size());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return shippers;
    }

}
