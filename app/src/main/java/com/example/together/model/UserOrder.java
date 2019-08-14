package com.example.together.model;

public class UserOrder {

    public String or_cd;
    public String or_stat;
	public String or_dt;
    public String or_dt2;
    public String th_dt;
    public String or_price;
    public String etp_cd;
    public String etp_nm;

    public UserOrder(String or_cd, String or_stat, String or_dt, String or_dt2, String th_dt, String or_price, String etp_cd, String etp_nm) {

        this.or_cd = or_cd;
        this.or_stat = or_stat;
        this.or_dt = or_dt;
        this.or_dt2 = or_dt2;
        this.th_dt = th_dt;
        this.or_price = or_price;
        this.etp_cd = etp_cd;
        this.etp_nm = etp_nm;

    }
}
