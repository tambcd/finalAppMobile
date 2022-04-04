package com.example.btlappgiaitri.Models;

public class Comment {

    String IDBL,ID_TK,IdVideo,NoiDung,name,avatar;

    public Comment() {
    }

    public Comment(String IDBL, String ID_TK, String idVideo, String noiDung) {
        this.IDBL = IDBL;
        this.ID_TK = ID_TK;
        IdVideo = idVideo;
        NoiDung = noiDung;
    }

    public Comment(String IDBL, String ID_TK, String idVideo, String noiDung, String name, String avatar) {
        this.IDBL = IDBL;
        this.ID_TK = ID_TK;
        IdVideo = idVideo;
        NoiDung = noiDung;
        this.name = name;
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIDBL() {
        return IDBL;
    }

    public void setIDBL(String IDBL) {
        this.IDBL = IDBL;
    }

    public String getID_TK() {
        return ID_TK;
    }

    public void setID_TK(String ID_TK) {
        this.ID_TK = ID_TK;
    }

    public String getIdVideo() {
        return IdVideo;
    }

    public void setIdVideo(String idVideo) {
        IdVideo = idVideo;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String noiDung) {
        NoiDung = noiDung;
    }
}
