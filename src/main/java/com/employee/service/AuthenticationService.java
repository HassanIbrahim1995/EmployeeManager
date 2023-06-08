package com.employee.service;

import com.employee.dto.LoginDto;

public interface AuthenticationService {
    String login(LoginDto loginDto);
}
