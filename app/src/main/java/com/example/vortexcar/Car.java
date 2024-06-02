package com.example.vortexcar;

public class Car {

    private  int id;
    private String company;
    private String model_year;
    private int mileage;
    private int seats_number;
    private int monthlyPrice;
    private int dailyPrice;
    private int price;
    private String color;
    private String status;
    private String image;

    public Car(int id, String company, String model_year, int mileage, int seats_number, int monthlyPrice, int dailyPrice, int price, String color, String status, String image) {
        this.id = id;
        this.company = company;
        this.model_year = model_year;
        this.mileage = mileage;
        this.seats_number = seats_number;
        this.monthlyPrice = monthlyPrice;
        this.dailyPrice = dailyPrice;
        this.price = price;
        this.color = color;
        this.status = status;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }

    public String getModel_year() {
        return model_year;
    }

    public int getMileage() {
        return mileage;
    }

    public int getSeats_number() {
        return seats_number;
    }

    public int getMonthlyPrice() {
        return monthlyPrice;
    }

    public int getDailyPrice() {
        return dailyPrice;
    }

    public int getPrice() {
        return price;
    }

    public String getColor() {
        return color;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setModel_year(String model_year) {
        this.model_year = model_year;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public void setSeats_number(int seats_number) {
        this.seats_number = seats_number;
    }

    public void setMonthlyPrice(int monthlyPrice) {
        this.monthlyPrice = monthlyPrice;
    }

    public void setDailyPrice(int dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setImage(String image) {
        this.image = image;
    }
}