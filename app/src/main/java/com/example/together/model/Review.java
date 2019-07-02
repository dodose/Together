package com.example.together.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class Review implements Serializable {

    public Drawable userImage;
    public String reviewid;
    public String reviewcont;
    public Float reviewstar;
    public String reviewdt;
    public String reviewnm;

    public Review(String user_id, String reviewcontent, Float star, String cont_dt,String user_nm) {
        this.reviewid = user_id;
        this.reviewcont = reviewcontent;
        this.reviewstar = star;
        this.reviewdt = cont_dt;
        this.reviewnm = user_nm;
    }

    public String getReviewnm() {
        return reviewnm;
    }

    public void setReviewnm(String reviewnm) {
        this.reviewnm = reviewnm;
    }

    public Drawable getUserImage() {
        return userImage;
    }

    public void setUserImage(Drawable userImage) {
        this.userImage = userImage;
    }

    public String getReviewid() {
        return reviewid;
    }

    public void setReviewid(String reviewid) {
        this.reviewid = reviewid;
    }

    public String getReviewcont() {
        return reviewcont;
    }

    public void setReviewcont(String reviewcont) {
        this.reviewcont = reviewcont;
    }

    public Float getReviewstar() {
        return reviewstar;
    }

    public void setReviewstar(Float reviewstar) {
        this.reviewstar = reviewstar;
    }

    public String getReviewdt() {
        return reviewdt;
    }

    public void setReviewdt(String reviewdt) {
        this.reviewdt = reviewdt;
    }


}
