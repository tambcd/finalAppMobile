package com.example.btlappgiaitri.Models;

public class User {
    String ID,avatar ,date ,email,idtk,name,quequan,sdt;

    public  User(){}

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public User(String ID, String avatar, String date, String email, String idtk, String name, String quequan, String sdt) {
        this.ID = ID;
        this.avatar = avatar;
        this.date = date;
        this.email = email;
        this.idtk = idtk;
        this.name = name;
        this.quequan = quequan;
        this.sdt = sdt;
    }

    public User(String avatar, String date, String email, String idtk, String name, String quequan, String sdt) {
        this.avatar = avatar;
        this.date = date;
        this.email = email;
        this.idtk = idtk;
        this.name = name;
        this.quequan = quequan;
        this.sdt = sdt;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdtk() {
        return idtk;
    }

    public void setIdtk(String idtk) {
        this.idtk = idtk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuequan() {
        return quequan;
    }

    public void setQuequan(String quequan) {
        this.quequan = quequan;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
