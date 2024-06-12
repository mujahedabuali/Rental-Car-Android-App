package com.example.vortexcar;

public class FavCar {
    private Car car;
    private String startDate;
    private String endDate;

    public FavCar(Car car, String startDate, String endDate) {
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and setters
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}

