package com.example.btlappgiaitri.Models;

public class TuongTac {
    String IDTT,ID_TK,IdVideo;

    public TuongTac() {
    }

    public TuongTac(String IDTT, String ID_TK, String idVideo) {
        this.IDTT = IDTT;
        this.ID_TK = ID_TK;
        this.IdVideo = idVideo;
    }

    public String getIDTT() {
        return IDTT;
    }

    public void setIDTT(String IDTT) {
        this.IDTT = IDTT;
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
}
