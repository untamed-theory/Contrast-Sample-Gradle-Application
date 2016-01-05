package com.contrastsecurity;

import com.contrastsecurity.models.Vehicle;
import com.contrastsecurity.repositories.VehicleRepository;
import com.jayway.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;

import static com.jayway.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class VehicleControllerTests {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Value("${local.server.port}")
    int port;

    @Before
    public void setUp() {
        Vehicle acura = new Vehicle(30, 22, 26, "Acura", "Integra", 2000, 4, 2.0f, "Regular");
        Vehicle ford = new Vehicle(30, 22, 26, "Ford", "Mustang", 2006, 8, 4.6f, "Premium");
        Vehicle bmw = new Vehicle(30, 22, 26, "BMW", "m3", 2010, 8, 4.0f, "Premium");

        vehicleRepository.save(Arrays.asList(acura, ford, bmw));

        // RestAssured setup
        RestAssured.port = port;
    }

    @After
    public void tearDown() {
        // remove all test vehicles
        vehicleRepository.deleteAll();
    }

    @Test
    public void testAllEmpty() {
        // delete all to see status code
        vehicleRepository.deleteAll();

        when().
                get("/vehicles/all").
                then().
                statusCode(204);
    }

    @Test
    public void testAllNotEmpty() {

        when().
                get("/vehicles/all").
                then().
                statusCode(200)
                .body("isEmpty()", is(false))
                .body("size()", is(3));
    }

    @Test
    public void testGetMake() {

        when().
                get("/vehicles/make?make=BMW").
                then().
                statusCode(200)
                .body("isEmpty()", is(false))
                .body("size()", is(1));
    }

    @Test
    public void testFilterWithAll() {

        when().
                get("/vehicles/filter?make=Ford&from=2005&to=2007").
                then().
                statusCode(200)
                .body("isEmpty()", is(false))
                .body("[0].make", equalTo("Ford"));
    }

    @Test
    public void testFilterWithMake() {

        when().
                get("/vehicles/filter?make=Acura").
                then().
                statusCode(200)
                .body("isEmpty()", is(false))
                .body("[0].make", equalTo("Acura"));
    }

    @Test
    public void testFilterWithFromYear() {

        when().
                get("/vehicles/filter?from=2008").
                then().
                statusCode(200)
                .body("isEmpty()", is(false))
                .body("[0].make", equalTo("BMW"))
                .body("size()", is(1));
    }

    @Test
    public void testFilterWithToYear() {

        when().
                get("/vehicles/filter?to=2000").
                then().
                statusCode(200)
                .body("isEmpty()", is(false))
                .body("[0].make", equalTo("Acura"))
                .body("size()", is(1));
    }

    @Test
    public void testEmptyFilter() {

        when().
                get("/vehicles/filter").
                then().
                statusCode(200)
                .body("isEmpty()", is(false))
                .body("size()", is(3));
    }

    @Test
    public void testFilterNoResults() {

        when().
                get("/vehicles/filter?to=1900").
                then().
                statusCode(204);
    }
}

