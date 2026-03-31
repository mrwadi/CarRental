package com.crd.interview.carrental.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<com.crd.interview.carrental.reservation.model.Car,Long> {
}
