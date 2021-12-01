package com.example.adminarea_realfood.Model;

import com.example.adminarea_realfood.Screen.TaiKhoanNganHangAdmin;

import java.util.Date;

public class ThanhToan {
    String idBill;
    String idCuaHang;
    String NoiDung;
    Float soTien;
    TaiKhoanNganHang taiKhoanNganHang;
    Date ngayThanhToan;

    public ThanhToan(String idBill, String idCuaHang, String noiDung, Float soTien, TaiKhoanNganHang taiKhoanNganHang, Date ngayThanhToan) {
        this.idBill = idBill;
        this.idCuaHang = idCuaHang;
        NoiDung = noiDung;
        this.soTien = soTien;
        this.taiKhoanNganHang = taiKhoanNganHang;
        this.ngayThanhToan = ngayThanhToan;
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

    public TaiKhoanNganHang getTaiKhoanNganHang() {
        return taiKhoanNganHang;
    }

    public void setTaiKhoanNganHang(TaiKhoanNganHang taiKhoanNganHang) {
        this.taiKhoanNganHang = taiKhoanNganHang;
    }

    public Date getNgayThanhToan() {
        return ngayThanhToan;
    }

    public void setNgayThanhToan(Date ngayThanhToan) {
        this.ngayThanhToan = ngayThanhToan;
    }
}
