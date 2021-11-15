package com.example.adminarea_realfood.Model;

public class BaoCao {
    String IDBaoCao,IDKhachHang,IDCuaHang,lyDo;

    public BaoCao() {
    }

    public BaoCao(String IDBaoCao, String IDKhachHang, String IDCuaHang, String lyDo) {
        this.IDBaoCao = IDBaoCao;
        this.IDKhachHang = IDKhachHang;
        this.IDCuaHang = IDCuaHang;
        this.lyDo = lyDo;
    }

    public String getIDBaoCao() {
        return IDBaoCao;
    }

    public void setIDBaoCao(String IDBaoCao) {
        this.IDBaoCao = IDBaoCao;
    }

    public String getIDKhachHang() {
        return IDKhachHang;
    }

    public void setIDKhachHang(String IDKhachHang) {
        this.IDKhachHang = IDKhachHang;
    }

    public String getIDCuaHang() {
        return IDCuaHang;
    }

    public void setIDCuaHang(String IDCuaHang) {
        this.IDCuaHang = IDCuaHang;
    }

    public String getLyDo() {
        return lyDo;
    }

    public void setLyDo(String lyDo) {
        this.lyDo = lyDo;
    }
}
