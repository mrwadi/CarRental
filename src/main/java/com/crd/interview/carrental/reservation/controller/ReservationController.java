package com.crd.interview.carrental.reservation.controller;

import com.crd.interview.carrental.reservation.dto.ReservationRequest;
import com.crd.interview.carrental.reservation.dto.ReservationResponse;
import com.crd.interview.carrental.reservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> create(@Valid @RequestBody ReservationRequest request) {
        ReservationResponse result = reservationService.createReservation(request);
        return ResponseEntity.ok(result);
    }
}
