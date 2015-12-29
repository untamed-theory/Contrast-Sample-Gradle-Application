package com.contrastsecurity.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import com.contrastsecurity.models.Vehicle;
import com.contrastsecurity.services.VehicleService;

@RestController
public class VehicleController {

    VehicleService vehicleService;


    @RequestMapping(value = "/vehicle/", method = RequestMethod.GET)
    public ResponseEntity<List<Vehicle>> listAllUsers() {
        List<Vehicle> users = vehicleService.findAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<List<Vehicle>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Vehicle>>(users, HttpStatus.OK);
    }

}