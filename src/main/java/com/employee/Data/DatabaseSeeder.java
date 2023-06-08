package com.employee.Data;

import com.employee.model.*;
import com.employee.repository.AppUserRepository;
import com.employee.repository.EmployeeRepository;
import com.employee.repository.PersonRepository;
import com.employee.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@AllArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        person1.setBirthdate(new Date());
        person1.setAddresses(new ArrayList<>());
        person1.setBsn("123456789");
        person1.setIban("NL12ABCD34567890");
        Address address1 = new Address("Street 1", "City 1", "State 1", "12345", person1);
        person1.addAddress(address1);
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("ADMIN"));
        roles.add(new Role("USER"));
        Employee employee = Employee.builder()
                .department(Department.IT)
                .build();
        person1.setEmployee(employee);
        employee.setPerson(person1);
        AppUser appUser1 = AppUser.builder()
                .username("username1")
                .password(passwordEncoder.encode("password1"))
                .email("email1@example.com")
                .employee(employee)
                .roles(roles)
                .build();
        employee.setAppUser(appUser1);
        personRepository.save(person1);
    }

}
