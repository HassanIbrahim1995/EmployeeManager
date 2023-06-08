package com.employee.repository;

import com.employee.model.Person;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findPersonByBsn(@Valid String bsn);
}
