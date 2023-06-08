package com.employee.controller;

import com.employee.dto.EmployeeDTO;
import com.employee.model.Employee;
import com.employee.objectsMappers.Mapper;
import com.employee.service.GenericService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final GenericService<Employee> employeeService;
    private final Mapper<Employee, EmployeeDTO> employeeMapper;

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        EmployeeDTO employeeDTO = employeeMapper.mapToDTO(employee);
        return ResponseEntity.ok(employeeDTO);
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee employee = employeeMapper.mapFromDTO(employeeDTO);
        Employee savedEmployee = employeeService.save(employee);
        EmployeeDTO savedEmployeeDTO = employeeMapper.mapToDTO(savedEmployee);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedEmployeeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        Employee existingEmployee = employeeService.getById(id);
        Employee updatedEmployee = employeeMapper.mapFromDTO(employeeDTO);
        updatedEmployee.setId(existingEmployee.getId());
        Employee savedEmployee = employeeService.update(updatedEmployee);
        EmployeeDTO savedEmployeeDTO = employeeMapper.mapToDTO(savedEmployee);
        return ResponseEntity.ok(savedEmployeeDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        employeeService.delete(employee);
        return ResponseEntity.noContent().build();
    }
}

