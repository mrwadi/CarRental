package com.crd.interview.carrental.reservation.dto;

import com.crd.interview.carrental.reservation.model.CarType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReservationRequest(
        @NotNull CarType carType,
        @NotNull LocalDateTime startDate,
        @Min(1) int numberOfDays,
        @NotNull Long customerId)
{}