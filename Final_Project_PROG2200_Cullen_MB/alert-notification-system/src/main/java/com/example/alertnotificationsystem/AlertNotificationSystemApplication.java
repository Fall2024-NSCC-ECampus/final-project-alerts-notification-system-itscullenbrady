package com.example.alertnotificationsystem;

import com.example.alertnotificationsystem.service.FireStationService;
import com.example.alertnotificationsystem.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class AlertNotificationSystemApplication implements CommandLineRunner {

    @Autowired
    private PersonService personService;

    @Autowired
    private FireStationService fireStationService;

    public static void main(String[] args) {
        SpringApplication.run(AlertNotificationSystemApplication.class, args);
    }

    @Override
    public void run(String... args) {

        // Initialize test persons
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

        Map<String, Object> person3 = new HashMap<>();
        person3.put("firstName", "Alice");
        person3.put("lastName", "Johnson");
        person3.put("address", "789 Oak St");
        person3.put("city", "Shelbyville");
        person3.put("phone", "555-555-5555");
        person3.put("email", "alice.j@example.com");
        person3.put("age", 12);
        person3.put("medications", "");
        person3.put("allergies", "dust");

        // Save persons
        Arrays.asList(person1, person2, person3).forEach(personService::savePerson);

        // Initialize test fire stations
        Map<String, Object> fireStation1 = new HashMap<>();
        fireStation1.put("stationNumber", 1);
        fireStation1.put("address", "123 Main St");

        Map<String, Object> fireStation2 = new HashMap<>();
        fireStation2.put("stationNumber", 2);
        fireStation2.put("address", "456 Elm St");

        // Save fire stations
        Arrays.asList(fireStation1, fireStation2).forEach(fireStationService::saveFireStation);

        // Print test data for verification
        System.out.println("All Persons: " + personService.getAllPersons());
        System.out.println("All Fire Stations: " + fireStationService.getAllFireStations());
    }
}
