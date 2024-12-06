package com.example.alertnotificationsystem.repository;

import com.example.alertnotificationsystem.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for performing CRUD operations on Person entities.
 * Extends JpaRepository to leverage built-in methods and custom query creation.
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    /**
     * Finds persons by their address.
     * @param address the address to search for.
     * @return a list of persons living at the given address.
     */
    List<Person> findByAddress(String address);

    /**
     * Finds persons by their city.
     * @param city the city to search for.
     * @return a list of persons living in the given city.
     */
    List<Person> findByCity(String city);

    /**
     * Finds persons by their first name and last name.
     * @param firstName the first name to search for.
     * @param lastName the last name to search for.
     * @return a list of persons matching the given first and last name.
     */
    List<Person> findByFirstNameAndLastName(String firstName, String lastName);

    /**
     * Finds persons by a list of addresses.
     * @param addresses a list of addresses to search for.
     * @return a list of persons living at any of the given addresses.
     */
    List<Person> findByAddressIn(List<String> addresses);
}
