package com.example.btlappgiaitri.Models;

public class LoiMoi {
    String ID,IDTKGui,IDTKNhan;

    public LoiMoi() {
    }

    public LoiMoi(String ID, String IDTKGui, String IDTKNhan) {
        this.ID = ID;
        this.IDTKGui = IDTKGui;
        this.IDTKNhan = IDTKNhan;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIDTKGui() {
        return IDTKGui;
    }

    public void setIDTKGui(String IDTKGui) {
        this.IDTKGui = IDTKGui;
    }

    public String getIDTKNhan() {
        return IDTKNhan;
    }

    public void setIDTKNhan(String IDTKNhan) {
        this.IDTKNhan = IDTKNhan;
    }
}
