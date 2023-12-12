package com.example.e_commerceapplication.models.product;

import java.io.Serializable;

public class MyCartModel implements Serializable {
    String productImage, productName, productDate, productTime, productRate;
    double productPrice, totalPrice;
    int totalQuantity;

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public String getProductTime() {
        return productTime;
    }

    public void setProductTime(String productTime) {
        this.productTime = productTime;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getProductRate() {
        return productRate;
    }

    public void setProductRate(String productRate) {
        this.productRate = productRate;
    }

    public MyCartModel(String productImage, String productName, String productDate, String productTime, String productRate, double productPrice, double totalPrice, int totalQuantity) {
        this.productImage = productImage;
        this.productName = productName;
        this.productDate = productDate;
        this.productTime = productTime;
        this.productRate = productRate;
        this.productPrice = productPrice;
        this.totalPrice = totalPrice;
        this.totalQuantity = totalQuantity;
    }

    public MyCartModel() {
    }
}
