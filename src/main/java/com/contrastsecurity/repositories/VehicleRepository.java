package com.contrastsecurity.repositories;


import java.util.List;

import com.contrastsecurity.models.Vehicle;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VehicleRepository extends MongoRepository<Vehicle, String> {

    public List<Vehicle> findByMake(String make);
    public List<Vehicle> findByYear(int year);

}