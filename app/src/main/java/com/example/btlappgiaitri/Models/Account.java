package com.example.btlappgiaitri.Models;

public class Account {
    String ID,Pass;

    public Account() {
    }

    public Account(String ID, String pass) {
        this.ID = ID;
        Pass = pass;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }
}
