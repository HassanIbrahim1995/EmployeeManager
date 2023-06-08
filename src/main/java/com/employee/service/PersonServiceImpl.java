package com.employee.service;

import com.employee.exception.PersonException;
import com.employee.model.Person;
import com.employee.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements GenericService<Person> {

    private final PersonRepository personRepository;

    @Override
    public Person getById(Long id) {
        log.info("Getting Person by id: {}", id);
        return personRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Person with id {} not found", id);
                    return new PersonException("Person with id " + id + " not found.");
                });
    }

    @Override
    public Person save(Person entity) {
        log.info("Saving Person: {}", entity);
        try {
            Person savedPerson = personRepository.save(entity);
            return savedPerson;
        } catch (DataAccessException ex) {
            log.error("Unable to save Person: {}", entity, ex);
            throw new PersonException("Unable to save person " + entity, ex);
        }
    }

    @Override
    public void delete(Person entity) {
        log.info("Deleting Person: {}", entity);
        if (!personRepository.existsById(entity.getId())) {
            log.error("Person with id {} not found, unable to delete", entity.getId());
            throw new PersonException("Person with id " + entity.getId() + " not found, unable to delete.");
        }
        personRepository.delete(entity);
    }

    @Override
    public Person update(Person entity) {
        log.info("Updating Person: {}", entity);
        if (!personRepository.existsById(entity.getId())) {
            log.error("Person with id {} not found, unable to update", entity.getId());
            throw new PersonException("Person with id " + entity.getId() + " not found, unable to update.");
        }
        try {
            return personRepository.save(entity);
        } catch (DataAccessException ex) {
            log.error("Unable to update Person: {}", entity, ex);
            throw new PersonException("Unable to update person " + entity, ex);
        }
    }
}

