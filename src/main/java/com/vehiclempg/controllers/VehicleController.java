package com.vehiclempg.controllers;


import com.vehiclempg.models.Vehicle;
import com.vehiclempg.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/vehicles", method = RequestMethod.GET)
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * Retrieves all vehicles in repository
     *
     * @return List of Vehicles
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Vehicle>> getAllVehicles() {

        List<Vehicle> vehicles = vehicleService.getAllVehicles()
                .stream()
                .limit(50)
                .collect(Collectors.toList());

        // for testing purposes
        //        .stream()
        //        .limit(50)
        //        .collect(Collectors.toList());

        if (vehicles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    /**
     * Retrieves vehicles of certain make
     *
     * @param make make of vehicle
     * @return List of Vehicles
     */
    @RequestMapping(value = "/make", method = RequestMethod.GET)
    public ResponseEntity<List<Vehicle>> getVehiclesByMake(@RequestParam(value = "make") String make) {
        List<Vehicle> vehicles = vehicleService.findByMake(make);

        if (vehicles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    /**
     * Retrieves vehicles of certain model
     *
     * @param model model of vehicle
     * @return List of Vehicles
     */
    @RequestMapping(value = "/model", method = RequestMethod.GET)
    public ResponseEntity<List<Vehicle>> getVehiclesByModel(@RequestParam(value = "model") String model) {
        List<Vehicle> vehicles = vehicleService.findByModel(model);

        if (vehicles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    /**
     * Retrieves vehicle of a certain year.
     *
     * @param year year of vehicle
     * @return List of Vehicles
     */
    @RequestMapping(value = "/year", method = RequestMethod.GET)
    public ResponseEntity<List<Vehicle>> getVehiclesByYear(@RequestParam(value = "year") int year) {
        List<Vehicle> vehicles = vehicleService.findByYear(year);

        if (vehicles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    /**
     * Retrieves vehicles between the two years
     *
     * @param fromYear year to filter on
     * @param toYear   End year to filter on
     * @return List of Vehicles
     */
    @RequestMapping(value = "/years", method = RequestMethod.GET)
    public ResponseEntity<List<Vehicle>> getVehiclesBetweenYears(@RequestParam(value = "from") int fromYear,
                                                                 @RequestParam(value = "to") int toYear) {
        List<Vehicle> vehicles = vehicleService.findByYearBetween(fromYear, toYear);

        if (vehicles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    /**
     * Retrieves all vehicles with number of cylinders.
     *
     * @param cylinders number of cylinders
     * @return List of Vehicle
     */
    @RequestMapping(value = "/cylinders", method = RequestMethod.GET)
    public ResponseEntity<List<Vehicle>> getVehiclesByModel(@RequestParam(value = "cylinders") int cylinders) {
        List<Vehicle> vehicles = vehicleService.findByCylinders(cylinders);

        if (vehicles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    /**
     * Retrieves all vehicles of a certain fuel type.
     *
     * @param type type of fuel
     * @return List of Vehicles
     */
    @RequestMapping(value = "/fuel", method = RequestMethod.GET)
    public ResponseEntity<List<Vehicle>> getVehiclesByFuelType(@RequestParam(value = "type") String type) {
        List<Vehicle> vehicles = vehicleService.findByFuelType(type);

        if (vehicles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    /**
     * Retrieves all unique vehicle makes.
     *
     * @return List of Vehicle makes
     */
    @RequestMapping(value = "/makes", method = RequestMethod.GET)
    public ResponseEntity<List<Vehicle>> getVehicleMakes() {

        List<Vehicle> makes = mongoTemplate.getCollection("vehicle").distinct("make");

        return new ResponseEntity<>(makes, HttpStatus.OK);
    }

    /**
     * Filter based on multiple makes.
     *
     * @return List of Vehicles
     */
    @RequestMapping(value = "/compare", method = RequestMethod.GET)
    public ResponseEntity<List<Vehicle>> compareVehicleMakes(@RequestParam(value = "makes") String makes) {

        List<String> splitMakes = Arrays.asList(makes.split(","));

        Query query = new Query();
        query.addCriteria(Criteria.where("make").in(splitMakes));

        List<Vehicle> vehicles = mongoTemplate.find(query, Vehicle.class);

        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }

    /**
     * Filters the vehicles returned.
     *
     * @param make     Vehicle make
     * @param fromYear Beginning year to filter on
     * @param toYear   End year to filter on
     * @return List of filtered Vehicles
     */
    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public ResponseEntity<List<Vehicle>> filter(@RequestParam(value = "make", required = false) String make,
                                                @RequestParam(value = "cylinders", required = false) Integer cylinders,
                                                @RequestParam(value = "from", required = false) Integer fromYear,
                                                @RequestParam(value = "to", required = false) Integer toYear) {
        // Empty Query
        Query query = new Query();

        // filter on make
        if (make != null) {
            query.addCriteria(Criteria.where("make").is(make));
        }

        // filter on cylinders
        if (cylinders != null) {
            query.addCriteria(Criteria.where("cylinders").is(cylinders));
        }

        // filter on year(s)
        // Options: no years, 1 year, or both years
        if (fromYear != null && toYear != null) {
            query.addCriteria(Criteria.where("year").gte(fromYear).andOperator(Criteria.where("year").lte(toYear)));
        } else if (fromYear != null) {
            query.addCriteria(Criteria.where("year").gte(fromYear));
        } else if (toYear != null) {
            query.addCriteria(Criteria.where("year").lte(toYear));
        }

        List<Vehicle> vehicles = mongoTemplate.find(query, Vehicle.class);

        if (vehicles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(vehicles, HttpStatus.OK);
    }
}