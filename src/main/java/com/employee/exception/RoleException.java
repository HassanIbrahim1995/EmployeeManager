package com.employee.exception;

import org.springframework.http.HttpStatus;

public class RoleException extends RuntimeException {
    HttpStatus httpStatus;
    public RoleException(String message) {
        super(message);
    }

    public RoleException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public RoleException(String message, Throwable cause) {
        super(message, cause);
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}


