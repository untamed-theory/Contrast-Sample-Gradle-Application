'use strict';

angular.module('VehicleMPG').service('vehicle', function ($http, $q) {

    return {

        getAllVehicles: function () {
            return $http.get('url for vehicles')
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