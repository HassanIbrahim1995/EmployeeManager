package com.employee.controller;

import com.employee.dto.AddressDTO;
import com.employee.model.Address;
import com.employee.objectsMappers.Mapper;
import com.employee.service.GenericService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final GenericService<Address> addressService;
    private final Mapper<Address, AddressDTO> addressMapper;

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long id) {
        Address address = addressService.getById(id);
        AddressDTO addressDTO = addressMapper.mapToDTO(address);
        return ResponseEntity.ok(addressDTO);
    }

    @PostMapping
    public ResponseEntity<AddressDTO> createAddress(@RequestBody AddressDTO addressDTO) {
        Address address = addressMapper.mapFromDTO(addressDTO);
        Address savedAddress = addressService.save(address);
        AddressDTO savedAddressDTO = addressMapper.mapToDTO(savedAddress);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAddressDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long id, @RequestBody AddressDTO addressDTO) {
        Address existingAddress = addressService.getById(id);
        Address updatedAddress = addressMapper.mapFromDTO(addressDTO);
        updatedAddress.setId(existingAddress.getId());
        Address savedAddress = addressService.update(updatedAddress);
        AddressDTO savedAddressDTO = addressMapper.mapToDTO(savedAddress);
        return ResponseEntity.ok(savedAddressDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        Address address = addressService.getById(id);
        addressService.delete(address);
        return ResponseEntity.noContent().build();
    }
}

