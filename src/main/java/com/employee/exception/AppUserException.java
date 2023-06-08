package com.employee.exception;

import org.springframework.http.HttpStatus;

public class AppUserException extends RuntimeException {
    HttpStatus httpStatus;
    public AppUserException(String message) {
        super(message);
    }

    public AppUserException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public AppUserException(String message, Throwable cause) {
        super(message, cause);
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
