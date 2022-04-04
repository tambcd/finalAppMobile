package com.example.btlappgiaitri.Models;

public class MessageItem {
    String ID,IDRoomChat,IDTaiKhoan,NoiDung;

    public MessageItem() {
    }

    public MessageItem(String ID, String IDRoomChat, String IDTaiKhoan, String noiDung) {
        this.ID = ID;
        this.IDRoomChat = IDRoomChat;
        this.IDTaiKhoan = IDTaiKhoan;
        this.NoiDung = noiDung;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIDRoomChat() {
        return IDRoomChat;
    }

    public void setIDRoomChat(String IDRoomChat) {
        this.IDRoomChat = IDRoomChat;
    }

    public String getIDTaiKhoan() {
        return IDTaiKhoan;
    }

    public void setIDTaiKhoan(String IDTaiKhoan) {
        this.IDTaiKhoan = IDTaiKhoan;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }

}
