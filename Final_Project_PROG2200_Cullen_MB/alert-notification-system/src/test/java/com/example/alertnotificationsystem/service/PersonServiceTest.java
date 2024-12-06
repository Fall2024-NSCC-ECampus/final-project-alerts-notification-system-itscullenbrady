package com.example.alertnotificationsystem.service;

import com.example.alertnotificationsystem.model.Person;
import com.example.alertnotificationsystem.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPersons() {
        Person person = new Person();
        person.setId(1L);
        person.setFirstName("John");
        person.setLastName("Doe");
        person.setAddress("123 Main St");

        when(personRepository.findAll()).thenReturn(List.of(person));

        List<Map<String, Object>> result = personService.getAllPersons();

        assertEquals(1, result.size());
        assertEquals("John", result.get(0).get("firstName"));
        assertEquals("Doe", result.get(0).get("lastName"));
        assertEquals("123 Main St", result.get(0).get("address"));
    }
}
