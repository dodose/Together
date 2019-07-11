package com.example.together.model;

public class User {

    private String id;
    private String username;
    private String fullname;
    private String imageurl;
    private String bio;
    private String status;
    private String ph_no;
    private String birth_dt;

    public User(String id, String username, String fullname, String imageurl, String bio, String status, String ph_no, String birth_dt) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.imageurl = imageurl;
        this.bio = bio;
        this.status = status;
        this.ph_no = ph_no;
        this.birth_dt = birth_dt;
    }

    public User(String id, String username, String fullname, String imageurl, String bio) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.imageurl = imageurl;
        this.bio = bio;
    }

    public User(String id, String username, String imageurl, String status){
        this.id = id;
        this.username = username;
        this.imageurl = imageurl;
        this.status = status;
    }

    public User(String status){
        this.status = status;
    }


    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPh_no() {
        return ph_no;
    }

    public void setPh_no(String ph_no) {
        this.ph_no = ph_no;
    }

    public String getBirth_dt() {
        return birth_dt;
    }

    public void setBirth_dt(String birth_dt) {
        this.birth_dt = birth_dt;
    }
}
