package com.example.btlappgiaitri.Models;

public class DataVideo {
    String ID,ID_TK,Timestamo,title,videourl;

    public DataVideo() {
    }

    public DataVideo(String videourl) {
        this.videourl = videourl;
    }

    public DataVideo(String ID, String ID_TK, String timestamo, String title, String videourl) {
        this.ID = ID;
        this.ID_TK = ID_TK;
        Timestamo = timestamo;
        this.title = title;
        this.videourl = videourl;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID_TK() {
        return ID_TK;
    }

    public void setID_TK(String ID_TK) {
        this.ID_TK = ID_TK;
    }

    public String getTimestamo() {
        return Timestamo;
    }

    public void setTimestamo(String timestamo) {
        Timestamo = timestamo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }
}
