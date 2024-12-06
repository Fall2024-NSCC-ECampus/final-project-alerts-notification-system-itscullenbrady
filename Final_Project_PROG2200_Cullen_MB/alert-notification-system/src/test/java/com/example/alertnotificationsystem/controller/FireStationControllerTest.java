package com.example.alertnotificationsystem.controller;

import com.example.alertnotificationsystem.service.FireStationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FireStationController.class)
public class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationService fireStationService; // Mock the dependency

    @Test
    public void testGetAllFireStations() throws Exception {
        when(fireStationService.getAllFireStations()).thenReturn(
                List.of(
                        Map.of("id", 1L, "stationNumber", 1, "address", "123 Main St")
                )
        );

        mockMvc.perform(get("/api/firestations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].stationNumber").value(1))
                .andExpect(jsonPath("$[0].address").value("123 Main St"));
    }
}
