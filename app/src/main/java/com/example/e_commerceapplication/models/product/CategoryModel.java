package com.example.e_commerceapplication.models.product;

import java.io.Serializable;

public class CategoryModel implements Serializable {
    String image_url, name, type;

    public CategoryModel(String image_url, String name, String type) {
        this.image_url = image_url;
        this.name = name;
        this.type = type;
    }
    public CategoryModel() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
