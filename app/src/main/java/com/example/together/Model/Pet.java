package com.example.together.Model;

public class Pet {
    private String petName;
    private String petBloodType;
    private String description;
    private int thumbnail;
    private int gender;
    private int petAge;

    public Pet(String petName, String petBloodType, String description, int thumbnail, int gender, int petAge) {
        this.petName = petName;
        this.petBloodType = petBloodType;
        this.description = description;
        this.thumbnail = thumbnail;
        this.gender = gender;
        this.petAge = petAge;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetBloodType() {
        return petBloodType;
    }

    public void setPetBloodType(String petBloodType) {
        this.petBloodType = petBloodType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getPetAge() {
        return petAge;
    }

    public void setPetAge(int petAge) {
        this.petAge = petAge;
    }
}
