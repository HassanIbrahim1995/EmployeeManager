package com.employee.exception;

import org.springframework.http.HttpStatus;

public class EmployeeException extends RuntimeException {
    HttpStatus httpStatus;

    public EmployeeException(String message) {
        super(message);
    }

    public EmployeeException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public EmployeeException(String message, Throwable cause) {
        super(message, cause);
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

