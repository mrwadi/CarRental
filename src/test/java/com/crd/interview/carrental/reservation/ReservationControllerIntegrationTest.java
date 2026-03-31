package com.crd.interview.carrental.reservation;

import com.crd.interview.carrental.reservation.dto.ReservationRequest;
import com.crd.interview.carrental.reservation.model.Car;
import com.crd.interview.carrental.reservation.model.CarType;
import com.crd.interview.carrental.reservation.model.Customer;
import com.crd.interview.carrental.reservation.repository.CarRepository;
import com.crd.interview.carrental.reservation.repository.CustomerRepository;
import com.crd.interview.carrental.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Integration tests for POST /api/reservations endpoint")
class ReservationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Customer testCustomer;
    private Car sedanCar;
    private LocalDateTime futureStartDate;
    private static final String API_ENDPOINT = "/api/reservations";

    @BeforeEach
    void setUp() {
        // Clear repositories
        reservationRepository.deleteAll();
        carRepository.deleteAll();
        customerRepository.deleteAll();

        // Create test data
        testCustomer = customerRepository.save(
                Customer.builder()
                        .email("test@example.com")
                        .build()
        );

        sedanCar = carRepository.save(
                Car.builder()
                        .licensePlate("WA1001")
                        .carType(CarType.SEDAN)
                        .build()
        );

        futureStartDate = LocalDateTime.now().plusDays(5);
    }

    @Test
    @DisplayName("Should successfully create a reservation with valid request")
    void shouldCreateReservationSuccessfully() throws Exception {
        // given
        ReservationRequest request = new ReservationRequest(
                CarType.SEDAN,
                futureStartDate,
                3,
                testCustomer.getId()
        );

        // when & then
        var result = mockMvc.perform(post(API_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationId", notNullValue()))
                .andExpect(jsonPath("$.carId", equalTo(Integer.valueOf(String.valueOf(sedanCar.getId())))))
                .andExpect(jsonPath("$.licensePlate", equalTo("WA1001")))
                .andExpect(jsonPath("$.carType", equalTo("SEDAN")))
                .andExpect(jsonPath("$.customerId", equalTo(Integer.valueOf(String.valueOf(testCustomer.getId())))))
                .andExpect(jsonPath("$.numberOfDays", equalTo(3)))
                .andExpect(jsonPath("$.status", equalTo("BOOKED")));

        // Verify that reservation was saved in database
        assertThat(reservationRepository.findAll()).hasSize(1);
    }
}

