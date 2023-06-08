package com.employee.service;

import com.employee.model.Address;
import com.employee.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements GenericService<Address> {

    private final AddressRepository addressRepository;

    @Override
    public Optional<Address> getById(Long id) {
        log.info("Getting Address by id: {}", id);
        return addressRepository.findById(id);
    }

    @Override
    public Optional<Address> save(Address entity) {
        log.info("Saving Address: {}", entity);
        Address savedAddress = addressRepository.save(entity);
        return Optional.of(savedAddress);
    }

    @Override
    public void delete(Address entity) {
        log.info("Deleting Address: {}", entity);
        if (addressRepository.existsById(entity.getId())) {
            addressRepository.delete(entity);
        }
    }

    @Override
    public Optional<Address> update(Address entity) {
        log.info("Updating Address: {}", entity);
        if (addressRepository.existsById(entity.getId())) {
            Address updatedAddress = addressRepository.save(entity);
            return Optional.of(updatedAddress);
        }
        return Optional.empty();
    }

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }
}
