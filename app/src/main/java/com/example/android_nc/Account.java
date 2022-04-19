package com.example.android_nc;

public class Account {
    String Email,Phanquyen;
    Long ma;
    public Account() {

    }

    public Account(long ma, String email, String phanquyen){
        this.ma = ma;
        this.Email = email;
        this.Phanquyen = phanquyen;
    }

    public Long getMa() {
        return ma;
    }

    public void setMa(Long ma) {
        this.ma = ma;
    }
    public String getEmail() { return Email;}
    public void setEmail(String email) {
        this.Email = email;
    }
    public String getPhanquyen() { return Phanquyen;}
    public void setPhanquyen(String phanquyen) {
        this.Phanquyen = phanquyen;
    }
}
