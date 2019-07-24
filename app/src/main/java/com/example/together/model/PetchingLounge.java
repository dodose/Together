package com.example.together.model;

public class PetchingLounge {

    private String petBunyangId;
    private String petFirendId;
    private String petchingLounge;

    public PetchingLounge(String petBunyangId, String petFirendId, String petchingLounge) {
        this.petBunyangId = petBunyangId;
        this.petFirendId = petFirendId;
        this.petchingLounge = petchingLounge;
    }

    public PetchingLounge() {
    }


    public String getPetBunyangId() {
        return petBunyangId;
    }

    public void setPetBunyangId(String petBunyangId) {
        this.petBunyangId = petBunyangId;
    }

    public String getPetFirendId() {
        return petFirendId;
    }

    public void setPetFirendId(String petFirendId) {
        this.petFirendId = petFirendId;
    }

    public String getPetchingLounge() {
        return petchingLounge;
    }

    public void setPetchingLounge(String petchingLounge) {
        this.petchingLounge = petchingLounge;
    }
}
