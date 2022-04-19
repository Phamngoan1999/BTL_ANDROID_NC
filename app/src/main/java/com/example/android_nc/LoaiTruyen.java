package com.example.android_nc;

import android.net.Uri;

import java.io.Serializable;

public class LoaiTruyen implements Serializable {
    public long ma;
    public String name;
    public String duongdan;

    public LoaiTruyen() {
    }

    public LoaiTruyen(long ma, String name,String duongdan) {
        this.ma = ma;
        this.name = name;
        this.duongdan = duongdan;
    }
    public long getMa() {
        return ma;
    }

    public void setMa(long ma) {
        this.ma = ma;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuongdan() {
        return duongdan;
    }

    public void setDuongdan(String duongdan) {
        this.duongdan = duongdan;
    }
}
