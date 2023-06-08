package com.employee.objectsMappers;

import com.employee.dto.AddressDTO;
import com.employee.model.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper implements Mapper<Address, AddressDTO> {

    @Override
    public AddressDTO mapToDTO(Address entity) {
        if (entity == null) {
            return null;
        }

        return AddressDTO.builder()
                .street(entity.getStreet())
                .city(entity.getCity())
                .state(entity.getState())
                .zipCode(entity.getZipCode())
                .build();
    }

    @Override
    public Address mapFromDTO(AddressDTO dto) {
        if (dto == null) {
            return null;
        }

        return Address.builder()
                .street(dto.getStreet())
                .city(dto.getCity())
                .state(dto.getState())
                .zipCode(dto.getZipCode())
                .build();
    }
}

