package com.example.e_commerceapplication.models.product;

import com.example.e_commerceapplication.general.data.ProductType;

import java.io.Serializable;

public class NewProductsModel extends Product implements Serializable {
    String release;
    public final ProductType productType = ProductType.NEW;

    public NewProductsModel(String image_url, String name, String description, double price, String rating) {
        this.image_url = image_url;
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
    }

    public NewProductsModel() {
    }

    @Override
    public ProductType productTypeConfirm() {
        return this.productType;
    }

    @Override
    public double[] calculatePayments(double amount, double shipping, double discount) {
        amount = getPrice();
        discount = amount * 0.1;
        shipping = 0.0;
        return new double[]{amount, shipping, discount};
    }
}
