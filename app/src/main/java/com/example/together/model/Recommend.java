package com.example.together.model;

public class Recommend {

    public String img_path;
    public String Time;
    public String etp_name;
    public String etp_addr;
    public String reviewcount;
    public String reviewavg;



    public Recommend(String mName, String mAddr,String mTime, String img_path, String reviewcount, String reviewavg) {

        this.img_path = img_path;
        this.etp_addr = mAddr;
        this.etp_name = mName;
        this.Time = mTime;
        this.reviewcount = reviewcount;
        this.reviewavg = reviewavg;


    }
}
