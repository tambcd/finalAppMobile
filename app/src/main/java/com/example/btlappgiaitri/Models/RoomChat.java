package com.example.btlappgiaitri.Models;

public class RoomChat {
    String ID, IDTaiKhoan1, IDTaiKhoan2;

    public  RoomChat(){};

    public RoomChat(String ID, String IDTaiKhoan1, String IDTaiKhoan2) {
        this.ID = ID;
        this.IDTaiKhoan1 = IDTaiKhoan1;
        this.IDTaiKhoan2 = IDTaiKhoan2;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIDTaiKhoan1() {
        return IDTaiKhoan1;
    }

    public void setIDTaiKhoan1(String IDTaiKhoan1) {
        this.IDTaiKhoan1 = IDTaiKhoan1;
    }

    public String getIDTaiKhoan2() {
        return IDTaiKhoan2;
    }

    public void setIDTaiKhoan2(String IDTaiKhoan2) {
        this.IDTaiKhoan2 = IDTaiKhoan2;
    }
}
