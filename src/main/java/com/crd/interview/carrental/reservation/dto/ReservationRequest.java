package com.crd.interview.carrental.reservation.dto;

import com.crd.interview.carrental.reservation.model.CarType;

import java.time.LocalDateTime;

public record ReservationRequest(
        CarType carType,
        LocalDateTime startDate,
        int numberOfDays,
        Long customerId )
{}