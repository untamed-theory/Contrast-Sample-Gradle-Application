'use strict';

angular.module('VehicleMPG').service('vehicle', function ($http, $q) {

    return {
        getAllVehicles: function () {
            return $http.get('/vehicles/all')
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse);
                    }
                );
        },
        getVehiclesByMake: function (make) {
            return $http.get('/vehicles/make?make=' + make)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse);
                    }
                );
        },
        getVehicleStats: function () {
            return $http.get('/vehicles/stats')
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse);
                    }
                );
        },
        filter: function (make, fromYear, toYear) {

            var url = '/vehicles/filter?';
            var options = [];

            if (make) {
               options.push('make=' + make);
            }

            if (fromYear) {
                options.push('from=' + fromYear);
            }

            if (toYear) {
                options.push('to=' + toYear);
            }

            url += options.join('&');

            return $http.get(url)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse);
                    }
                );
        }
    };
});