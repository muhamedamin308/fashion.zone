package com.example.e_commerceapplication.classes.product;

import com.example.e_commerceapplication.general.enums.ProductType;

import java.io.Serializable;

import kotlin.jvm.internal.SerializedIr;

public class NewProduct extends Product implements Serializable {
    String release;
    int stock;
    public final ProductType productType = ProductType.NEW;

    public NewProduct(String image_url, String name, String description, double price, String rating, int stock) {
        this.image_url = image_url;
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.stock = stock;
    }

    public NewProduct() {
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

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    @Override
    public int getStock() {
        return stock;
    }

    @Override
    public void setStock(int stock) {
        this.stock = stock;
    }

    public ProductType getProductType() {
        return productType;
    }
}
