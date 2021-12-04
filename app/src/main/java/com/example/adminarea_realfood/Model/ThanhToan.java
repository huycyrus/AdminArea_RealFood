package com.example.adminarea_realfood.Model;

import android.net.Uri;

import com.example.adminarea_realfood.Screen.TaiKhoanNganHangAdmin;
import com.example.adminarea_realfood.TrangThai.TrangThaiThanhToan;

import java.util.Date;

public class ThanhToan {
    String idBill;
    String idCuaHang;
    String NoiDung;

    Float soTien;
    String tenNguoiGui;
    String soTaiKhoan;
    String tenNganHang;
    Date ngayThanhToan;
    Uri imageImage;
    TrangThaiThanhToan trangThaiThanhToan;

    public String getTenNguoiGui() {
        return tenNguoiGui;
    }

    public void setTenNguoiGui(String tenNguoiGui) {
        this.tenNguoiGui = tenNguoiGui;
    }

    public String getSoTaiKhoan() {
        return soTaiKhoan;
    }

    public void setSoTaiKhoan(String soTaiKhoan) {
        this.soTaiKhoan = soTaiKhoan;
    }

    public String getTenNganHang() {
        return tenNganHang;
    }

    public void setTenNganHang(String tenNganHang) {
        this.tenNganHang = tenNganHang;
    }

    public ThanhToan(String idBill, String idCuaHang, String noiDung, Float soTien, String tenNguoiGui, String soTaiKhoan, String tenNganHang, Date ngayThanhToan, Uri imageImage, TrangThaiThanhToan trangThaiThanhToan) {
        this.idBill = idBill;
        this.idCuaHang = idCuaHang;
        NoiDung = noiDung;
        this.soTien = soTien;
        this.tenNguoiGui = tenNguoiGui;
        this.soTaiKhoan = soTaiKhoan;
        this.tenNganHang = tenNganHang;
        this.ngayThanhToan = ngayThanhToan;
        this.imageImage = imageImage;
        this.trangThaiThanhToan = trangThaiThanhToan;
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

    public Uri getImageImage() {
        return imageImage;
    }

    public void setImageImage(Uri imageImage) {
        this.imageImage = imageImage;
    }

    public TrangThaiThanhToan getTrangThaiThanhToan() {
        return trangThaiThanhToan;
    }

    public void setTrangThaiThanhToan(TrangThaiThanhToan trangThaiThanhToan) {
        this.trangThaiThanhToan = trangThaiThanhToan;
    }

}
