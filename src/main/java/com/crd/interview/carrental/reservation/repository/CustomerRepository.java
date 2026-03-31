package com.crd.interview.carrental.reservation.repository;

import com.crd.interview.carrental.reservation.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
