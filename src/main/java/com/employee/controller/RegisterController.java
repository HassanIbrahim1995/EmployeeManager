package com.employee.controller;

import com.employee.dto.UserRegistrationDto;
import com.employee.exception.RegistrationException;
import com.employee.service.RegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserRegistrationDto userDto) {
        if (userDto == null) {
            throw new RegistrationException("Invalid input: User data is null", HttpStatus.BAD_REQUEST);
        }
        registerService.saveUser(userDto);
        return ResponseEntity.ok("User registration successful!");
    }
}
