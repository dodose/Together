package com.example.together.model;

public class PetchingFriend {

    private String petName;
    private String petBreed;
    private String petImg;
    private String petGender;
    private String age;
    private String intro_dog;
    private String petFriendId;
    private String owner;

    public PetchingFriend() {
    }

    public PetchingFriend(String petName, String petBreed, String petImg, String petGender, String age, String intro_dog, String petFriendId, String owner) {
        this.petName = petName;
        this.petBreed = petBreed;
        this.petImg = petImg;
        this.petGender = petGender;
        this.age = age;
        this.intro_dog = intro_dog;
        this.petFriendId = petFriendId;
        this.owner = owner;
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

    public String getIntro_dog() {
        return intro_dog;
    }

    public void setIntro_dog(String intro_dog) {
        this.intro_dog = intro_dog;
    }

    public String getPetFriendId() {
        return petFriendId;
    }

    public void setPetFriendId(String petFriendId) {
        this.petFriendId = petFriendId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
