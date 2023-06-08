package com.employee.objectsMappers;

import com.employee.dto.AddressDTO;
import com.employee.dto.PersonDTO;
import com.employee.model.Address;
import com.employee.model.Person;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersonMapperImpl implements Mapper<Person, PersonDTO> {

    private final AddressMapper addressMapper;

    public PersonMapperImpl(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    @Override
    public PersonDTO mapToDTO(Person entity) {
        if (entity == null) {
            return null;
        }

        List<AddressDTO> addressDTOs = entity.getAddresses()
                .stream()
                .map(addressMapper::mapToDTO)
                .collect(Collectors.toList());

        return PersonDTO.builder()
                .firstName(entity.getFirstName())
                .middleName(entity.getMiddleName())
                .lastName(entity.getLastName())
                .birthdate(entity.getBirthdate())
                .addresses(addressDTOs)
                .bsn(entity.getBsn())
                .iban(entity.getIban())
                .build();
    }

    @Override
    public Person mapFromDTO(PersonDTO dto) {
        if (dto == null) {
            return null;
        }

        Person person = Person.builder()
                .firstName(dto.getFirstName())
                .middleName(dto.getMiddleName())
                .lastName(dto.getLastName())
                .birthdate(dto.getBirthdate())
                .bsn(dto.getBsn())
                .iban(dto.getIban())
                .build();

        List<Address> addresses = dto.getAddresses()
                .stream()
                .map(addressMapper::mapFromDTO)
                .peek(address -> address.setPerson(person))
                .toList();

        person.setAddresses(addresses);

        return person;
    }

}
