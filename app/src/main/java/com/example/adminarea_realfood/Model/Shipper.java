package com.example.adminarea_realfood.Model;

import com.example.adminarea_realfood.TrangThai.TrangThaiShipper;

public class Shipper {
    String iDShipper, idCuaHang, eMail, matKhau, hoVaTen, diaChi, khuVucHoatDong, ngaySinh, maSoXe, soDienThoai, loaiShipper;
    TrangThaiShipper trangThaiShipper;

    public Shipper() {
    }

    public Shipper(String iDShipper, String idCuaHang, String eMail, String matKhau, String hoVaTen, String diaChi, String khuVucHoatDong, String ngaySinh, String maSoXe, String soDienThoai, TrangThaiShipper trangThaiShipper, String loaiShipper) {
        this.iDShipper = iDShipper;
        this.idCuaHang = idCuaHang;
        this.eMail = eMail;
        this.matKhau = matKhau;
        this.hoVaTen = hoVaTen;
        this.diaChi = diaChi;
        this.khuVucHoatDong = khuVucHoatDong;
        this.ngaySinh = ngaySinh;
        this.maSoXe = maSoXe;
        this.soDienThoai = soDienThoai;
        this.trangThaiShipper = trangThaiShipper;
        this.loaiShipper = loaiShipper;
    }

    public String getiDShipper() {
        return iDShipper;
    }

    public void setiDShipper(String iDShipper) {
        this.iDShipper = iDShipper;
    }

    public String getIdCuaHang() {
        return idCuaHang;
    }

    public void setIdCuaHang(String idCuaHang) {
        this.idCuaHang = idCuaHang;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getHoVaTen() {
        return hoVaTen;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getKhuVucHoatDong() {
        return khuVucHoatDong;
    }

    public void setKhuVucHoatDong(String khuVucHoatDong) {
        this.khuVucHoatDong = khuVucHoatDong;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getMaSoXe() {
        return maSoXe;
    }

    public void setMaSoXe(String maSoXe) {
        this.maSoXe = maSoXe;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public TrangThaiShipper getTrangThaiShipper() {
        return trangThaiShipper;
    }

    public void setTrangThaiShipper(TrangThaiShipper trangThaiShipper) {
        this.trangThaiShipper = trangThaiShipper;
    }

    public  String getLoaiShipper(){
        return loaiShipper;
    }

    public void setLoaiShipper(String loaiShipper){
        this.loaiShipper = loaiShipper;
    }
}
