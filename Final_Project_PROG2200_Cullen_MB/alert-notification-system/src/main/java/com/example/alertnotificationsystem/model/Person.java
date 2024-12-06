package com.example.alertnotificationsystem.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "persons")
@Data // Lombok generates getters, setters
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * First name of the person.
     * Used to identify the person.
     */
    private String firstName;

    /**
     * Last name of the person.
     * Used to identify the person.
     */
    private String lastName;

    /**
     * Address of the person.
     * Used to determine the location of the person.
     */
    private String address;

    /**
     * City where the person lives.
     * Helps categorize residents by city.
     */
    private String city;

    /**
     * Phone number of the person.
     * Used for contact purposes.
     */
    private String phone;

    /**
     * Email of the person.
     * Used for digital communication.
     */
    private String email;

    /**
     * Age of the person.
     * Used for age-related services and tracking.
     */
    private int age;

    /**
     * List of medications the person is taking.
     * Stored as text for detailed descriptions.
     */
    @Column(columnDefinition = "TEXT")
    private String medications;

    /**
     * List of allergies the person has.
     * Stored as text for detailed descriptions.
     */
    @Column(columnDefinition = "TEXT")
    private String allergies;
}
