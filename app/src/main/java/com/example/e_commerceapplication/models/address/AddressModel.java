package com.example.e_commerceapplication.models.address;

public class AddressModel {
    String address, city, country;
    boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public AddressModel() {
    }

    public AddressModel(String address, String city, String country, boolean isSelected) {
        this.address = address;
        this.city = city;
        this.country = country;
        this.isSelected = isSelected;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
