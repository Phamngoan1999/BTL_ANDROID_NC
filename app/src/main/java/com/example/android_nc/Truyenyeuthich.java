package com.example.android_nc;

import java.io.Serializable;

public class Truyenyeuthich implements Serializable {
    public long ma;
    public String tentruyen;
    public String noidung;
    public String tenloaitruyen;
    public long maloai;
    public String email;
    public Truyenyeuthich() {
    }
    public Truyenyeuthich(long ma, String tentruyen, String noidung,Long maloai, String tenloaitruyen,String email) {
        this.ma = ma;
        this.tentruyen = tentruyen;
        this.noidung = noidung;
        this.maloai = maloai;
        this.tenloaitruyen = tenloaitruyen;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTenloaitruyen() {
        return tenloaitruyen;
    }

    public void setTenloaitruyen(String tenloaitruyen) {
        this.tenloaitruyen = tenloaitruyen;
    }

    public long getMaloai() {
        return maloai;
    }

    public void setMaloai(long maloai) {
        this.maloai = maloai;
    }

    public long getMa() {
        return ma;
    }

    public void setMa(long ma) {
        this.ma = ma;
    }

    public String getTentruyen() {
        return tentruyen;
    }

    public void setTentruyen(String tentruyen) {
        this.tentruyen = tentruyen;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }
}
