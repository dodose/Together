package com.example.together.model;

public class Lounge
{
    private String requestorId;
    private String petBunyangId;


    public Lounge() {
    }

    public Lounge(String requestorId, String petBunyangId) {
        this.requestorId = requestorId;
        this.petBunyangId = petBunyangId;
    }

    public String getRequestorId() {
        return requestorId;
    }

    public void setRequestorId(String requestorId) {
        this.requestorId = requestorId;
    }

    public String getPetBunyangId() {
        return petBunyangId;
    }

    public void setPetBunyangId(String petBunyangId) {
        this.petBunyangId = petBunyangId;
    }
}
