package com.example.together.Model;

public class Funeral {

    public String img_path;
    public String Time;
    public String content;
    public String etp_name;
    public String etp_addr;
    public String starcount;
    public String price;


    public Funeral(String img_path, String mAddr, String mName, String mTime, String mcontent, Float starcount, String price) {
        this.img_path = img_path;
        this.Time = mTime;
        this.etp_addr = mAddr;
        this.etp_name = mName;
        this.starcount = starcount.toString();
        this.content = mcontent;
        this.price = price;

    }
}
