package com.example.myfoodorderapp;

public class OrderModel {
    String orderImage;
    String soldOrderName,price,OrderNumber;
    public OrderModel(){}


    public OrderModel(String orderImage, String soldOrderName, String price, String orderNumber) {
        this.orderImage = orderImage;
        this.soldOrderName = soldOrderName;
        this.price = price;
        this.OrderNumber = orderNumber;
    }

    public String getOrderImage() {

        return orderImage;
    }

    public void setOrderImage(String orderImage) {

        this.orderImage = orderImage;
    }

    public String getSoldOrderName() {
        return soldOrderName;
    }

    public void setSoldOrderName(String soldOrderName) {
        this.soldOrderName = soldOrderName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrderNumber() {
        return OrderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        OrderNumber = orderNumber;
    }
}
