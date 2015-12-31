package com.contrastsecurity;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.boot.SpringApplication;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.contrastsecurity.repositories")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(new Class[] {Application.class, AppInitializer.class }, args);
    }
}
