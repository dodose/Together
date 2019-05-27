package com.example.together.Model;

public class Pet {

    private String imageurl;
    private String petname;
    private String petbreed;
    private int petweight;
    private int birthday;
    private String gender;
    private String intro;

    public Pet(){

    }

    public Pet(String imageurl, String petname, String intro){

    }


    public Pet(String imageurl, String petname, String petbreed, int petweight, int birthday, String gender, String intro) {
        this.imageurl = imageurl;
        this.petname = petname;
        this.petbreed = petbreed;
        this.petweight = petweight;
        this.birthday = birthday;
        this.gender = gender;
        this.intro = intro;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getPetname() {
        return petname;
    }

    public void setPetname(String petname) {
        this.petname = petname;
    }

    public String getPetbreed() {
        return petbreed;
    }

    public void setPetbreed(String petbreed) {
        this.petbreed = petbreed;
    }

    public int getPetweight() {
        return petweight;
    }

    public void setPetweight(int petweight) {
        this.petweight = petweight;
    }

    public int getBirthday() {
        return birthday;
    }

    public void setBirthday(int birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
