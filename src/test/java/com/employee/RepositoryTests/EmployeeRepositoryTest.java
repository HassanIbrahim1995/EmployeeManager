package com.employee.RepositoryTests;

import com.employee.model.Department;
import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void testSaveEmployee() {
        Employee employee = new Employee();
        employee.setDepartment(Department.IT);

        Employee savedEmployee = employeeRepository.save(employee);

        Assertions.assertNotNull(savedEmployee.getId());
    }

    @Test
    public void testFindOneEmployee() {
        Employee employee = new Employee();
        employee.setDepartment(Department.IT);

        Employee savedEmployee = employeeRepository.save(employee);

        Optional<Employee> foundEmployeeOptional = employeeRepository.findById(savedEmployee.getId());
        Assertions.assertTrue(foundEmployeeOptional.isPresent());

        Employee foundEmployee = foundEmployeeOptional.get();
        Assertions.assertEquals(savedEmployee.getId(), foundEmployee.getId());
        Assertions.assertEquals(savedEmployee.getDepartment(), foundEmployee.getDepartment());
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee = new Employee();
        employee.setDepartment(Department.IT);

        Employee savedEmployee = employeeRepository.save(employee);

        savedEmployee.setDepartment(Department.SALES);
        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        Assertions.assertEquals(savedEmployee.getId(), updatedEmployee.getId());
        Assertions.assertEquals(Department.SALES, updatedEmployee.getDepartment());
    }

    @Test
    public void testDeleteEmployee() {
        Employee employee = new Employee();
        employee.setDepartment(Department.IT);

        Employee savedEmployee = employeeRepository.save(employee);

        employeeRepository.delete(savedEmployee);

        Optional<Employee> deletedEmployeeOptional = employeeRepository.findById(savedEmployee.getId());
        Assertions.assertFalse(deletedEmployeeOptional.isPresent());
    }

    @Test
    public void testFindAllEmployees() {
        Employee employee1 = new Employee();
        employee1.setDepartment(Department.IT);
        employeeRepository.save(employee1);

        Employee employee2 = new Employee();
        employee2.setDepartment(Department.SALES);
        employeeRepository.save(employee2);

        List<Employee> allEmployees = employeeRepository.findAll();
        Assertions.assertEquals(2, allEmployees.size());
    }

}
