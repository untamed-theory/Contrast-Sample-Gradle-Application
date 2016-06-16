# Contrast Gradle Plugin Sample Web Application
This web application shows a brief chart of vehicle average miles per gallon over recent history.
It also allows you to filter on basic principles like year and make to narrow down the results.

## Dependencies
* Mongo 3.2
* Java 1.8
* Maven 3.3.9
* Contrast Java SDK(Installed in local maven repo)

**Note:** you must configure the contrastConfiguration settings from your TeamServer within the build.gradle file in this project.

## To run the application
1. Start MongoDB with mongod See: https://docs.mongodb.org/manual/tutorial/manage-mongodb-processes/
2. gradle build bootRun

**Note:** Spring takes care of the database creation and assumes localhost:27017 for location
Then go to http://localhost:8080/

## Install Contrast Agent
1. gradle build contrastInstall

## Onboard with TeamServer
1. java -javaagent:build/contrast.jar -Dcontrast.appname=mytestapp -Dcontrast.server=mytestserver -jar build/libs/Contrast-Sample-Gradle-Application-0.0.1-SNAPSHOT.jar
The application will now show up on your TeamServer

## Verify with Contrast Agent
1. gradle build contrastVerify


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

