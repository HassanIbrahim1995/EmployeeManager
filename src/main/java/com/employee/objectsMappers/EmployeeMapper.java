package com.employee.objectsMappers;

import com.employee.dto.AppUserDTO;
import com.employee.dto.EmployeeDTO;
import com.employee.dto.PersonDTO;
import com.employee.model.AppUser;
import com.employee.model.Employee;
import com.employee.model.Person;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper implements Mapper<Employee, EmployeeDTO> {
    private final Mapper<Person, PersonDTO> personMapper;
    private final Mapper<AppUser, AppUserDTO> appUserMapper;

    public EmployeeMapper(Mapper<Person, PersonDTO> personMapper, Mapper<AppUser, AppUserDTO> appUserMapper) {
        this.personMapper = personMapper;
        this.appUserMapper = appUserMapper;
    }

    @Override
    public EmployeeDTO mapToDTO(Employee entity) {
        if (entity == null) {
            return null;
        }

        return EmployeeDTO.builder()
                .department(entity.getDepartment())
                .person(personMapper.mapToDTO(entity.getPerson()))
                .appUser(appUserMapper.mapToDTO(entity.getAppUser()))
                .build();
    }

    @Override
    public Employee mapFromDTO(EmployeeDTO dto) {
        if (dto == null) {
            return null;
        }

        return Employee.builder()
                .department(dto.getDepartment())
                .person(personMapper.mapFromDTO(dto.getPerson()))
                .appUser(appUserMapper.mapFromDTO(dto.getAppUser()))
                .build();
    }
}

