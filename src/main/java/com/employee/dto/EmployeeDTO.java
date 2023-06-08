package com.employee.dto;

import com.employee.model.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    private Department department;
    private PersonDTO person;
    private AppUserDTO appUser;
}
