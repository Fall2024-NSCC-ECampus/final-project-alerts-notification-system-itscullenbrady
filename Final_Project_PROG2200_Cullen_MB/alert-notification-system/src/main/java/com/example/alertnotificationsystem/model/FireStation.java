package com.example.alertnotificationsystem.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "fire_stations")
@Data // Lombok annotation for generating getter/setter methods, equals, hashcode, and toString
public class FireStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Fire station number.
     * Represents a unique identifier for the station.
     */
    private int stationNumber;

    /**
     * Address served by this fire station.
     * Used to identify the location this station responds to.
     */
    private String address;
}
