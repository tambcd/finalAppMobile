package com.example.btlappgiaitri.Models;

public class BanBe {
    String ID,IDTK1,IDTK2,name,avatar;



    public BanBe() {
    }

    public BanBe(String ID, String IDTK1, String IDTK2) {
        this.ID = ID;
        this.IDTK1 = IDTK1;
        this.IDTK2 = IDTK2;
    }

    public BanBe(String ID, String IDTK1, String IDTK2, String name, String avatar) {
        this.ID = ID;
        this.IDTK1 = IDTK1;
        this.IDTK2 = IDTK2;
        this.name = name;
        this.avatar = avatar;
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

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIDTK1() {
        return IDTK1;
    }

    public void setIDTK1(String IDTK1) {
        this.IDTK1 = IDTK1;
    }

    public String getIDTK2() {
        return IDTK2;
    }

    public void setIDTK2(String IDTK2) {
        this.IDTK2 = IDTK2;
    }
}
