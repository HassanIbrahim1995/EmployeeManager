package com.employee.objectsMappers;

import com.employee.dto.PersonDTO;
import com.employee.model.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonMapperImpl implements Mapper<Person, PersonDTO> {

    @Override
    public PersonDTO mapToDTO(Person entity) {
        if (entity == null) {
            return null;
        }

        return PersonDTO.builder()
                .firstName(entity.getFirstName())
                .middleName(entity.getMiddleName())
                .lastName(entity.getLastName())
                .birthdate(entity.getBirthdate())
                .addresses(entity.getAddresses())
                .bsn(entity.getBsn())
                .iban(entity.getIban())
                .build();
    }

    @Override
    public Person mapFromDTO(PersonDTO dto) {
        if (dto == null) {
            return null;
        }

        return Person.builder()
                .firstName(dto.getFirstName())
                .middleName(dto.getMiddleName())
                .lastName(dto.getLastName())
                .birthdate(dto.getBirthdate())
                .addresses(dto.getAddresses())
                .bsn(dto.getBsn())
                .iban(dto.getIban())
                .build();
    }
}
