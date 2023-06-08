package com.employee.service;

import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements GenericService<Employee> {

    private final EmployeeRepository employeeRepository;

    @Override
    public Optional<Employee> getById(Long id) {
        log.info("Getting Employee by id: {}", id);
        return employeeRepository.findById(id);
    }

    @Override
    public Optional<Employee> save(Employee entity) {
        log.info("Saving Employee: {}", entity);
        Employee savedEmployee = employeeRepository.save(entity);
        return Optional.of(savedEmployee);
    }

    @Override
    public void delete(Employee entity) {
        log.info("Deleting Employee: {}", entity);
        if (employeeRepository.existsById(entity.getId())) {
            employeeRepository.delete(entity);
        }
    }

    @Override
    public Optional<Employee> update(Employee entity) {
        log.info("Updating Employee: {}", entity);
        if (employeeRepository.existsById(entity.getId())) {
            Employee updatedEmployee = employeeRepository.save(entity);
            return Optional.of(updatedEmployee);
        }
        return Optional.empty();
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
}
