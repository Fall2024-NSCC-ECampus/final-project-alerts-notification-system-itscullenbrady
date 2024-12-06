package com.example.alertnotificationsystem.controller;

import com.example.alertnotificationsystem.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/persons")
@Slf4j // Adds logging capabilities to this controller
public class PersonController {

    @Autowired
    private PersonService personService;

    /**
     * Fetches a list of all persons.
     * @return A list of all persons.
     */
    @GetMapping
    public List<Map<String, Object>> getAllPersons() {
        log.info("Fetching all persons.");
        return personService.getAllPersons();
    }

    /**
     * Fetches a specific person by their ID.
     * @param id The ID of the person to search for.
     * @return The details of the person or 404 if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getPersonById(@PathVariable Long id) {
        log.info("Fetching person by ID: {}", id);
        Map<String, Object> person = personService.getPersonById(id);
        return person != null ? ResponseEntity.ok(person) : ResponseEntity.notFound().build();
    }

    /**
     * Fetches fire-related details for a specific address.
     * @param address The address to search for fire-related details.
     * @return Fire details for the address or 404 if not found.
     */
    @GetMapping("/fire")
    public ResponseEntity<Map<String, Object>> getFireDetailsByAddress(@RequestParam String address) {
        log.info("Fetching fire details for address: {}", address);
        Map<String, Object> fireDetails = personService.getFireDetailsByAddress(address);
        return fireDetails != null ? ResponseEntity.ok(fireDetails) : ResponseEntity.notFound().build();
    }

    /**
     * Fetches community emails for a specific city.
     * @param city The city to search for.
     * @return A list of email addresses of people living in the given city.
     */
    @GetMapping("/communityEmail")
    public ResponseEntity<List<String>> getCommunityEmailsByCity(@RequestParam String city) {
        log.info("Fetching community emails for city: {}", city);
        List<String> emails = personService.getCommunityEmailsByCity(city);
        return emails.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(emails);
    }

    /**
     * Fetches persons by their first name and last name.
     * @param firstName The first name of the person.
     * @param lastName The last name of the person.
     * @return List of persons matching the first and last name.
     */
    @GetMapping("/personInfo")
    public ResponseEntity<List<Map<String, Object>>> getPersonInfoByName(@RequestParam String firstName, @RequestParam String lastName) {
        log.info("Fetching persons by name: {} {}", firstName, lastName);
        List<Map<String, Object>> persons = personService.getPersonsByName(firstName, lastName);
        return persons.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(persons);
    }
}
