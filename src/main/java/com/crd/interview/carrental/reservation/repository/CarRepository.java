package com.crd.interview.carrental.reservation.repository;

import com.crd.interview.carrental.reservation.model.Car;
import com.crd.interview.carrental.reservation.model.CarType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CarRepository extends JpaRepository<com.crd.interview.carrental.reservation.model.Car,Long> {
    //FIXME fix query to find available car , join with reservations
    @Query("SELECT c FROM Car c ORDER BY c.id LIMIT 1")
    Optional<Car> findAvailableCar(CarType carType, LocalDateTime reservationStartDate, LocalDateTime reservationEndDate);
}
