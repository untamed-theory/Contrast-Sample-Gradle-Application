package com.contrastsecurity;


import com.contrastsecurity.models.Vehicle;
import com.contrastsecurity.repositories.VehicleRepository;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.File;
import java.io.IOException;
import java.util.Map;


@Configuration
public class AppInitializer implements ServletContextInitializer {

    static Logger log = Logger.getLogger(AppInitializer.class.getName());

    @Autowired
    private VehicleRepository repository;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        CsvMapper mapper = new CsvMapper();
        mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
        File csvFile = new File("small_vehicles.csv");
        MappingIterator<Map.Entry> it = null;

        CsvSchema schema = CsvSchema.emptySchema().withHeader();

        try {
            it = mapper.readerFor(Vehicle.class).with(schema).readValues(csvFile);
        } catch (IOException e) {
            log.error("Unable to read in file.");
            return;
        }

        if (it.hasNext()) {
            it.next(); // skip header
        }

        while (it.hasNext()) {
            Vehicle vehicle = (Vehicle) it.next();

            repository.save(vehicle);
        }
    }
}
