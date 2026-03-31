package com.crd.interview.carrental.reservation.exception;

import com.crd.interview.carrental.reservation.service.NoCarAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoCarAvailableException.class)
    public ProblemDetail handleNoCarAvailableException(NoCarAvailableException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
    }
}

