package com.employee.RepositoryTests;

import com.employee.model.Person;
import com.employee.repository.PersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PersonRepositoryTest {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testSavePerson() {
        Person person = createPerson("John", "Doe", new Date(), "123456789", "NL12ABCD34567890");

        Person savedPerson = personRepository.save(person);

        Assertions.assertNotNull(savedPerson.getId());
    }

    @Test
    public void testFindOnePerson() {
        Person person = createPerson("John", "Doe", new Date(), "123456789", "NL12ABCD34567890");

        Person savedPerson = personRepository.save(person);

        Optional<Person> foundPersonOptional = personRepository.findById(savedPerson.getId());
        Assertions.assertTrue(foundPersonOptional.isPresent());

        Person foundPerson = foundPersonOptional.get();
        Assertions.assertEquals(savedPerson.getId(), foundPerson.getId());
        Assertions.assertEquals(savedPerson.getFirstName(), foundPerson.getFirstName());
        Assertions.assertEquals(savedPerson.getLastName(), foundPerson.getLastName());
    }

    @Test
    public void testUpdatePerson() {
        Person person = createPerson("John", "Doe", new Date(), "123456789", "NL12ABCD34567890");

        Person savedPerson = personRepository.save(person);

        savedPerson.setFirstName("Jane");
        Person updatedPerson = personRepository.save(savedPerson);

        Assertions.assertEquals(savedPerson.getId(), updatedPerson.getId());
        Assertions.assertEquals("Jane", updatedPerson.getFirstName());
    }

    @Test
    public void testDeletePerson() {
        Person person = createPerson("John", "Doe", new Date(), "123456789", "NL12ABCD34567890");

        Person savedPerson = personRepository.save(person);

        personRepository.delete(savedPerson);

        Optional<Person> deletedPersonOptional = personRepository.findById(savedPerson.getId());
        Assertions.assertFalse(deletedPersonOptional.isPresent());
    }

    @Test
    public void testFindAllPersons() {
        Person person1 = createPerson("John", "Doe", new Date(), "123456789", "NL12ABCD34567890");
        personRepository.save(person1);

        Person person2 = createPerson("Jane", "Smith", new Date(), "987654321", "NL98ZYXW12345678");
        personRepository.save(person2);

        List<Person> allPersons = personRepository.findAll();
        Assertions.assertEquals(2, allPersons.size());
    }

    private Person createPerson(String firstName, String lastName, Date birthdate, String bsn, String iban) {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setBirthdate(birthdate);
        person.setBsn(bsn);
        person.setIban(iban);
        // Set other fields as needed
        return person;
    }

}
