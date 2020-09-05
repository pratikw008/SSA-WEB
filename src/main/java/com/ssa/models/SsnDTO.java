package com.ssa.models;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SsnDTO {

	private String ssn;
	
	@NotEmpty(message = "first name must not be empty")
	private String firstName;

	@NotEmpty(message = "last name must not be empty")
	private String lastName;
	
	@NotNull(message = "Plz Provide Gender")
	private Gender gender;

	@Past(message = "DOB must be past date")
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate dateOfBirth;
	
	@NotEmpty(message = "Plz Provide State Name")
	private String stateName;
}
