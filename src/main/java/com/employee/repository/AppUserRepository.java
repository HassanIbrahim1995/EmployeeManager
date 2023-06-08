package com.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.employee.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
}

