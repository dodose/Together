package com.example.together.Model;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;

public class Review {

    public Drawable userImage;
    public String reviewid;
    public String reviewcont;
    public String reviewstar;
    public String reviewdt;

    public Review(String user_id, String reviewcontent, String star, String cont_dt) {
        this.reviewid = user_id;
        this.reviewcont = reviewcontent;
        this.reviewstar = star;
        this.reviewdt = cont_dt;
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

    public String getReviewstar() {
        return reviewstar;
    }

    public void setReviewstar(String reviewstar) {
        this.reviewstar = reviewstar;
    }

    public String getReviewdt() {
        return reviewdt;
    }

    public void setReviewdt(String reviewdt) {
        this.reviewdt = reviewdt;
    }


}
