package com.ssa.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ssa.generator.SsnSequenceGenerator;
import com.ssa.models.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SSN_TBL")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class SsnEntity {
	
	@Id
	@Column(name = "SSN")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ssn_seq")
    @GenericGenerator(
        name = "ssn_seq", 
        strategy = "com.ssa.generator.SsnSequenceGenerator", 
        parameters = {
            @Parameter(name = SsnSequenceGenerator.INCREMENT_PARAM, value = "1"),
            @Parameter(name = SsnSequenceGenerator.NUMBER_FORMAT_PARAMETER, value = "%09d") })
	private String ssn;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	

	@Column(name = "LAST_NAME")
	private String lastName;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "GENDER")
	private Gender gender;
	
	
	@Column(name = "DOB")
	private LocalDate dateOfBirth;
	
	@Column(name = "STATE_NAME")
	private String stateName;
	
	@CreatedDate
	@Column(name = "CREATED_AT", updatable = false)
	private LocalDateTime createdAt;
	
	@LastModifiedDate
	@Column(name = "MODIFIED_AT", insertable = false)
	private LocalDateTime modifiedAt;
}