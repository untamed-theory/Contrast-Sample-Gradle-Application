'use strict';

angular.module('VehicleMPG').controller('VehicleController', function ($scope, vehicle) {
    $scope.vehicles = [];
    $scope.filterForm = {fromYear: 1990, toYear: 2000, make: '', cylinders: null, displacement: null};
    $scope.info = {makes: [], selectedMPG: 'averageMPG'};
    $scope.alerts = [];

    $scope.populateData = function () {
        vehicle.getAllVehicles()
            .then(
                function (data) {
                    $scope.vehicles = data;
                    $scope.createChart();
                },
                function (err) {
                    console.error(err);
                }
            );

        vehicle.getVehicleMakes()
            .then(
                function (data) {
                    $scope.info.makes = data;
                },
                function (err) {
                    console.error(err);
                }
            );
    };

    $scope.validateForm = function () {
        $scope.alerts = []; // clear previous alerts

        if (isNaN($scope.filterForm.cylinders) || parseInt($scope.filterForm.cylinders) <= 0) {
            $scope.alerts.push("Cylinders must be a positive integer.")
        }

        if (isNaN($scope.filterForm.fromYear) || parseInt($scope.filterForm.fromYear) <= 0) {
            $scope.alerts.push("From year must be a positive integer.")
        }

        if (isNaN($scope.filterForm.toYear) || parseInt($scope.filterForm.toYear) <= 0) {
            $scope.alerts.push("To year must be a positive integer.")
        }

        if (parseInt($scope.filterForm.fromYear) > parseInt($scope.filterForm.toYear)) {
            $scope.alerts.push("From year cannot be after to year.")
        }

        return $scope.alerts.length === 0;
    };

    $scope.selectMPGType = function (type) {
        $scope.info.selectedMPG = type;

        // remove previous chart
        d3.select("svg").remove();

        // repopulate data
        $scope.createChart();
    };

    $scope.filter = function () {

        if ($scope.validateForm() === false) {
            return;
        }

        vehicle.filter($scope.filterForm.make, $scope.filterForm.fromYear, $scope.filterForm.toYear)
            .then(
                function (data) {
                    $scope.vehicles = data;

                    // remove previous chart
                    d3.select("svg").remove();

                    // repopulate data
                    $scope.createChart();
                },
                function (err) {
                    console.error(err);
                }
            );

    };

    $scope.createChart = function () {
        // Set the dimensions of the canvas / graph
        var margin = {top: 30, right: 20, bottom: 30, left: 50},
            width = 1200 - margin.left - margin.right,
            height = 600 - margin.top - margin.bottom;

        // Set the ranges
        var x = d3.scale.linear().range([0, width]);
        var y = d3.scale.linear().range([height, 0]);

        // Define the axes
        var xAxis = d3.svg.axis().scale(x).orient("bottom").tickFormat(d3.format("d"));

        var yAxis = d3.svg.axis().scale(y).ticks(10).orient("left");

        // Adds the svg canvas
        var svg = d3.select("#yearly-change-chart")
            .append("svg")
            .attr("width", width + margin.left + margin.right)
            .attr("height", height + margin.top + margin.bottom)
            .append("g")
            .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        // Scale the range of the data
        // pad 5 years on either side
        x.domain([d3.min($scope.vehicles, function (d) {
            return d.year;
        }) - 5, d3.max($scope.vehicles, function (d) {
            return d.year;
        }) + 5]);

        y.domain([0, d3.max($scope.vehicles, function (d) {
            return d[$scope.info.selectedMPG];
        })]);

        // add data to canvas and fill
        svg.selectAll("dot")
            .data($scope.vehicles)
            .enter()
            .append("circle")
            .attr("class", "dot")
            .attr("r", 3)
            .attr("cx", function (d) {
                return x(d.year);
            })
            .attr("cy", function (d) {
                return y(d[$scope.info.selectedMPG]);
            })
            .style("fill", function (d, i) {
                if (d[$scope.info.selectedMPG] <= 15) {
                    return "red";
                } else if (d[$scope.info.selectedMPG] > 15 && d[$scope.info.selectedMPG] < 30) {
                    return "orange";
                } else {
                    return "green";
                }
            });

        // Add the X Axis
        svg.append("g")
            .attr("class", "x axis")
            .attr("transform", "translate(0," + height + ")")
            .call(xAxis)
            .append("text")
            .attr("class", "label")
            .attr("y", -margin.right)
            .style("text-anchor", "start")
            .text("Year");

        // Add the Y Axis
        svg.append("g")
            .attr("class", "y axis")
            .attr("transform", "translate(" + width + ",0)")
            .call(yAxis)
            .append("text")
            .attr("class", "label")
            .attr("x", -100)
            .attr("y", margin.right)
            .attr("transform", "rotate(-90)")
            .text("Miles per gallon");
    };

    $scope.populateData();
});