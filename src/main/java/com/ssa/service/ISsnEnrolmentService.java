package com.ssa.service;

import java.util.List;

import com.ssa.models.SsnDTO;
import com.ssa.models.SsnVerifier;

public interface ISsnEnrolmentService {
	
	public List<String> getAllStates();
	
	public SsnDTO ssnEnrol(SsnDTO ssnEnrol);
	
	public SsnVerifier findBySsnAndStateName(String ssn,String stateName);
	
	public String findBySsn(String ssn);
}
