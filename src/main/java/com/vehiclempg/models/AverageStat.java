package com.vehiclempg.models;

public class AverageStat {

    private String make;

    private float average;

    private int year;

    public AverageStat(String make, int year, float average) {
        this.average = average;
        this.make = make;
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public float getAverage() {
        return average;
    }

    public void setAverage(float average) {
        this.average = average;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
