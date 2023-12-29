package org.adaschool.tdd;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.adaschool.tdd.controller.weather.WeatherReportController;
import org.adaschool.tdd.controller.weather.dto.WeatherReportDto;
import org.adaschool.tdd.repository.document.WeatherReport;
import org.adaschool.tdd.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class WeatherReportControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WeatherService weatherService;

    @InjectMocks
    private WeatherReportController weatherReportController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(weatherReportController).build();
    }

    @Test
    void testFindById_WeatherReportFound() throws Exception {
        // Mock the behavior of the WeatherService
        WeatherReport weatherReport = new WeatherReport(null, 25.0, 70.0, "John", new Date());
        when(weatherService.findById("1")).thenReturn(weatherReport);

        // Perform GET request to /v1/weather/1
        mockMvc.perform(get("/v1/weather/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"temperature\":25.0,\"humidity\":70.0,\"reporter\":\"John\"}"));
    }

    @Test
    void testCreate_WeatherReportCreated() throws Exception {
        // Prepare WeatherReportDto for the request body
        WeatherReportDto weatherReportDto = new WeatherReportDto(null, 28.0, 65.0, "Alice", new Date());

        // Mock the behavior of the WeatherService
        WeatherReport createdWeatherReport = new WeatherReport(null, 28.0, 65.0, "Alice", new Date());
        when(weatherService.report(any(WeatherReportDto.class))).thenReturn(createdWeatherReport);

        // Perform POST request to /v1/weather with JSON payload
        mockMvc.perform(post("/v1/weather/created")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"temperature\":28.0,\"humidity\":65.0,\"reporter\":\"Alice\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"temperature\":28.0,\"humidity\":65.0,\"reporter\":\"Alice\"}"));
    }
}
