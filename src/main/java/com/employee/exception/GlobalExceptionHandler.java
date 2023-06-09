package com.employee.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PersonException.class)
    public ResponseEntity<ProblemDetail> handlePersonException(PersonException ex) {
        log.error("PersonException occurred: {}", ex.getMessage());
        HttpStatus httpStatus = ex.getHttpStatus();
        String title = "Person Service Exception";
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(httpStatus, ex.getMessage());
        problem.setTitle(title);
        return ResponseEntity.status(httpStatus).body(problem);
    }

    @ExceptionHandler(RoleException.class)
    public ResponseEntity<ProblemDetail> handleRoleException(RoleException ex) {
        log.error("RoleException occurred: {}", ex.getMessage());
        HttpStatus httpStatus = ex.getHttpStatus();
        String title = "Role Service Exception";
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(httpStatus, ex.getMessage());
        problem.setTitle(title);
        return ResponseEntity.status(httpStatus).body(problem);
    }

    @ExceptionHandler(AddressException.class)
    public ResponseEntity<ProblemDetail> handleAddressException(AddressException ex) {
        log.error("AddressException occurred: {}", ex.getMessage());
        HttpStatus httpStatus = ex.getHttpStatus();
        String title = "Address Service Exception";
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(httpStatus, ex.getMessage());
        problem.setTitle(title);
        return ResponseEntity.status(httpStatus).body(problem);
    }

    @ExceptionHandler(EmployeeException.class)
    public ResponseEntity<ProblemDetail> handleEmployeeException(EmployeeException ex) {
        log.error("EmployeeException occurred: {}", ex.getMessage());
        HttpStatus httpStatus = ex.getHttpStatus();
        String title = "Employee Service Exception";
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(httpStatus, ex.getMessage());
        problem.setTitle(title);
        return ResponseEntity.status(httpStatus).body(problem);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ProblemDetail> handleDataAccessException(DataAccessException ex) {
        log.error("DataAccessException occurred: {}", ex.getMessage());
        String title = "Data Access Exception";
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
        problem.setTitle(title);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(problem);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        log.error("DataAccessException occurred: {}", ex.getMessage());
        String title = "Data Access Exception";
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage());
        problem.setTitle(title);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(problem);
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<ProblemDetail> handleRegistrationException(RegistrationException ex) {
        log.error("RegistrationException occurred: {}", ex.getMessage());
        HttpStatus httpStatus = ex.getHttpStatus();
        String title = "Registration Exception";
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(httpStatus, ex.getMessage());
        problem.setTitle(title);
        return ResponseEntity.status(httpStatus).body(problem);
    }

}
