package com.schambeck.dna.web.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

@RestControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = CONFLICT, reason = "Data Integrity Violation")
    void handleConflict() {
    }

    @ExceptionHandler(DnaRequiredException.class)
    @ResponseStatus(value = BAD_REQUEST, reason = "DNA is required")
    void handleDnaRequiredException() {
    }

    @ExceptionHandler(NotSquareException.class)
    @ResponseStatus(value = BAD_REQUEST, reason = "DNA must be a square table")
    void handleNotSquareException() {
    }

}
