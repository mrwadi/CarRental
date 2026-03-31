package com.crd.interview.carrental.reservation.repository;

import com.crd.interview.carrental.reservation.model.Car;
import com.crd.interview.carrental.reservation.model.CarType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CarRepository extends JpaRepository<com.crd.interview.carrental.reservation.model.Car,Long> {
    //TODO add query to find available car , join with reservations
    Optional<Car> findAvailableCar(CarType carType, LocalDateTime reservationStartDate, LocalDateTime reservationEndDate);
}
