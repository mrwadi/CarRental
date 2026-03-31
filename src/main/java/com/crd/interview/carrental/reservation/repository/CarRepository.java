package com.crd.interview.carrental.reservation.repository;

import com.crd.interview.carrental.reservation.model.Car;
import com.crd.interview.carrental.reservation.model.CarType;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CarRepository extends JpaRepository<com.crd.interview.carrental.reservation.model.Car,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Car c " +
            "WHERE c.carType = :carType " +
            "AND NOT EXISTS (SELECT r FROM Reservation r WHERE r.car = c AND r.startDate < :reservationEndDate AND r.endDate > :reservationStartDate) " +
            "ORDER BY c.id " +
            "LIMIT 1")
    Optional<Car> findAvailableCar(CarType carType, LocalDateTime reservationStartDate, LocalDateTime reservationEndDate);
}
