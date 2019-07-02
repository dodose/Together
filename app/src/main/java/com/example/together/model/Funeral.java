package com.example.together.model;

public class Funeral {

    public String img_path;
    public String Time;
    public String content;
    public String etp_name;
    public String etp_addr;
    public String starcount;
    public String price;
    public String etp_cd;


    public Funeral(String img_path, String mAddr, String mName, String mTime, String mcontent, Float starcount, String price,String etp_cd) {
        this.img_path = img_path;
        this.Time = mTime;
        this.etp_addr = mAddr;
        this.etp_name = mName;
        this.starcount = starcount.toString();
        this.content = mcontent;
        this.price = price;
        this.etp_cd = etp_cd;

    }
}
