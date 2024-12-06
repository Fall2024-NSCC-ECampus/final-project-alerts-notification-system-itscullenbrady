package com.example.alertnotificationsystem.controller;

import com.example.alertnotificationsystem.service.FireStationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/firestations")
@Slf4j // Adds logging capabilities to this controller
public class FireStationController {

    @Autowired
    private FireStationService fireStationService;

    /**
     * Fetches all fire stations.
     * @return A list of all fire stations.
     */
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllFireStations() {
        log.info("Fetching all fire stations.");
        return ResponseEntity.ok(fireStationService.getAllFireStations());
    }

    /**
     * Fetches fire stations by their station number.
     * @param stationNumber The station number to search.
     * @return A list of fire stations matching the given station number.
     */
    @GetMapping("/station/{stationNumber}")
    public ResponseEntity<List<Map<String, Object>>> getFireStationsByNumber(@PathVariable int stationNumber) {
        log.info("Fetching fire stations by station number: {}", stationNumber);
        return ResponseEntity.ok(fireStationService.getFireStationsByNumber(stationNumber));
    }

    /**
     * Fetches people served by a specific fire station number.
     * @param stationNumber The station number to search for.
     * @return People details served by the specified fire station.
     */
    @GetMapping("/firestation")
    public ResponseEntity<Map<String, Object>> getPeopleByStationNumber(@RequestParam int stationNumber) {
        log.info("Fetching people served by fire station number: {}", stationNumber);
        Map<String, Object> data = fireStationService.getPeopleByStationNumber(stationNumber);

        if (data.isEmpty()) {
            log.warn("No data found for fire station number: {}", stationNumber);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(data);
    }
    /**
     * Fetches the fire station details based on the provided address.
     *
     * @param address The address for which the fire station details are to be fetched.
     * @return A ResponseEntity containing the fire station details if found, or a 404 Not Found status if no fire station is found for the given address.
     */
    @GetMapping("/byAddress")
    public ResponseEntity<Map<String, Object>> getFireStationByAddress(@RequestParam String address) {
        log.info("Fetching fire station by address: {}", address);
        Map<String, Object> fireStation = fireStationService.getFireDetailsByAddress(address);
        return fireStation != null ? ResponseEntity.ok(fireStation) : ResponseEntity.notFound().build();
    }


    /**
     * Fetches flood information for multiple fire station numbers.
     * @param stations A list of station numbers to search for flood details.
     * @return Flood details for the specified stations.
     */
    @GetMapping("/flood/stations")
    public ResponseEntity<Map<String, List<Map<String, Object>>>> getFloodStations(@RequestParam List<Integer> stations) {
        log.info("Fetching flood stations for station numbers: {}", stations);
        Map<String, List<Map<String, Object>>> result = fireStationService.getFloodStations(stations);
        return ResponseEntity.ok(result);
    }

    /**
     * Adds a new fire station.
     * @param fireStation The fire station details to save.
     * @return The saved fire station details.
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> addFireStation(@RequestBody Map<String, Object> fireStation) {
        log.info("Adding a new fire station: {}", fireStation);
        return ResponseEntity.ok(fireStationService.saveFireStation(fireStation));
    }

    /**
     * Updates an existing fire station by ID.
     * @param id The ID of the fire station to update.
     * @param updatedFireStation The updated details of the fire station.
     * @return The updated fire station details or 404 if not found.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateFireStation(@PathVariable Long id, @RequestBody Map<String, Object> updatedFireStation) {
        log.info("Updating fire station with ID: {}", id);
        Map<String, Object> result = fireStationService.updateFireStation(id, updatedFireStation);

        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    /**
     * Deletes a fire station by ID.
     * @param id The ID of the fire station to delete.
     * @return A response indicating whether the deletion was successful or not.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFireStation(@PathVariable Long id) {
        log.info("Deleting fire station with ID: {}", id);
        boolean isDeleted = fireStationService.deleteFireStation(id);

        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
