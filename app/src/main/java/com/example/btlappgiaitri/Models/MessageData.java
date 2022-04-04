package com.example.btlappgiaitri.Models;

public class MessageData {
    private String id;
    private String fullName;
    private String textContent;
    private String textTime,avartar;

    public  MessageData(int i, String name, String test, String hôm_qua){};


    public MessageData(String id, String fullName, String textContent, String textTime) {
        this.id = id;
        this.fullName = fullName;
        this.textContent = textContent;
        this.textTime = textTime;
    }


    public MessageData(String id, String fullName,String avartar) {
        this.id = id;
        this.fullName = fullName;
        this.avartar = avartar;
    }

    public MessageData(String id, String fullName, String textContent, String textTime, String avartar) {
        this.id = id;
        this.fullName = fullName;
        this.textContent = textContent;
        this.textTime = textTime;
        this.avartar = avartar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public String getTextTime() {
        return textTime;
    }

    public void setTextTime(String textTime) {
        this.textTime = textTime;
    }

    public String getAvartar() {
        return avartar;
    }

    public void setAvartar(String avartar) {
        this.avartar = avartar;
    }
}
