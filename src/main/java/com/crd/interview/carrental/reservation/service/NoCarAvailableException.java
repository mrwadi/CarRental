package com.crd.interview.carrental.reservation.service;

public class NoCarAvailableException extends RuntimeException {
    public NoCarAvailableException(String message) {
        super(message);
    }
}
