package com.employee.controller;

import com.employee.dto.PersonDTO;
import com.employee.model.Person;
import com.employee.objectsMappers.Mapper;
import com.employee.service.GenericService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonController {

    private final GenericService<Person> personService;
    private final Mapper<Person, PersonDTO> personMapper;

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long id) {
        Person person = personService.getById(id);
        PersonDTO personDTO = personMapper.mapToDTO(person);
        return ResponseEntity.ok(personDTO);
    }

    @PostMapping
    public ResponseEntity<PersonDTO> createPerson(@RequestBody PersonDTO personDTO) {
        Person person = personMapper.mapFromDTO(personDTO);
        Person savedPerson = personService.save(person);
        PersonDTO savedPersonDTO = personMapper.mapToDTO(savedPerson);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPersonDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable Long id, @RequestBody PersonDTO personDTO) {
        Person existingPerson = personService.getById(id);
        Person updatedPerson = personMapper.mapFromDTO(personDTO);
        updatedPerson.setId(existingPerson.getId());
        Person savedPerson = personService.update(updatedPerson);
        PersonDTO savedPersonDTO = personMapper.mapToDTO(savedPerson);
        return ResponseEntity.ok(savedPersonDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        Person person = personService.getById(id);
        personService.delete(person);
        return ResponseEntity.noContent().build();
    }
}

