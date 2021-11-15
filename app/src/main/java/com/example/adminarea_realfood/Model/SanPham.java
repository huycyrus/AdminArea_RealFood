package com.example.adminarea_realfood.Model;

import java.util.ArrayList;

public class SanPham {
    String IDSanPham, TenSanPham, IDLoai, IDDanhMuc, Gia, ChiTietSanPham, IDCuaHang,size;

    ArrayList<String> images;

    public ArrayList<String> getImages() {
        return images;
    }

    public SanPham() {
    }

    public SanPham(String IDSanPham, String tenSanPham, String IDLoai, String IDDanhMuc, String gia, String chiTietSanPham, String IDCuaHang, String size) {
        this.IDSanPham = IDSanPham;
        TenSanPham = tenSanPham;
        this.IDLoai = IDLoai;
        this.IDDanhMuc = IDDanhMuc;
        Gia = gia;
        ChiTietSanPham = chiTietSanPham;
        this.IDCuaHang = IDCuaHang;
        this.size = size;
    }

    public String getIDSanPham() {
        return IDSanPham;
    }

    public void setIDSanPham(String IDSanPham) {
        this.IDSanPham = IDSanPham;
    }

    public String getTenSanPham() {
        return TenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        TenSanPham = tenSanPham;
    }

    public String getIDLoai() {
        return IDLoai;
    }

    public void setIDLoai(String IDLoai) {
        this.IDLoai = IDLoai;
    }

    public String getIDDanhMuc() {
        return IDDanhMuc;
    }

    public void setIDDanhMuc(String IDDanhMuc) {
        this.IDDanhMuc = IDDanhMuc;
    }

    public String getGia() {
        return Gia;
    }

    public void setGia(String gia) {
        Gia = gia;
    }

    public String getChiTietSanPham() {
        return ChiTietSanPham;
    }

    public void setChiTietSanPham(String chiTietSanPham) {
        ChiTietSanPham = chiTietSanPham;
    }

    public String getIDCuaHang() {
        return IDCuaHang;
    }

    public void setIDCuaHang(String IDCuaHang) {
        this.IDCuaHang = IDCuaHang;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
