'use strict';

angular.module('VehicleMPG').controller('VehicleController', function ($scope, vehicle, ngProgressFactory) {
    $scope.vehicles = [];
    $scope.filterForm = {fromYear: 1990, toYear: 2000, make: '', cylinders: null, displacement: null};
    $scope.info = {makes: [], selectedMPG: 'averageMPG', make: ''};
    $scope.alerts = [];
    $scope.compareMakes = [];
    $scope.averages = [];

    // progress bar
    $scope.progressbar = ngProgressFactory.createInstance();

    $scope.populateData = function () {
        $scope.progressbar.start();

        vehicle.getAllVehicles()
            .then(
                function (data) {
                    $scope.vehicles = data;
                    $scope.createPlotChart();

                    $scope.progressbar.complete();
                },
                function (err) {
                    console.error(err);

                    $scope.progressbar.complete();

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
        d3.select("#plot-chart svg").remove();

        // repopulate data
        $scope.createPlotChart();
    };

    $scope.addMakeToCompare = function () {
        if ($scope.compareMakes.indexOf($scope.info.make) === -1) {
            $scope.compareMakes.push($scope.info.make);
        }
    };

    $scope.clearCompareMakes = function () {
        $scope.compareMakes = [];
    };

    $scope.removeMake = function(make) {
        $scope.compareMakes.splice(make, 1);
    };

    $scope.compare = function () {
        vehicle.compareVehicleMakes($scope.compareMakes)
            .then(
                function (data) {
                    $scope.vehicles = data;

                    // remove previous chart
                    d3.select("#plot-chart svg").remove();

                    // repopulate data
                    $scope.createPlotChart();
                },
                function (err) {
                    console.error(err);
                }
            );
    };

    $scope.filter = function () {

        if ($scope.validateForm() === false) {
            return;
        }

        vehicle.filter($scope.filterForm)
            .then(
                function (data) {
                    $scope.vehicles = data;

                    // remove previous chart
                    d3.select("#plot-chart svg").remove();

                    // repopulate data
                    $scope.createPlotChart();
                },
                function (err) {
                    console.error(err);
                }
            );

    };

    $scope.clear = function () {
        $scope.filterForm = {fromYear: null, toYear: null, make: null, cylinders: null, displacement: null};
        $scope.vehicles = [];
        $scope.compareMakes = [];

        vehicle.getAllVehicles()
            .then(
                function (data) {
                    $scope.vehicles = data;

                    // remove previous chart
                    d3.select("#plot-chart svg").remove();

                    $scope.createPlotChart();
                },
                function (err) {
                    console.error(err);
                }
            );
    };

    $scope.getAverages = function () {
        vehicle.getAverages($scope.info.selectedMPG, $scope.compareMakes)
            .then(
                function (data) {
                    $scope.averages = data;

                    // remove previous chart
                    d3.select("#average-chart svg").remove();

                    // repopulate data
                    $scope.createAverageChart();
                },
                function (err) {
                    console.error(err);
                }
            );

    };

    // tab toggle
    $('a[data-toggle="tab"]').on('show.bs.tab', function (e) {
        if (e.target.hash === "#plot") {
            d3.select("#plot-chart svg").remove();
            $scope.createPlotChart();
        } else if (e.target.hash === "#average") {
            $scope.getAverages();
        }
    });

    $scope.createPlotChart = function () {
        // Set the dimensions of the canvas / graph
        var margin = {top: 30, right: 30, bottom: 30, left: 30},
            width = 1000 - margin.left - margin.right,
            height = 500 - margin.top - margin.bottom;

        // Set the ranges
        var x = d3.scale.linear().range([0, width]);
        var y = d3.scale.linear().range([height, 0]);

        // Define the axes
        var xAxis = d3.svg.axis().scale(x).orient("bottom").tickFormat(d3.format("d"));

        var yAxis = d3.svg.axis().scale(y).ticks(10).orient("right");

        //  tooltip
        var tooltip = d3.select("body").append("div")
            .attr("class", "tooltip")
            .style("opacity", 0);

        // Adds the svg canvas
        var svg = d3.select("#plot-chart")
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
            })
            .on("mouseover", function(d) {
                tooltip.transition()
                    .duration(200)
                    .style("opacity", .9);
                tooltip.html($scope.buildToolTip(d))
                    .style("left", (d3.event.pageX + 10) + "px")
                    .style("top", (d3.event.pageY) + "px");
            })
            .on("mouseout", function(d) {
                tooltip.transition()
                    .duration(500)
                    .style("opacity", 0);
            });

        // Add the X Axis
        svg.append("g")
                .attr("class", "x axis")
                .attr("transform", "translate(0," + height + ")")
                .call(xAxis)
            .append("text")
                .attr("class", "label")
                .attr("x", width - margin.right)
                .attr("y", -10)
                .style("text-anchor", "start")
                .text("Year");

        // Add the Y Axis
        svg.append("g")
                .attr("class", "y axis")
                .call(yAxis)
            .append("text")
                .attr("class", "label")
                .attr("x", -100)
                .attr("y", -15)
                .attr("transform", "rotate(-90)")
                .text("Miles per gallon");
    };

    $scope.createAverageChart = function () {
        // Set the dimensions of the canvas / graph
        var margin = {top: 30, right: 30, bottom: 30, left: 30},
            width = 1000 - margin.left - margin.right,
            height = 500 - margin.top - margin.bottom;

        // Set the ranges
        var x = d3.scale.linear().range([0, width]);
        var y = d3.scale.linear().range([height, 0]);

        // Define the axes
        var xAxis = d3.svg.axis().scale(x).orient("bottom").tickFormat(d3.format("d"));

        var yAxis = d3.svg.axis().scale(y).ticks(10).orient("right");

        // Adds the svg canvas
        var svg = d3.select("#average-chart")
            .append("svg")
            .attr("width", width + margin.left + margin.right)
            .attr("height", height + margin.top + margin.bottom)
            .append("g")
            .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

        //  tooltip
        var tooltip = d3.select("body").append("div")
            .attr("class", "tooltip")
            .style("opacity", 0);

        // Scale the range of the data
        // pad 5 years on either side
        x.domain([d3.min($scope.averages, function (d) {
            return d.year;
        }) - 5, d3.max($scope.averages, function (d) {
            return d.year;
        }) + 5]);

        y.domain([0, d3.max($scope.averages, function (d) {
            return d.average;
        })]);

        // group by make
        var group = d3.nest()
            .key(function(d) {
                return d.make;
            })
            .entries($scope.averages);

        // Add the X Axis
        svg.append("g")
                .attr("class", "x axis")
                .attr("transform", "translate(0," + height + ")")
                .call(xAxis)
            .append("text")
                .attr("class", "label")
                .attr("x", width - margin.right)
                .attr("y", -10)
                .style("text-anchor", "start")
                .text("Year");

        // Add the Y Axis
        svg.append("g")
                .attr("class", "y axis")
                .call(yAxis)
            .append("text")
                .attr("class", "label")
                .attr("x", -100)
                .attr("y", -15)
                .attr("transform", "rotate(-90)")
                .text("Miles per gallon");

        // iterate over all groups and add a line for each make
        group.forEach(function(d, i) {
            var line = d3.svg.line()
                .interpolate("basis")
                .x(function(d) { return x(d.year); })
                .y(function(d) { return y(d.average); });

            svg.append('path')
                .datum(d.values)
                .attr('d', line)
                .attr("class", "line")
                .on("mouseover", function(d) {
                    tooltip.transition()
                        .duration(200)
                        .style("opacity", .9);
                    tooltip.html("<Strong>Vehicle Make: </strong>" + d[0].make)
                        .style("left", (d3.event.pageX + 10) + "px")
                        .style("top", (d3.event.pageY) + "px");
                })
                .on("mouseout", function(d) {
                    tooltip.transition()
                        .duration(500)
                        .style("opacity", 0);
                });
        });
    };

    $scope.buildToolTip = function(v) {
        var div = "<strong>" + v.year + " " + v.make + " " + v.model + "</strong>";

        div += "<br>";
        div += "Cylinders: " + v.cylinders;
        div += "<br>";
        div += "Average MPG: " + v.averageMPG;
        div += "<br>";
        div += "City MPG: " + v.cityMPG;
        div += "<br>";
        div += "Highway MPG: " + v.highwayMPG;

        return div;
    };

    $scope.populateData();
});