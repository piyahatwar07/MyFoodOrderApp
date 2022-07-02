package com.example.myfoodorderapp;

public class MenuList {
String name,price,imageUrl;
 public MenuList(String name, String price, String imageUrl){
     this.name=name;
     this.price=price;
     this.imageUrl=imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
