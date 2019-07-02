package com.example.together.model;

public class FuneralProdcutOrder {


    public String name;
    public String price;

    public String img;

    public FuneralProdcutOrder(String name, String price,String img) {

        this.name = name;
        this.price = price;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
