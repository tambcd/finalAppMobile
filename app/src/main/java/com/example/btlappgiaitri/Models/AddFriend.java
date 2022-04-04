package com.example.btlappgiaitri.Models;

public class AddFriend {
    String ID,IDtk,avatar,name;
    boolean check;

    public AddFriend() {
    }

    public AddFriend(String ID,String IDtk, String avatar, String name,boolean check) {
        this.ID = ID;
        this.IDtk =IDtk;
        this.avatar = avatar;
        this.name = name;
        this.check=check;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIDtk() {
        return IDtk;
    }

    public void setIDtk(String IDtk) {
        this.IDtk = IDtk;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}
