package com.example.together.model;

public class Product {

    public String productimage;
    public String productname;
    public String productcont;
    public String productprice;

    public Product(String img_path, String name, String price, String cont) {
        this.productimage = img_path;
        this.productname = name;
        this.productcont = cont;
        this.productprice = price;

    }
}
