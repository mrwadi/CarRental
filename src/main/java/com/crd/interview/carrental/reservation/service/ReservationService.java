package com.crd.interview.carrental.reservation.service;

import com.crd.interview.carrental.reservation.dto.ReservationRequest;
import com.crd.interview.carrental.reservation.dto.ReservationResponse;
import com.crd.interview.carrental.reservation.model.Car;
import com.crd.interview.carrental.reservation.model.Customer;
import com.crd.interview.carrental.reservation.model.Reservation;
import com.crd.interview.carrental.reservation.model.ReservationStatus;
import com.crd.interview.carrental.reservation.repository.CarRepository;
import com.crd.interview.carrental.reservation.repository.CustomerRepository;
import com.crd.interview.carrental.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;


    @Transactional
    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        LocalDateTime reservationEndDate = reservationRequest.startDate().plusDays(reservationRequest.numberOfDays());

        //FIXME lock item for this transaction - select for update
        Car availableCar = carRepository.findAvailableCar(
                        reservationRequest.carType(),
                        reservationRequest.startDate(),
                        reservationEndDate)
                .orElseThrow(() -> new NoCarAvailableException("No available car found"));

        Customer customer = customerRepository.findById(reservationRequest.customerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + reservationRequest.customerId()));

        Reservation reservation = Reservation.builder()
                .car(availableCar)
                .startDate(reservationRequest.startDate())
                .endDate(reservationEndDate)
                .customer(customer)
                .status(ReservationStatus.BOOKED)
                .build();
        reservationRepository.save(reservation);

        return ReservationResponse.from(reservation);
    }
}
