package com.employee.controller;

import com.employee.dto.AddressDTO;
import com.employee.exception.AddressException;
import com.employee.model.Address;
import com.employee.objectsMappers.Mapper;
import com.employee.service.GenericService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final GenericService<Address> addressService;
    private final Mapper<Address, AddressDTO> addressMapper;

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long id) {
        Optional<Address> addressOptional = addressService.getById(id);
        if (addressOptional.isPresent()) {
            Address address = addressOptional.get();
            AddressDTO addressDTO = addressMapper.mapToDTO(address);
            return ResponseEntity.ok(addressDTO);
        } else {
            throw new AddressException("Address with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<AddressDTO> createAddress(@RequestBody AddressDTO addressDTO) {
        Address address = addressMapper.mapFromDTO(addressDTO);
        Optional<Address> savedAddressOptional = addressService.save(address);
        if (savedAddressOptional.isPresent()) {
            Address savedAddress = savedAddressOptional.get();
            AddressDTO savedAddressDTO = addressMapper.mapToDTO(savedAddress);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAddressDTO);
        } else {
            throw new AddressException("Failed to create address.", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long id, @RequestBody AddressDTO addressDTO) {
        Address existingAddress = addressService.getById(id)
                .orElseThrow(() -> new AddressException("Address with ID " + id + " not found.", HttpStatus.NOT_FOUND));

        Address updatedAddress = addressMapper.mapFromDTO(addressDTO);
        updatedAddress.setId(existingAddress.getId());

        Optional<Address> savedAddressOptional = addressService.update(updatedAddress);
        Address savedAddress = savedAddressOptional.get();
        AddressDTO savedAddressDTO = addressMapper.mapToDTO(savedAddress);
        return ResponseEntity.ok(savedAddressDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        Optional<Address> addressOptional = addressService.getById(id);
        if (addressOptional.isPresent()) {
            Address address = addressOptional.get();
            addressService.delete(address);
            return ResponseEntity.noContent().build();
        } else {
            throw new AddressException("Address with ID " + id + " not found.", HttpStatus.NOT_FOUND);
        }
    }
}
