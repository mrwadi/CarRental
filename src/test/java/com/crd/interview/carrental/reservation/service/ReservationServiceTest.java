package com.crd.interview.carrental.reservation.service;

import com.crd.interview.carrental.reservation.dto.ReservationRequest;
import com.crd.interview.carrental.reservation.dto.ReservationResponse;
import com.crd.interview.carrental.reservation.model.*;
import com.crd.interview.carrental.reservation.repository.CarRepository;
import com.crd.interview.carrental.reservation.repository.CustomerRepository;
import com.crd.interview.carrental.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    CarRepository carRepository;
    @Mock
    ReservationRepository reservationRepository;
    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    ReservationService reservationService;

    private Customer givenCustomer;
    private Car givenSedanCar;

    @BeforeEach
    void setUp() {
        givenCustomer = Customer.builder().id(1L).email("jan@example.com").build();
        givenSedanCar = Car.builder().id(10L).licensePlate("WA 1001").carType(CarType.SEDAN).build();
    }

    @Test
    @DisplayName("Should create reservation when a car of requested type is available")
    void shouldCreateReservationWhenCarAvailable() {
        //given
        when(carRepository.findAvailableCar(eq(CarType.SEDAN), any(), any())).thenReturn(Optional.of(givenSedanCar));

        when(customerRepository.findById(givenCustomer.getId())).thenReturn(Optional.of(givenCustomer));

        when(reservationRepository.save(any())).thenAnswer(inv -> {
            Reservation r = inv.getArgument(0);
            r.setId(100L);
            return r;
        });

        //when
        ReservationRequest req = new ReservationRequest(CarType.SEDAN, LocalDateTime.now().plusDays(1), 2, givenCustomer.getId());
        ReservationResponse response = reservationService.createReservation(req);

        //then
        assertThat(response).isNotNull();
        assertThat(response.reservationId()).isEqualTo(100L);
        assertThat(response.carType()).isEqualTo(CarType.SEDAN);
        assertThat(response.status()).isEqualTo(ReservationStatus.BOOKED);
        assertThat(response.numberOfDays()).isEqualTo(2L);
        verify(reservationRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should throw NoCarAvailableException when no cars of requested type are free")
    void shouldThrowWhenNoCarsAvailable() {
        when(carRepository.findAvailableCar(eq(CarType.VAN), any(), any())).thenReturn(Optional.empty());

        //when
        ReservationRequest req = new ReservationRequest(CarType.VAN, LocalDateTime.now().plusDays(1), 2, givenCustomer.getId());
        assertThatThrownBy(() -> reservationService.createReservation(req))
                .isInstanceOf(NoCarAvailableException.class);

        verify(reservationRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when customer is not found")
    void shouldThrowWhenCustomerNotFound() {
        //given
        when(carRepository.findAvailableCar(eq(CarType.SEDAN), any(), any())).thenReturn(Optional.of(givenSedanCar));
        when(customerRepository.findById(givenCustomer.getId())).thenReturn(Optional.empty());

        //when
        ReservationRequest req = new ReservationRequest(CarType.SEDAN, LocalDateTime.now().plusDays(1), 2, givenCustomer.getId());
        assertThatThrownBy(() -> reservationService.createReservation(req))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Customer not found: " + givenCustomer.getId());

        verify(reservationRepository, never()).save(any());
    }

}