package com.contrastsecurity.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "vehicles")
public class Vehicle {

    @Id
    private String id;

    @Field("highway08")
    private int highwayMPG;
    @Field("city08")
    private int cityMPG;
    @Field("comb08")
    private int averageMPG;

    private String make;
    private String model;
    private int year;

    private int cylinders;
    @Field("displ")
    private int displacement;

    private String fuelType;

    // default constructor
    public Vehicle() {
    }

    @PersistenceConstructor
    public Vehicle(int highwayMPG, int cityMPG, int averageMPG, String make, String model, int year, int cylinders,
                   int displacement, String fuelType) {
        this.highwayMPG = highwayMPG;
        this.cityMPG = cityMPG;
        this.averageMPG = averageMPG;
        this.make = make;
        this.model = model;
        this.year = year;
        this.cylinders = cylinders;
        this.displacement = displacement;
        this.fuelType = fuelType;
    }

    public int getHighwayMPG() {
        return highwayMPG;
    }

    public void setHighwayMPG(int highwayMPG) {
        this.highwayMPG = highwayMPG;
    }

    public int getCityMPG() {
        return cityMPG;
    }

    public void setCityMPG(int cityMPG) {
        this.cityMPG = cityMPG;
    }

    public int getAverageMPG() {
        return averageMPG;
    }

    public void setAverageMPG(int averageMPG) {
        this.averageMPG = averageMPG;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getCylinders() {
        return cylinders;
    }

    public void setCylinders(int cylinders) {
        this.cylinders = cylinders;
    }

    public int getDisplacement() {
        return displacement;
    }

    public void setDisplacement(int displacement) {
        this.displacement = displacement;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getId() {
        return id;
    }
}
