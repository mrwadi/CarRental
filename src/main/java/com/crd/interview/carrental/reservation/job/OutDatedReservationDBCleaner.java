package com.crd.interview.carrental.reservation.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutDatedReservationDBCleaner {

//    private final ReservationRepository reservationRepository;

    @Scheduled(fixedDelay = 2000)
    @Transactional
    public void cleanOutdatedReservations() {
        log.info("Delete items form db job triggered");
    }
}
