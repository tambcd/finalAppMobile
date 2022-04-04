package com.example.btlappgiaitri.Models;

public class MediaObject {

    private String ID,ID_TK;
    private String title;
    private String date;
    private String videourl;
    private boolean like , isplay;
    private  String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MediaObject(String ID, String ID_TK, String title, String date, String videourl, boolean like, boolean isplay, String name) {
        this.ID = ID;
        this.ID_TK = ID_TK;
        this.title = title;
        this.date = date;
        this.videourl = videourl;
        this.like = like;
        this.isplay = isplay;
        this.name = name;
    }

    public MediaObject(String ID, String ID_TK, String title, String date, String videourl, boolean like, boolean isplay) {
        this.ID = ID;
        this.ID_TK = ID_TK;
        this.title = title;
        this.date = date;
        this.videourl = videourl;
        this.like = like;
        this.isplay = isplay;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public boolean isIsplay() {
        return isplay;
    }

    public void setIsplay(boolean isplay) {
        this.isplay = isplay;
    }
}