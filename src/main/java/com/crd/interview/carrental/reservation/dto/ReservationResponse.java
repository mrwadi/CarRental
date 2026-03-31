package com.crd.interview.carrental.reservation.dto;
import com.crd.interview.carrental.reservation.model.CarType;
import com.crd.interview.carrental.reservation.model.Reservation;
import com.crd.interview.carrental.reservation.model.ReservationStatus;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public record ReservationResponse(
        Long reservationId,
        Long carId,
        String licensePlate,
        CarType carType,
        Long customerId,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        long numberOfDays,
        ReservationStatus status
) {
    public static ReservationResponse from(Reservation r) {
        return new ReservationResponse(
                r.getId(),
                r.getCar().getId(),
                r.getCar().getLicensePlate(),
                r.getCar().getCarType(),
                r.getCustomer().getId(),
                r.getStartDate(),
                r.getEndDate(),
                ChronoUnit.DAYS.between(r.getStartDate(), r.getEndDate()),
                r.getStatus()
        );
    }
}