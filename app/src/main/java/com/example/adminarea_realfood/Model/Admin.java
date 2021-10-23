package com.example.adminarea_realfood.Model;

public class Admin {
    String hoTen;
    String eMail;
    String sDT;
    String ngaySinh;

    public Admin() {
    }

    public Admin(String hoTen, String eMail, String sDT, String ngaySinh) {
        this.hoTen = hoTen;
        this.eMail = eMail;
        this.sDT = sDT;
        this.ngaySinh = ngaySinh;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getsDT() {
        return sDT;
    }

    public void setsDT(String sDT) {
        this.sDT = sDT;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }
}
