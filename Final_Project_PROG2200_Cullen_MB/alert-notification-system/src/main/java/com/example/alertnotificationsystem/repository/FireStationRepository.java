package com.example.alertnotificationsystem.repository;

import com.example.alertnotificationsystem.model.FireStation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on FireStation entities.
 * Extends JpaRepository to leverage built-in methods and custom query creation.
 */
public interface FireStationRepository extends JpaRepository<FireStation, Long> {

    /**
     * Finds fire stations by their station number.
     * @param stationNumber the station number to search for.
     * @return a list of fire stations matching the given station number.
     */
    List<FireStation> findByStationNumber(int stationNumber);

    /**
     * Finds fire stations by their address.
     * @param address the address to search for.
     * @return a list of fire stations matching the given address.
     */
    List<FireStation> findByAddress(String address);

    /**
     * Finds fire stations by a list of station numbers.
     * @param stationNumbers a list of station numbers to search for.
     * @return a list of fire stations matching any of the given station numbers.
     */
    List<FireStation> findByStationNumberIn(List<Integer> stationNumbers);
}
