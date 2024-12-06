package com.example.alertnotificationsystem.service;

import com.example.alertnotificationsystem.model.FireStation;
import com.example.alertnotificationsystem.repository.FireStationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class FireStationServiceTest {

    @Mock
    private FireStationRepository fireStationRepository;

    @InjectMocks
    private FireStationService fireStationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllFireStations() {
        FireStation station = new FireStation();
        station.setId(1L);
        station.setStationNumber(1);
        station.setAddress("123 Main St");

        when(fireStationRepository.findAll()).thenReturn(List.of(station));

        List<Map<String, Object>> result = fireStationService.getAllFireStations();

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).get("stationNumber"));
        assertEquals("123 Main St", result.get(0).get("address"));
    }
}
