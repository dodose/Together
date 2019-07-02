package com.example.together.model;

public class Pet {

    private String petimageurl;
    private String petname;
    private String petbreed;
    private String petweight;
    private String birthday;
    private String gender;
    private String intro;
    private String petid;
    private Boolean isChecked;

    public Pet() {

    }


    public Pet(Boolean isChecked) {
        this.isChecked = isChecked;
    }

    public Pet(String petimageurl, String petname, String petbreed, String petweight, String birthday, String gender, String intro, String petid) {
        this.petimageurl = petimageurl;
        this.petname = petname;
        this.petbreed = petbreed;
        this.petweight = petweight;
        this.birthday = birthday;
        this.gender = gender;
        this.intro = intro;
        this.petid = petid;
    }

    public Pet(String petimageurl, String petname, String intro) {
        this.petimageurl = petimageurl;
        this.petname = petname;
        this.intro = intro;
    }

    public Pet(String petimageurl, String petname) {
        this.petimageurl = petimageurl;
        this.petname = petname;
    }

    public String getPetimageurl() {
        return petimageurl;
    }

    public void setPetimageurl(String petimageurl) {
        this.petimageurl = petimageurl;
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

    public String getPetweight() {
        return petweight;
    }

    public void setPetweight(String petweight) {
        this.petweight = petweight;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
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

    public String getPetid() {
        return petid;
    }

    public void setPetid(String petid) {
        this.petid = petid;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }
}

