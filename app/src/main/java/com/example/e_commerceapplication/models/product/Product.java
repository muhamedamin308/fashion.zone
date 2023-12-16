package com.example.e_commerceapplication.models.product;

import com.example.e_commerceapplication.general.data.ProductType;

import java.io.Serializable;

abstract public class Product implements Serializable {
    String image_url, name, description, rating, type;
    double price;
    int stock;
    public ProductType productType = ProductType.MAIN;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Product(String image_url, String name, String description, String rating, double price, String type) {
        this.image_url = image_url;
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.price = price;
        this.type = type;
    }

    public Product() {
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public ProductType productTypeConfirm() {
        return this.productType;
    }
    abstract public double[] calculatePayments(double amount, double shipping, double discount);

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
