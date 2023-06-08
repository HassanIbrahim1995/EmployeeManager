package com.employee.service;

import com.employee.model.Person;
import com.employee.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements GenericService<Person> {

    private final PersonRepository personRepository;

    @Override
    public Optional<Person> getById(Long id) {
        log.info("Getting Person by id: {}", id);
        return personRepository.findById(id);
    }

    public Optional<Person> findByBsn(String bsn) {
        return personRepository.findPersonByBsn(bsn);
    }

    @Override
    public Optional<Person> save(Person person) {
        Optional<Person> foundPerson = personRepository.findPersonByBsn(person.getBsn());
        if (foundPerson.isPresent()) {
            log.warn("Person with BSN {} already exists, unable to save", person.getBsn());
            return Optional.empty();
        }
        return Optional.of(personRepository.save(person));
    }

    @Override
    public void delete(Person person) {
        Optional<Person> foundPerson = personRepository.findPersonByBsn(person.getBsn());
        foundPerson.ifPresent(p -> {
            personRepository.delete(p);
            log.warn("Person with BSN {} deleted", person.getBsn());
        });
    }

    @Override
    public Optional<Person> update(Person entity) {
        log.info("Updating Person: {}", entity);
        if (!personRepository.existsById(entity.getId())) {
            log.warn("Person with id {} not found, unable to update", entity.getId());
            return Optional.empty();
        }
        return Optional.of(personRepository.save(entity));
    }

    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }
}
