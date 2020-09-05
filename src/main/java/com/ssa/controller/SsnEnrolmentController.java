package com.ssa.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ssa.models.SsnDTO;
import com.ssa.service.ISsnEnrolmentService;

@RestController
@Validated
public class SsnEnrolmentController {
	
	private ISsnEnrolmentService enrolmentService;

	public SsnEnrolmentController(ISsnEnrolmentService enrolmentService) {
		this.enrolmentService = enrolmentService;
	}
	
	@PostMapping("/ssnEnrol")
	public ResponseEntity<SsnDTO> ssnEnrol(@Valid @RequestBody SsnDTO ssnDTO) {
		SsnDTO ssnSavedInDb = enrolmentService.ssnEnrol(ssnDTO);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{ssn}").buildAndExpand(ssnSavedInDb.getSsn()).toUri();
		return ResponseEntity.created(location).body(ssnSavedInDb);
	}
	
	@GetMapping("/allStates")
	public List<String> getAllStates() {
		return enrolmentService.getAllStates();
	}
}