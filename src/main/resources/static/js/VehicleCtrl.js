'use strict';

angular.module('VehicleMPG').controller('VehicleController', function ($scope, vehicle) {
    var self = this;
    self.vehicles = [];

    self.fetchAllVehicles = function () {
        vehicle.fetchAllVehicles()
            .then(
                function (data) {
                    self.vehicles = data;
                },
                function (errResponse) {
                    console.error('Error while fetching vehicles');
                }
            );
    };

    self.fetchAllVehicles();
});