package com.example.alertnotificationsystem.service;

import com.example.alertnotificationsystem.model.FireStation;
import com.example.alertnotificationsystem.model.Person;
import com.example.alertnotificationsystem.repository.FireStationRepository;
import com.example.alertnotificationsystem.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FireStationService {

    @Autowired
    private FireStationRepository fireStationRepository;

    @Autowired
    private PersonRepository personRepository;

    /**
     * Get all fire stations.
     * @return List of fire station data.
     */
    public List<Map<String, Object>> getAllFireStations() {
        log.info("Fetching all fire stations.");
        return fireStationRepository.findAll().stream()
                .map(this::convertFireStationToMap)
                .collect(Collectors.toList());
    }

    /**
     * Get fire stations by station number.
     * @param stationNumber Fire station number.
     * @return List of fire station data.
     */
    public List<Map<String, Object>> getFireStationsByNumber(int stationNumber) {
        log.info("Fetching fire stations with station number: {}", stationNumber);
        return fireStationRepository.findByStationNumber(stationNumber).stream()
                .map(this::convertFireStationToMap)
                .collect(Collectors.toList());
    }

    /**
     * Get people served by a fire station and summary of adults/children.
     * @param stationNumber Fire station number.
     * @return People and summary data.
     */
    public Map<String, Object> getPeopleByStationNumber(int stationNumber) {
        log.info("Fetching people served by fire station number: {}", stationNumber);

        List<String> addresses = fireStationRepository.findByStationNumber(stationNumber)
                .stream()
                .map(FireStation::getAddress)
                .collect(Collectors.toList());

        List<Map<String, Object>> people = personRepository.findByAddressIn(addresses).stream()
                .map(this::convertPersonToSummaryMap)
                .collect(Collectors.toList());

        long adults = people.stream().filter(p -> (int) p.get("age") > 18).count();
        long children = people.size() - adults;

        Map<String, Object> result = new HashMap<>();
        result.put("people", people);
        result.put("adults", adults);
        result.put("children", children);

        return result;
    }

    /**
     * Get details of people living at an address and the servicing fire station number.
     * @param address Address to search.
     * @return Fire station and resident details.
     */
    public Map<String, Object> getFireDetailsByAddress(String address) {
        log.info("Fetching fire details for address: {}", address);

        FireStation fireStation = fireStationRepository.findByAddress(address).stream().findFirst().orElse(null);
        if (fireStation == null) {
            return null;
        }

        List<Map<String, Object>> residents = personRepository.findByAddress(address).stream()
                .map(this::convertPersonToDetailedMap)
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("stationNumber", fireStation.getStationNumber());
        result.put("residents", residents);

        return result;
    }

    /**
     * Get household details for multiple fire stations.
     * @param stationNumbers List of fire station numbers.
     * @return Grouped households by address.
     */
    public Map<String, List<Map<String, Object>>> getFloodStations(List<Integer> stationNumbers) {
        log.info("Fetching flood data for fire stations: {}", stationNumbers);

        List<FireStation> fireStations = fireStationRepository.findByStationNumberIn(stationNumbers);

        Map<String, List<Map<String, Object>>> floodData = new HashMap<>();
        for (FireStation fireStation : fireStations) {
            String address = fireStation.getAddress();
            List<Map<String, Object>> residents = personRepository.findByAddress(address).stream()
                    .map(this::convertPersonToDetailedMap)
                    .collect(Collectors.toList());
            floodData.put(address, residents);
        }

        return floodData;
    }

    /**
     * Save a new fire station.
     * @param fireStation Fire station data.
     * @return Saved fire station data.
     */
    public Map<String, Object> saveFireStation(Map<String, Object> fireStation) {
        log.info("Saving a new fire station: {}", fireStation);

        FireStation station = new FireStation();
        station.setStationNumber((Integer) fireStation.get("stationNumber"));
        station.setAddress((String) fireStation.get("address"));

        FireStation savedStation = fireStationRepository.save(station);

        return convertFireStationToMap(savedStation);
    }

    /**
     * Update an existing fire station.
     * @param id Fire station ID.
     * @param updatedFireStation Updated fire station data.
     * @return Updated fire station data.
     */
    public Map<String, Object> updateFireStation(Long id, Map<String, Object> updatedFireStation) {
        log.info("Updating fire station with ID: {}", id);

        return fireStationRepository.findById(id)
                .map(existingStation -> {
                    existingStation.setStationNumber((Integer) updatedFireStation.get("stationNumber"));
                    existingStation.setAddress((String) updatedFireStation.get("address"));
                    FireStation savedStation = fireStationRepository.save(existingStation);
                    return convertFireStationToMap(savedStation);
                })
                .orElse(null);
    }

    /**
     * Delete a fire station by ID.
     * @param id Fire station ID.
     * @return Whether the fire station was deleted.
     */
    public boolean deleteFireStation(Long id) {
        log.info("Deleting fire station with ID: {}", id);

        if (fireStationRepository.existsById(id)) {
            fireStationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Utility Methods

    private Map<String, Object> convertFireStationToMap(FireStation station) {
        Map<String, Object> fireStationMap = new HashMap<>();
        fireStationMap.put("id", station.getId());
        fireStationMap.put("stationNumber", station.getStationNumber());
        fireStationMap.put("address", station.getAddress());
        return fireStationMap;
    }

    private Map<String, Object> convertPersonToSummaryMap(Person person) {
        Map<String, Object> personMap = new HashMap<>();
        personMap.put("firstName", person.getFirstName());
        personMap.put("lastName", person.getLastName());
        personMap.put("address", person.getAddress());
        personMap.put("phone", person.getPhone());
        personMap.put("age", person.getAge());
        return personMap;
    }

    private Map<String, Object> convertPersonToDetailedMap(Person person) {
        Map<String, Object> personMap = new HashMap<>();
        personMap.put("name", person.getFirstName() + " " + person.getLastName());
        personMap.put("phone", person.getPhone());
        personMap.put("age", person.getAge());
        personMap.put("medications", person.getMedications());
        personMap.put("allergies", person.getAllergies());
        return personMap;
    }
}
