package com.example.btlappgiaitri.Models;

public class Notifycation {
    String ID ;
    String IDTKG ;
    String IDTKN ;
    String Type ;
    String textContent ;
    String thoiGian ;
    String IDLink;

    public Notifycation() {
    }

    public Notifycation(String ID, String IDTKG, String IDTKN, String type, String textContent, String thoiGian) {
        this.ID = ID;
        this.IDTKG = IDTKG;
        this.IDTKN = IDTKN;
        Type = type;
        this.textContent = textContent;
        this.thoiGian = thoiGian;
    }

    public Notifycation(String ID, String IDTKG, String IDTKN, String type, String textContent, String thoiGian, String IDLink) {
        this.ID = ID;
        this.IDTKG = IDTKG;
        this.IDTKN = IDTKN;
        Type = type;
        this.textContent = textContent;
        this.thoiGian = thoiGian;
        this.IDLink = IDLink;
    }

    public String getIDLink() {
        return IDLink;
    }

    public void setIDLink(String IDLink) {
        this.IDLink = IDLink;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIDTKG() {
        return IDTKG;
    }

    public void setIDTKG(String IDTKG) {
        this.IDTKG = IDTKG;
    }

    public String getIDTKN() {
        return IDTKN;
    }

    public void setIDTKN(String IDTKN) {
        this.IDTKN = IDTKN;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }
}
