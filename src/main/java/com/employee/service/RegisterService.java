package com.employee.service;

import com.employee.model.*;
import com.employee.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;
import com.employee.dto.UserRegistrationDto;

@Service
@AllArgsConstructor
public class RegisterService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveUser(UserRegistrationDto userDto) {
        Person person = new Person();
        person.setFirstName(userDto.getFirstName());
        person.setLastName(userDto.getLastName());
        person.setBirthdate(userDto.getBirthdate());
        person.setAddresses(new ArrayList<>());
        person.getAddresses().add(new Address(userDto.getStreet(), userDto.getCity(), userDto.getState(), userDto.getPostalCode(), person));
        person.setIban(userDto.getIban());
        person.setBsn(userDto.getBsn());

        Employee employee = Employee.builder()
                .department(Department.IT)
                .person(person)
                .build();

        AppUser appUser = AppUser.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .employee(employee)
                .roles(userDto.getRoles().stream().map(Role::new).collect(Collectors.toSet()))
                .build();
        person.setEmployee(employee);
        employee.setPerson(person);
        employee.setAppUser(appUser);
        appUser.setEmployee(employee);
        personRepository.save(person);
    }
}
