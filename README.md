# Vehicle MPG Web Application
Author: Justin Leo

This web application shows a brief chart of vehicle average miles per gallon over recent history.
It also allows you to filter on basic principles like year and make to narrow down the results.

This application utilizes the Spring library for Java to retrieve and filter vehicle data in a REST tier.

In the front end, Bootstrap is used for CSS and AngularJs is used to display, filter, and store the data.

The data store is MongoDB since the data is easily stored as a document.

When the app is first run, it checks to see if data is stored in Mongo from a previous install,
else it loads the vehicle.csv and maps the columns to a Vehicle object that represents a MongoDB document.

## Dependencies
* Mongo 3.2
* Java 1.8
* Maven 3.3.9
* Contrast Java SDK(Installed in local maven repo)

**Note:** you must configure the correct contrastConfiguration settings within the build.gradle file in this project.

## To run the application
1. Start MongoDB with mongod See: https://docs.mongodb.org/manual/tutorial/manage-mongodb-processes/
2. gradle build bootRun

**Note:** Spring takes care of the database creation and assumes localhost:27017 for location
Then go to http://localhost:8080/

## Install Contrast Agent
1. gradle contrastInstall

## Verify with Contrast Agent
1. gradle build contrastVerify

##Run with Contrast
1. gradle build contrastInstall contrastVerify


## To run the tests
gradle test

## CSV Resource
https://www.fueleconomy.gov/feg/download.shtml

## Resources
* https://spring.io/guides/tutorials/spring-security-and-angular-js/
* http://docs.spring.io/spring-data/mongodb/docs/current/reference/html/
* http://g00glen00b.be/spring-boot-rest-assured/
* http://bl.ocks.org/weiglemc/6185069
* http://victorbjelkholm.github.io/ngProgress/

## Future:
- statistics on MPG
- update average filter to use year and cylinders
