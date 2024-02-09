package com.example.e_commerceapplication.classes.product;

import java.io.Serializable;

public class Payment implements Serializable {
    String creditCardHolderName, productName, productRate, userID;
    int productQuantity;
    double productTotalPrice;


    public Payment() {
    }

    public Payment(String creditCardHolderName, String productName, String productRate, String userID, int productQuantity, double productTotalPrice) {
        this.creditCardHolderName = creditCardHolderName;
        this.productName = productName;
        this.productRate = productRate;
        this.productQuantity = productQuantity;
        this.productTotalPrice = productTotalPrice;
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public double getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductTotalPrice(double productTotalPrice) {
        this.productTotalPrice = productTotalPrice;
    }

    public String getCreditCardHolderName() {
        return creditCardHolderName;
    }

    public void setCreditCardHolderName(String creditCardHolderName) {
        this.creditCardHolderName = creditCardHolderName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductRate() {
        return productRate;
    }

    public void setProductRate(String productRate) {
        this.productRate = productRate;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }
}
