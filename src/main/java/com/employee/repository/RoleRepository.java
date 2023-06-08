package com.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.employee.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}

