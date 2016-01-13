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
        getVehicleMakes: function () {
            return $http.get('/vehicles/makes')
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse);
                    }
                );
        },
        getAverages: function (type, makes) {
            var url = '/vehicles/averages?type=' + type;

            if (makes.length !== 0) {
                url += '&makes=' + makes.join(",");
            }

            return $http.get(url)
                .then(
                    function (response) {
                        return response.data;
                    },
                    function (errResponse) {
                        return $q.reject(errResponse);
                    }
                );
        },
        filter: function (form, mpg) {

            var url = '/vehicles/filter?mpg=' + mpg + '&';
            var options = [];

            if (form.makes) {
                options.push('makes=' + form.makes.join(','));
            }

            if (form.cylinders) {
                options.push('cylinders=' + form.cylinders)
            }

            if (form.fromYear) {
                options.push('from=' + form.fromYear);
            }

            if (form.toYear) {
                options.push('to=' + form.toYear);
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