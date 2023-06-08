package com.employee.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Builder
public class Person {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @Size(max = 50)
    @NotBlank(message = "First name can not be empty.")
    private String firstName;

    @Column
    @Size(max = 50)
    private String middleName;

    @Column
    @Size(max = 50)
    @NotBlank(message = "Last name cannot be empty.")
    private String lastName;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "person")
    @ToString.Exclude
    private List<Address> addresses =new ArrayList<>();

    @Column
    @Size(min = 9, max = 9)
    @Pattern(regexp = "[0-9]+")
    @NotBlank(message = "Bsn cannot be empty.")
    private String bsn;

    @Column
    @Size(max = 50)
    @NotBlank(message = "Iban cannot be empty.")
    private String iban;

    public Person(String firstName, String middleName, String lastName, Date birthdate, List<Address> addresses, String bsn, String iban) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.addresses = addresses;
        this.bsn = bsn;
        this.iban = iban;
    }

    public void addAddress(Address address) {
        addresses.add(address);
        address.setPerson(this);
    }

    public void removeAddress(Address address) {
        addresses.remove(address);
        address.setPerson(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Person person = (Person) o;
        return getId() != null && Objects.equals(getId(), person.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
