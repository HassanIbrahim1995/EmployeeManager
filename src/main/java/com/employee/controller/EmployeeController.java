package com.employee.controller;

import com.employee.dto.EmployeeDTO;
import com.employee.exception.EmployeeException;
import com.employee.model.Employee;
import com.employee.objectsMappers.Mapper;
import com.employee.service.GenericService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final GenericService<Employee> employeeService;
    private final Mapper<Employee, EmployeeDTO> employeeMapper;

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employeeOptional = employeeService.getById(id);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            EmployeeDTO employeeDTO = employeeMapper.mapToDTO(employee);
            return ResponseEntity.ok(employeeDTO);
        } else {
            throw new EmployeeException("Employee with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.mapFromDTO(employeeDTO);
        Optional<Employee> savedEmployeeOptional = employeeService.save(employee);
        if (savedEmployeeOptional.isPresent()) {
            Employee savedEmployee = savedEmployeeOptional.get();
            EmployeeDTO savedEmployeeDTO = employeeMapper.mapToDTO(savedEmployee);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployeeDTO);
        } else {
            throw new EmployeeException("Employee with name: " + employeeDTO.getPerson().getFirstName() + " already exists.", HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        Optional<Employee> existingEmployeeOptional = employeeService.getById(id);
        if (existingEmployeeOptional.isPresent()) {
            Employee existingEmployee = existingEmployeeOptional.get();
            Employee updatedEmployee = employeeMapper.mapFromDTO(employeeDTO);
            updatedEmployee.setId(existingEmployee.getId());
            Optional<Employee> savedEmployeeOptional = employeeService.update(updatedEmployee);
            if (savedEmployeeOptional.isPresent()) {
                Employee savedEmployee = savedEmployeeOptional.get();
                EmployeeDTO savedEmployeeDTO = employeeMapper.mapToDTO(savedEmployee);
                return ResponseEntity.ok(savedEmployeeDTO);
            }
        }
        throw new EmployeeException("Failed to update employee with ID " + id + " not found.", HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        Optional<Employee> employeeOptional = employeeService.getById(id);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            employeeService.delete(employee);
            return ResponseEntity.noContent().build();
        } else {
            throw new EmployeeException("Employee with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }

}
