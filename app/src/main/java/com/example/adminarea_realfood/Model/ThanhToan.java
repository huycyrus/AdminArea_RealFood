package com.example.adminarea_realfood.Model;

import android.net.Uri;

import com.example.adminarea_realfood.Screen.TaiKhoanNganHangAdmin;

import java.util.Date;

public class ThanhToan {
    String idBill;
    String idCuaHang;
    String NoiDung;
    Float soTien;

    Date ngayThanhToan;
    Uri imageImage;

    public Uri getImageImage() {
        return imageImage;
    }

    public void setImageImage(Uri imageImage) {
        this.imageImage = imageImage;
    }

    public ThanhToan(String idBill, String idCuaHang, String noiDung, Float soTien, Date ngayThanhToan, Uri imageImage) {
        this.idBill = idBill;
        this.idCuaHang = idCuaHang;
        NoiDung = noiDung;
        this.soTien = soTien;

        this.ngayThanhToan = ngayThanhToan;
        this.imageImage = imageImage;
    }

    public ThanhToan() {
    }

    public String getIdBill() {
        return idBill;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
    }

    public String getIdCuaHang() {
        return idCuaHang;
    }

    public void setIdCuaHang(String idCuaHang) {
        this.idCuaHang = idCuaHang;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

    public Float getSoTien() {
        return soTien;
    }

    public void setSoTien(Float soTien) {
        this.soTien = soTien;
    }


    public Date getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(Date ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }
}
