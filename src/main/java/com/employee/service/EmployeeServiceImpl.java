package com.employee.service;

import com.employee.exception.EmployeeException;
import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements GenericService<Employee> {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee getById(Long id) {
        log.info("Getting Employee by id: {}", id);
        return employeeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employee with id {} not found", id);
                    return new EmployeeException("Employee with id " + id + " not found.");
                });
    }

    @Override
    public Employee save(Employee entity) {
        log.info("Saving Employee: {}", entity);
        try {
            return employeeRepository.save(entity);
        } catch (DataAccessException ex) {
            log.error("Unable to save Employee: {}", entity, ex);
            throw new EmployeeException(String.format("Unable to save employee %s", entity), ex);
        }
    }

    @Override
    public void delete(Employee entity) {
        log.info("Deleting Employee: {}", entity);
        if (!employeeRepository.existsById(entity.getId())) {
            log.error("Employee with id {} not found, unable to delete", entity.getId());
            throw new EmployeeException("Employee with id " + entity.getId() + " not found, unable to delete.");
        }
        employeeRepository.delete(entity);
    }

    @Override
    public Employee update(Employee entity) {
        log.info("Updating Employee: {}", entity);
        if (!employeeRepository.existsById(entity.getId())) {
            log.error("Employee with id {} not found, unable to update", entity.getId());
            throw new EmployeeException("Employee with id " + entity.getId() + " not found, unable to update.");
        }
        try {
            return employeeRepository.save(entity);
        } catch (DataAccessException ex) {
            log.error("Unable to update Employee: {}", entity, ex);
            throw new EmployeeException(String.format("Unable to update employee %s", entity), ex);
        }
    }
}

