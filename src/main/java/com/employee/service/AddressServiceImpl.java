package com.employee.service;

import com.employee.exception.AddressException;
import com.employee.model.Address;
import com.employee.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements GenericService<Address> {

    private final AddressRepository addressRepository;

    @Override
    public Address getById(Long id) {
        log.info("Getting Address by id: {}", id);
        return addressRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Address with id {} not found", id);
                    return new AddressException("Address with id " + id + " not found.");
                });
    }

    @Override
    public Address save(Address entity) {
        log.info("Saving Address: {}", entity);
        try {
            return addressRepository.save(entity);
        } catch (DataAccessException ex) {
            log.error("Unable to save Address: {}", entity, ex);
            throw new AddressException(String.format("Unable to save address %s", entity), ex);
        }
    }

    @Override
    public void delete(Address entity) {
        log.info("Deleting Address: {}", entity);
        if (!addressRepository.existsById(entity.getId())) {
            log.error("Address with id {} not found, unable to delete", entity.getId());
            throw new AddressException("Address with id " + entity.getId() + " not found, unable to delete.");
        }
        addressRepository.delete(entity);
    }

    @Override
    public Address update(Address entity) {
        log.info("Updating Address: {}", entity);
        if (!addressRepository.existsById(entity.getId())) {
            log.error("Address with id {} not found, unable to update", entity.getId());
            throw new AddressException("Address with id " + entity.getId() + " not found, unable to update.");
        }
        try {
            return addressRepository.save(entity);
        } catch (DataAccessException ex) {
            log.error("Unable to update Address: {}", entity, ex);
            throw new AddressException(String.format("Unable to update address %s", entity), ex);
        }
    }
}


