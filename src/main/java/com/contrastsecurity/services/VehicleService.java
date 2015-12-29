package com.contrastsecurity.services;

import com.contrastsecurity.models.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class VehicleService {

    private static List<Vehicle> vehicles;

    static {
        vehicles = populateDummyUsers();
    }

    public List<Vehicle> findAllUsers() {
        return vehicles;
    }

    private static List<Vehicle> populateDummyUsers() {
        return new ArrayList<Vehicle>();

    }
}
