package com.employee.dto;

import com.employee.model.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonDTO {

    @NotBlank(message = "First name cannot be empty.")
    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String middleName;

    @NotBlank(message = "Last name cannot be empty.")
    @Size(max = 50)
    private String lastName;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;

    private List<Address> addresses;

    @Size(min = 9, max = 9)
    @Pattern(regexp = "[0-9]+")
    @NotBlank(message = "Bsn cannot be empty.")
    private String bsn;

    @NotBlank(message = "Iban cannot be empty.")
    @Size(max = 50)
    private String iban;
}
