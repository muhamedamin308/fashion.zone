package com.example.e_commerceapplication.models.users;

public class User {
    String username, email, password, userID;
    double paymentRate;
    public User() {
    }

    public User(String username, String email, String password, double paymentRate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.paymentRate = paymentRate;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getPaymentRate() {
        return paymentRate;
    }

    public void setPaymentRate(double paymentRate) {
        this.paymentRate = paymentRate;
    }
}
