package com.employee.RepositoryTests;

import com.employee.model.Address;
import com.employee.repository.AddressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void testSaveAddress() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("City");
        address.setState("State");
        address.setZipCode("12345");
        Address savedAddress = addressRepository.save(address);
        Assertions.assertNotNull(savedAddress.getId());
    }

    @Test
    public void testFindById() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("City");
        address.setState("State");
        address.setZipCode("12345");
        Address savedAddress = addressRepository.save(address);
        Optional<Address> foundAddressOptional = addressRepository.findById(savedAddress.getId());
        Assertions.assertTrue(foundAddressOptional.isPresent());
        Address foundAddress = foundAddressOptional.get();
        Assertions.assertEquals(savedAddress.getId(), foundAddress.getId());
        Assertions.assertEquals(savedAddress.getStreet(), foundAddress.getStreet());
        Assertions.assertEquals(savedAddress.getCity(), foundAddress.getCity());
        Assertions.assertEquals(savedAddress.getState(), foundAddress.getState());
        Assertions.assertEquals(savedAddress.getZipCode(), foundAddress.getZipCode());
    }

    @Test
    public void testUpdateAddress() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("City");
        address.setState("State");
        address.setZipCode("12345");
        Address savedAddress = addressRepository.save(address);
        savedAddress.setZipCode("54321");
        Address updatedAddress = addressRepository.save(savedAddress);
        Assertions.assertEquals(savedAddress.getId(), updatedAddress.getId());
        Assertions.assertEquals(savedAddress.getStreet(), updatedAddress.getStreet());
        Assertions.assertEquals(savedAddress.getCity(), updatedAddress.getCity());
        Assertions.assertEquals(savedAddress.getState(), updatedAddress.getState());
        Assertions.assertEquals(savedAddress.getZipCode(), updatedAddress.getZipCode());
    }

    @Test
    public void testDeleteAddress() {
        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("City");
        address.setState("State");
        address.setZipCode("12345");
        Address savedAddress = addressRepository.save(address);
        addressRepository.delete(savedAddress);
        Optional<Address> deletedAddressOptional = addressRepository.findById(savedAddress.getId());
        Assertions.assertFalse(deletedAddressOptional.isPresent());
    }

    @Test
    public void testFindAllAddresses() {
        Address address1 = new Address();
        address1.setStreet("123 Main St");
        address1.setCity("City");
        address1.setState("State");
        address1.setZipCode("12345");
        addressRepository.save(address1);
        Address address2 = new Address();
        address2.setStreet("456 Elm St");
        address2.setCity("City");
        address2.setState("State");
        address2.setZipCode("54321");
        addressRepository.save(address2);
        List<Address> allAddresses = addressRepository.findAll();
        Assertions.assertEquals(2, allAddresses.size());
    }
}

