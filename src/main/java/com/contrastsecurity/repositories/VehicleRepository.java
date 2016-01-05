package com.contrastsecurity.repositories;

import com.contrastsecurity.models.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface VehicleRepository extends MongoRepository<Vehicle, String> {

    List<Vehicle> findByMake(String make);

    List<Vehicle> findByModel(String model);

    List<Vehicle> findByYear(int year);

    List<Vehicle> findByYearBetween(int from, int to);

    List<Vehicle> findByCylinders(int cylinders);

    List<Vehicle> findByFuelType(String type);
}