package com.example.e_commerceapplication.classes.product;

import com.example.e_commerceapplication.general.enums.ProductType;

import java.io.Serializable;

public class PopularProduct extends Product implements Serializable {
    int order;
    public final ProductType productType = ProductType.POPULAR;

    @Override
    public ProductType productTypeConfirm() {
        return this.productType;
    }

    @Override
    public double[] calculatePayments(double amount, double shipping, double discount) {
        amount = getPrice();
        discount = 0.0;
        shipping = amount * 0.2;
        return new double[]{amount, shipping, discount};
    }

    public PopularProduct() {
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public PopularProduct(String image_url, String name, String description, String rating, double price, int order) {
        this.image_url = image_url;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.price = price;
        this.order = order;
    }
}
