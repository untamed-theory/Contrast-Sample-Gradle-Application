# Vehicle MPG Web Application
Author: Justin Leo

For Contrast Security Assignment

This web application shows a brief chart of vehicle average miles per gallon over recent history.
It also allows you to filter on basic principles like year and make to narrow down the results.

This application utilizes the Spring library for Java to retrieve and filter vehicle data in a REST tier.

In the front end, Bootstrap is used for CSS and AngularJs is used to display, filter, and store the data.

The data store is MongoDB since the data is easily stored as a document.

When the app is first run, it checks to see if data is stored in Mongo from a previous install,
else it loads the vehicle.csv and maps the columns to a Vehicle object that represents a MongoDB document.

There are many other filters in the controllers like cylinders and city MPG but are not shown on the page.

## Dependencies
* Mongo 3.2
* Java 1.8
* Maven 3.3.9

## To run the application
1. Start MongoDB with mongod See: https://docs.mongodb.org/manual/tutorial/manage-mongodb-processes/
2. mvn clean spring-boot:run

Note: Spring takes care of the database creation and assumes localhost:27017 for location

Then go to http://localhost:8080/

## To run the tests
mvn test

## CSV Resource
https://www.fueleconomy.gov/feg/download.shtml

## Resources
https://spring.io/guides/tutorials/spring-security-and-angular-js/
http://docs.spring.io/spring-data/mongodb/docs/current/reference/html/
http://g00glen00b.be/spring-boot-rest-assured/
http://bl.ocks.org/weiglemc/6185069
http://victorbjelkholm.github.io/ngProgress/

## Future:
- more charts
- statistics on MPG