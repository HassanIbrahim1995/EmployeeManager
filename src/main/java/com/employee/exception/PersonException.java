package com.employee.exception;

import org.springframework.http.HttpStatus;

public class PersonException extends RuntimeException {

    HttpStatus httpStatus;

    public PersonException(String message) {
        super(message);
    }

    public PersonException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersonException(String message, Throwable cause , HttpStatus httpStatus) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public PersonException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}