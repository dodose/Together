package com.example.together.model;

public class SubCategoryItem {

    private String categoryId;
    private String subId;
    private String subCategoryName;
    private String isChecked;
    private String imgurl;

//    private ArrayList<SubCategoryItem> suit;
    private String pd_nm;
    private String pd_img;
    private String pd_content;
    private String pd_price;



//    public SubCategoryItem(ArrayList<SubCategoryItem> suit){
//        this.suit = suit;
//    }


    public SubCategoryItem(String pd_nm, String pd_img,String pd_content,String pd_price){
        this.pd_nm = pd_nm;
        this.pd_img = pd_img;
        this.pd_content = pd_content;
        this.pd_price = pd_price;
    }

    public SubCategoryItem() {

    }


    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }


    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getPd_nm() {
        return pd_nm;
    }

    public void setPd_nm(String pd_nm) {
        this.pd_nm = pd_nm;
    }

    public String getPd_img() {
        return pd_img;
    }

    public void setPd_img(String pd_img) {
        this.pd_img = pd_img;
    }

    public String getPd_content() {
        return pd_content;
    }

    public void setPd_content(String pd_content) {
        this.pd_content = pd_content;
    }

    public String getPd_price() {
        return pd_price;
    }

    public void setPd_price(String pd_price) {
        this.pd_price = pd_price;
    }
}
