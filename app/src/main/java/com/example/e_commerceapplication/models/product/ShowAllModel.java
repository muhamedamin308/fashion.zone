package com.example.e_commerceapplication.models.product;

import com.example.e_commerceapplication.general.data.ProductType;

import java.io.Serializable;

public class ShowAllModel extends Product implements Serializable {
    String type;
    public final ProductType productType = ProductType.ALL;

    public ShowAllModel(String image_url, String name, String description, String rating, String type, Double price) {
        this.image_url = image_url;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.type = type;
        this.price = price;
    }

    public ShowAllModel() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public ProductType productTypeConfirm() {
        return this.productType;
    }

    @Override
    public double[] calculatePayments(double amount, double shipping, double discount) {
        amount = getPrice();
        discount = amount * 0.1;
        shipping = amount * 0.2;
        return new double[]{amount, shipping, discount};
    }
}
