package com.example.alertnotificationsystem;

import com.example.alertnotificationsystem.service.FireStationService;
import com.example.alertnotificationsystem.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TestDataRunner implements CommandLineRunner {

    @Autowired
    private PersonService personService;

    @Autowired
    private FireStationService fireStationService;

    @Override
    public void run(String... args) {
        // Add test person data
        Map<String, Object> person1 = new HashMap<>();
        person1.put("firstName", "John");
        person1.put("lastName", "Doe");
        person1.put("address", "123 Main St");
        person1.put("city", "Springfield");
        person1.put("phone", "123-456-7890");
        person1.put("email", "john.doe@example.com");
        person1.put("age", 30);
        person1.put("medications", "aspirin,ibuprofen");
        person1.put("allergies", "peanuts");

        Map<String, Object> person2 = new HashMap<>();
        person2.put("firstName", "Jane");
        person2.put("lastName", "Smith");
        person2.put("address", "456 Elm St");
        person2.put("city", "Springfield");
        person2.put("phone", "987-654-3210");
        person2.put("email", "jane.smith@example.com");
        person2.put("age", 25);
        person2.put("medications", "paracetamol");
        person2.put("allergies", "");

        personService.savePerson(person1);
        personService.savePerson(person2);

        // Add test fire station data
        Map<String, Object> fireStation1 = new HashMap<>();
        fireStation1.put("stationNumber", 1);
        fireStation1.put("address", "123 Main St");

        Map<String, Object> fireStation2 = new HashMap<>();
        fireStation2.put("stationNumber", 2);
        fireStation2.put("address", "456 Elm St");

        fireStationService.saveFireStation(fireStation1);
        fireStationService.saveFireStation(fireStation2);

        // Log test data for verification
        System.out.println("Test Data Initialized:");
        System.out.println("Persons: " + personService.getAllPersons());
        System.out.println("Fire Stations: " + fireStationService.getAllFireStations());
    }
}
