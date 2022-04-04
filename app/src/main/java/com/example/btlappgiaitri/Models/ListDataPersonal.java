package com.example.btlappgiaitri.Models;

import java.util.List;

public class ListDataPersonal {
    private int type;
    List<BanBe> banBeList;
    List<User> MainPage;
    List<DataVideo> videoList;
    List<txtContent> txtContents;


    public ListDataPersonal(int type, List<BanBe> banBeList, List<User> mainPage, List<DataVideo> videoList, List<txtContent> txtContents) {
        this.type = type;
        this.banBeList = banBeList;
        MainPage = mainPage;
        this.videoList = videoList;
        this.txtContents = txtContents;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<BanBe> getBanBeList() {
        return banBeList;
    }

    public void setBanBeList(List<BanBe> banBeList) {
        this.banBeList = banBeList;
    }

    public List<User> getMainPage() {
        return MainPage;
    }

    public void setMainPage(List<User> mainPage) {
        MainPage = mainPage;
    }

    public List<DataVideo> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<DataVideo> videoList) {
        this.videoList = videoList;
    }

    public List<txtContent> getTxtContents() {
        return txtContents;
    }

    public void setTxtContents(List<txtContent> txtContents) {
        this.txtContents = txtContents;
    }
}
