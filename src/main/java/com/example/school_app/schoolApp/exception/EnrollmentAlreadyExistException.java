package com.example.school_app.schoolApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EnrollmentAlreadyExistException extends RuntimeException {
    public EnrollmentAlreadyExistException(String message) {
        super(message);
    }
}
