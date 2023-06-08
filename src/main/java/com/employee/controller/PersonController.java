package com.employee.controller;

import com.employee.ResponseMessage.ResponseMessage;
import com.employee.dto.PersonDTO;
import com.employee.exception.PersonException;
import com.employee.model.Person;
import com.employee.objectsMappers.Mapper;
import com.employee.service.PersonServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonServiceImpl personService;
    private final Mapper<Person, PersonDTO> personMapper;

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long id) {
        parseAndValidateId(String.valueOf(id));
        Optional<Person> person = personService.getById(id);
        if (person.isPresent()) {
            PersonDTO personDTO = personMapper.mapToDTO(person.get());
            return ResponseEntity.ok(personDTO);
        }
        throw new PersonException("Person with ID " + id + " not found.", HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<PersonDTO> createPerson(@RequestBody @Valid PersonDTO personDTO) {
        Person person = personMapper.mapFromDTO(personDTO);
        if (personService.findByBsn(person.getBsn()).isPresent()) {
            throw new PersonException("Person with BSN " + personDTO.getBsn() + " already exists.", HttpStatus.BAD_REQUEST);
        }
        Optional<Person> savedPerson = personService.save(person);
        PersonDTO savedPersonDTO = personMapper.mapToDTO(savedPerson.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPersonDTO);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PersonDTO>> getAllPersons() {
        List<Person> persons = personService.findAll();
        if (persons.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        List<PersonDTO> personDTOs = persons.stream()
                .map(personMapper::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(personDTOs);
    }


    @PutMapping
    public ResponseEntity<PersonDTO> updatePerson(@RequestBody @Valid PersonDTO personDTO) {
        Optional<Person> existingPerson = personService.findByBsn(personDTO.getBsn());
        if (existingPerson.isPresent()) {
            Person updatedPerson = personMapper.mapFromDTO(personDTO);
            updatedPerson.setId(existingPerson.get().getId());
            Optional<Person> savedPerson = personService.update(updatedPerson);
            if (savedPerson.isPresent()) {
                PersonDTO savedPersonDTO = personMapper.mapToDTO(savedPerson.get());
                return ResponseEntity.ok(savedPersonDTO);
            }
        }
        throw new PersonException("Person with name " + personDTO.getFirstName() + " not found.", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deletePerson(@PathVariable Long id) {
        parseAndValidateId(String.valueOf(id));
        Optional<Person> person = personService.getById(id);
        if (person.isPresent()) {
            personService.delete(person.get());
            return ResponseEntity.ok(new ResponseMessage("Person with name " + person.get().getFirstName() + " was deleted."));
        }
        throw new PersonException("Person with ID " + id + " not found.", HttpStatus.NOT_FOUND);
    }

    private void parseAndValidateId(String id) {
        try {
            long parsedId = Long.parseLong(id);
            if (parsedId < 0) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            throw new PersonException("Invalid ID: " + id, HttpStatus.BAD_REQUEST);
        }
    }

}

