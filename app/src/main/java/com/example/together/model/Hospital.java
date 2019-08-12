package com.example.together.model;

public class Hospital {

    public String img_path;
    public String mAddr;
    public String mName;
    public String mTime;
    public Float starcount;
    public String etp_cd;
    public String content;
    public String km;

    public Hospital(String img_path, String mAddr, String mName, String mTime, Float starcount, String etp_cd,String etpcontent,Float km) {
        this.img_path = img_path;
        this.mAddr = mAddr;
        this.mName = mName;
        this.mTime = mTime;
        this.starcount = starcount;
        this.etp_cd = etp_cd;
        this.content = etpcontent;
        this.km = km.toString();

    }
}
