package com.contrastsecurity.services;

import com.contrastsecurity.models.Vehicle;
import com.contrastsecurity.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public List<Vehicle> findByMake(String make) {
        return vehicleRepository.findByMake(make);
    }

    public List<Vehicle> findByModel(String model) {
        return vehicleRepository.findByModel(model);
    }

    public List<Vehicle> findByYear(int year) {
        return vehicleRepository.findByYear(year);
    }

    public List<Vehicle> findByYearBetween(int fromYear, int toYear) {
        return vehicleRepository.findByYearBetween(fromYear, toYear);
    }

    public List<Vehicle> findByCylinders(int cylinders) {
        return vehicleRepository.findByCylinders(cylinders);
    }

    public List<Vehicle> findByFuelType(String fuelType) {
        return vehicleRepository.findByFuelType(fuelType);
    }
}
