package com.example.together.Model;

public class Hotel {

    public String img_path;
    public String Time;
    public String content;
    public String etp_name;
    public String etp_addr;
    public String starcount;
    public String price;


    public Hotel(String img_path, String mAddr, String mName, String mTime, String mcontent, Float starcount, String firstprise) {

        this.img_path = img_path;
        this.etp_addr = mAddr;
        this.etp_name = mName;
        this.Time = mTime;
        this.content = mcontent;
        this.starcount = starcount.toString();
        this.price = firstprise;

    }



}
