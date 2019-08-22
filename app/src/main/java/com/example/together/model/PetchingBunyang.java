package com.example.together.model;

public class PetchingBunyang {

    private String petName;
    private String petBreed;
    private String petImg;
    private String petGender;
    private String age;
    private String having_certificate;
    private String intro_dog;
    private String specail_note;
    private String owner;
    private String petbunyangid;
    private String image_blood_certification;
    private String petcode;


    public PetchingBunyang() {
    }

    public PetchingBunyang(String petName, String petBreed, String petImg, String petGender, String age, String having_certificate, String intro_dog, String specail_note, String owner, String petbunyangid, String image_blood_certification, String petcode) {
        this.petName = petName;
        this.petBreed = petBreed;
        this.petImg = petImg;
        this.petGender = petGender;
        this.age = age;
        this.having_certificate = having_certificate;
        this.intro_dog = intro_dog;
        this.specail_note = specail_note;
        this.owner = owner;
        this.petbunyangid = petbunyangid;
        this.image_blood_certification = image_blood_certification;
        this.petcode = petcode;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public String getPetImg() {
        return petImg;
    }

    public void setPetImg(String petImg) {
        this.petImg = petImg;
    }

    public String getPetGender() {
        return petGender;
    }

    public void setPetGender(String petGender) {
        this.petGender = petGender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHaving_certificate() {
        return having_certificate;
    }

    public void setHaving_certificate(String having_certificate) {
        this.having_certificate = having_certificate;
    }

    public String getIntro_dog() {
        return intro_dog;
    }

    public void setIntro_dog(String intro_dog) {
        this.intro_dog = intro_dog;
    }

    public String getSpecail_note() {
        return specail_note;
    }

    public void setSpecail_note(String specail_note) {
        this.specail_note = specail_note;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPetbunyangid() {
        return petbunyangid;
    }

    public void setPetbunyangid(String petbunyangid) {
        this.petbunyangid = petbunyangid;
    }

    public String getImage_blood_certification() {
        return image_blood_certification;
    }

    public void setImage_blood_certification(String image_blood_certification) {
        this.image_blood_certification = image_blood_certification;
    }

    public String getPetcode() {
        return petcode;
    }

    public void setPetcode(String petcode) {
        this.petcode = petcode;
    }
}