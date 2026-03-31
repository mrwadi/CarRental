package com.crd.interview.carrental.reservation.repository;

import com.crd.interview.carrental.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
}
