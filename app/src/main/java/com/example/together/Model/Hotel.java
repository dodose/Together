package com.example.together.Model;

public class Hotel {

    public int drawableId;
    public String Time;
    public String content;
    public String etp_name;
    public String etp_addr;




    public Hotel(int drawableId,String mAddr,String mName,String Time,String content){
        this.drawableId = drawableId;
        this.etp_addr = mAddr;
        this.etp_name = mName;
        this.Time = Time;
        this.content = content;
    }

//    public Hotel(String mAddr){
//        this.etp_addr = mAddr;
//    }

}
