package com.example.together.Model;

public class Pet {

    private String petimage;
    private String petname;
    private String petbreed;
    private int petweight;
    private int birthday;
    private String gender;
    private String intro;
    private String petid;



    public Pet(){

    }

    public Pet(String petimage, String petname){
        this.petimage = petimage;
        this.petname = petname;

    }


    public Pet(String petimage, String petname, String intro){
        this.petimage = petimage;
        this.petname = petname;
        this.intro = intro;

    }


    public Pet(String petimage, String petname, String petbreed, int petweight, int birthday, String gender, String intro, String petid) {
        this.petimage = petimage;
        this.petname = petname;
        this.petbreed = petbreed;
        this.petweight = petweight;
        this.birthday = birthday;
        this.gender = gender;
        this.intro = intro;
        this.petid = petid;
    }


    public String getPetid() {
        return petid;
    }

    public void setPetid(String petid) {
        this.petid = petid;
    }

    public String getPetimage() {
        return petimage;
    }

    public void setPetimage(String petimage) {
        this.petimage = petimage;
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
