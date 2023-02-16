package com.example.vendorlyvendorapp;

public class putProductData {
String name,price,imgurl;

    public putProductData() {
    }

    public putProductData(String name, String price, String imgurl) {
        this.name = name;
        this.price = price;
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

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
