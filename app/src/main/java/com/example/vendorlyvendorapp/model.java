package com.example.vendorlyvendorapp;

public class model {
    String imgurl,name,price;

    model(){

    }

    public model(String imgurl, String name, String price) {
        this.imgurl = imgurl;
        this.name = name;
        this.price = price;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
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
}
