package com.ssa.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssa.models.SsnVerifier;
import com.ssa.service.ISsnEnrolmentService;

@RestController
public class SsnValidatorController {
	
	private ISsnEnrolmentService enrolmentService;

	public SsnValidatorController(ISsnEnrolmentService enrolmentService) {
		this.enrolmentService = enrolmentService;
	}
	
	@GetMapping("/validate/{ssn}/{stateName}")
	public ResponseEntity<SsnVerifier> validateSsnByState(@PathVariable("ssn")String ssn,@PathVariable("stateName")String stateName) {
		SsnVerifier ssnVerifier = enrolmentService.findBySsnAndStateName(ssn, stateName);
		return ResponseEntity.ok(ssnVerifier);
	}
	
	@GetMapping("/validate/{ssn}")
	public ResponseEntity<String> validateSsn(@PathVariable("ssn")String ssn) {
		String stateName = enrolmentService.findBySsn(ssn);
		return ResponseEntity.ok(stateName);
	}
}