package com.employee.Data;

import com.employee.model.*;
import com.employee.repository.AppUserRepository;
import com.employee.repository.EmployeeRepository;
import com.employee.repository.PersonRepository;
import com.employee.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final PersonRepository personRepository;
    private final EmployeeRepository employeeRepository;
    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public DatabaseSeeder(PersonRepository personRepository, EmployeeRepository employeeRepository,
                          AppUserRepository appUserRepository, RoleRepository roleRepository) {
        this.personRepository = personRepository;
        this.employeeRepository = employeeRepository;
        this.appUserRepository = appUserRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Person person1 = new Person();
        person1.setFirstName("John");
        person1.setLastName("Doe");
        person1.setBirthdate(new Date());
        person1.setAddresses(new ArrayList<>());
        person1.setBsn("123456789");
        person1.setIban("NL12ABCD34567890");

// Create Address
        Address address1 = new Address("Street 1", "City 1", "State 1", "12345", person1);
        person1.addAddress(address1);

// Save Person
        Person savedPerson1 = personRepository.save(person1);

        // Create Employee
        Employee employee1 = Employee.builder()
                .department(Department.IT)
                .person(savedPerson1)
                .build();
        employeeRepository.save(employee1);

        // Retrieve Managed Role from the database
        Role role1 = roleRepository.findByName("ROLE_USER");

        // Create AppUser
        AppUser appUser1 = AppUser.builder()
                .username("username1")
                .password("password1")
                .email("email1@example.com")
                .employee(employee1)
                .build();

        // Add the retrieved Role to the AppUser's roles collection
        appUser1.getRoles().add(role1);

        // Save AppUser
        appUserRepository.save(appUser1);

        System.out.println("Saved records:");
        List<Person> persons = personRepository.findAll();
        List<Employee> employees = employeeRepository.findAll();
        List<AppUser> appUsers = appUserRepository.findAll();
        List<Role> roles = roleRepository.findAll();
        System.out.println(persons);
        System.out.println(employees);
        System.out.println(appUsers);
        System.out.println(roles);
    }

}