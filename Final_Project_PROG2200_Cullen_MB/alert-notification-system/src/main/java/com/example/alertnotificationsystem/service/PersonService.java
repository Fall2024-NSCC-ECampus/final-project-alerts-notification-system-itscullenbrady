package com.example.alertnotificationsystem.service;

import com.example.alertnotificationsystem.model.Person;
import com.example.alertnotificationsystem.repository.FireStationRepository;
import com.example.alertnotificationsystem.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j // Adds logging capabilities to this service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private FireStationRepository fireStationRepository;

    /**
     * Fetches all persons from the database.
     * @return List of maps where each map contains person details.
     */
    public List<Map<String, Object>> getAllPersons() {
        log.info("Fetching all persons.");
        return personRepository.findAll().stream()
                .map(this::convertPersonToMap)
                .collect(Collectors.toList());
    }

    /**
     * Fetches a specific person by ID.
     * @param id The ID of the person to fetch.
     * @return A map containing the person's details or null if not found.
     */
    public Map<String, Object> getPersonById(Long id) {
        log.info("Fetching person by ID: {}", id);
        return personRepository.findById(id)
                .map(this::convertPersonToMap)
                .orElse(null);
    }

    /**
     * Fetches persons based on their address.
     * @param address The address to search for.
     * @return List of maps, each containing details of persons at the given address.
     */
    public List<Map<String, Object>> getPersonsByAddress(String address) {
        log.info("Fetching persons by address: {}", address);
        return personRepository.findByAddress(address).stream()
                .map(this::convertPersonToMap)
                .collect(Collectors.toList());
    }

    /**
     * Fetches persons based on their city.
     * @param city The city to search for.
     * @return List of maps, each containing details of persons in the given city.
     */
    public List<Map<String, Object>> getPersonsByCity(String city) {
        log.info("Fetching persons by city: {}", city);
        return personRepository.findByCity(city).stream()
                .map(this::convertPersonToMap)
                .collect(Collectors.toList());
    }

    /**
     * Saves a new person to the database.
     * @param personData A map containing the person's data to be saved.
     * @return A map containing the saved person's details.
     */
    public Map<String, Object> savePerson(Map<String, Object> personData) {
        log.info("Saving a new person: {}", personData);
        Person person = convertMapToPerson(personData);
        Person savedPerson = personRepository.save(person);
        return convertPersonToMap(savedPerson);
    }

    /**
     * Updates an existing person in the database.
     * @param id The ID of the person to update.
     * @param updatedPersonData A map containing the updated person's data.
     * @return A map containing the updated person's details or null if not found.
     */
    public Map<String, Object> updatePerson(Long id, Map<String, Object> updatedPersonData) {
        log.info("Updating person with ID: {}", id);
        Optional<Person> personOptional = personRepository.findById(id);
        if (personOptional.isPresent()) {
            Person person = personOptional.get();
            updatePersonFields(person, updatedPersonData);
            Person savedPerson = personRepository.save(person);
            return convertPersonToMap(savedPerson);
        }
        return null;
    }

    /**
     * Deletes a person by ID.
     * @param id The ID of the person to delete.
     * @return True if the person was deleted, false if not found.
     */
    public boolean deletePerson(Long id) {
        log.info("Deleting person with ID: {}", id);
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Fetches community emails for a specific city.
     * @param city The city to search for.
     * @return A list of email addresses of people living in the given city.
     */
    public List<String> getCommunityEmailsByCity(String city) {
        log.info("Fetching community emails for city: {}", city);
        return personRepository.findByCity(city).stream()
                .map(Person::getEmail)
                .collect(Collectors.toList());
    }

    /**
     * Fetches persons by their first name and last name.
     * @param firstName The first name of the person.
     * @param lastName The last name of the person.
     * @return List of persons matching the first and last name.
     */
    public List<Map<String, Object>> getPersonsByName(String firstName, String lastName) {
        log.info("Fetching persons by name: {} {}", firstName, lastName);
        return personRepository.findByFirstNameAndLastName(firstName, lastName).stream()
                .map(this::convertPersonToMap)
                .collect(Collectors.toList());
    }

    /**
     * Fetches fire details for a specific address.
     * @param address The address to get fire details for.
     * @return A map containing the fire station number and resident details or null if not found.
     */
    public Map<String, Object> getFireDetailsByAddress(String address) {
        log.info("Fetching fire details for address: {}", address);

        // Fetch the fire station serving the given address
        var fireStation = fireStationRepository.findByAddress(address);
        if (fireStation.isEmpty()) {
            log.warn("No fire station found for address: {}", address);
            return null;
        }

        // Fetch the residents living at the address
        List<Person> residents = personRepository.findByAddress(address);

        // Map resident details to a response format
        List<Map<String, Object>> residentDetails = residents.stream()
                .map(resident -> {
                    Map<String, Object> personMap = new HashMap<>();
                    personMap.put("name", resident.getFirstName() + " " + resident.getLastName());
                    personMap.put("phone", resident.getPhone());
                    personMap.put("age", resident.getAge());
                    personMap.put("medications", resident.getMedications());
                    personMap.put("allergies", resident.getAllergies());
                    return personMap;
                })
                .collect(Collectors.toList());

        // Construct the response containing fire station number and residents
        Map<String, Object> response = new HashMap<>();
        response.put("stationNumber", fireStation.get(0).getStationNumber());
        response.put("residents", residentDetails);

        return response;
    }

    // Helper method to convert a Person entity to a map
    private Map<String, Object> convertPersonToMap(Person person) {
        Map<String, Object> personMap = new HashMap<>();
        personMap.put("id", person.getId());
        personMap.put("firstName", person.getFirstName());
        personMap.put("lastName", person.getLastName());
        personMap.put("address", person.getAddress());
        personMap.put("city", person.getCity());
        personMap.put("phone", person.getPhone());
        personMap.put("email", person.getEmail());
        personMap.put("age", person.getAge());
        personMap.put("medications", person.getMedications());
        personMap.put("allergies", person.getAllergies());
        return personMap;
    }

    // Helper method to convert a map to a Person entity
    private Person convertMapToPerson(Map<String, Object> personMap) {
        Person person = new Person();
        person.setFirstName((String) personMap.get("firstName"));
        person.setLastName((String) personMap.get("lastName"));
        person.setAddress((String) personMap.get("address"));
        person.setCity((String) personMap.get("city"));
        person.setPhone((String) personMap.get("phone"));
        person.setEmail((String) personMap.get("email"));
        person.setAge((Integer) personMap.get("age"));
        person.setMedications((String) personMap.get("medications"));
        person.setAllergies((String) personMap.get("allergies"));
        return person;
    }

    // Helper method to update an existing Person entity with new data
    private void updatePersonFields(Person person, Map<String, Object> updatedPersonData) {
        person.setFirstName((String) updatedPersonData.get("firstName"));
        person.setLastName((String) updatedPersonData.get("lastName"));
        person.setAddress((String) updatedPersonData.get("address"));
        person.setCity((String) updatedPersonData.get("city"));
        person.setPhone((String) updatedPersonData.get("phone"));
        person.setEmail((String) updatedPersonData.get("email"));
        person.setAge((Integer) updatedPersonData.get("age"));
        person.setMedications((String) updatedPersonData.get("medications"));
        person.setAllergies((String) updatedPersonData.get("allergies"));
    }
}
