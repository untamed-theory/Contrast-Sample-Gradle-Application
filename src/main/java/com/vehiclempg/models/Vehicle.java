package com.vehiclempg.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "vehicle")
public class Vehicle {

    @Id
    private String id;

    private int highwayMPG;
    private int cityMPG;
    private int averageMPG;

    private String make;
    private String model;
    private int year;

    private int cylinders;
    private float displ;

    private String fuelType;

    // default constructor
    public Vehicle() {
    }

    /**
     * Vehicle class represented as a Mongo document
     *
     * @param highwayMPG highway miles per gallon
     * @param cityMPG    city miles per gallon
     * @param averageMPG average miles per gallon
     * @param make       make of the vehicle
     * @param model      model of the vehicle
     * @param year       year of the vehicle
     * @param cylinders  number of cylinders
     * @param displ      displacement of the vehicle
     * @param fuelType   type of fuel the vehicle uses
     */
    public Vehicle(int highwayMPG, int cityMPG, int averageMPG, String make, String model, int year, int cylinders,
                   float displ, String fuelType) {
        this.highwayMPG = highwayMPG;
        this.cityMPG = cityMPG;
        this.averageMPG = averageMPG;
        this.make = make;
        this.model = model;
        this.year = year;
        this.cylinders = cylinders;
        this.displ = displ;
        this.fuelType = fuelType;
    }

    @Override
    public String toString() {
        return Integer.toString(year) + " " + make + " " + model;
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

    public float getDispl() {
        return displ;
    }

    public void setDispl(float displ) {
        this.displ = displ;
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
