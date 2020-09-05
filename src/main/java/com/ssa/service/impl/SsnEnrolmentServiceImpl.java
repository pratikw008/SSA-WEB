package com.ssa.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ssa.custom.exception.SsnEntityNotFoundException;
import com.ssa.entities.SsnEntity;
import com.ssa.models.SsnDTO;
import com.ssa.models.SsnVerifier;
import com.ssa.repository.SsnEntityRepository;
import com.ssa.repository.StateEntityRepository;
import com.ssa.service.ISsnEnrolmentService;
import com.ssa.utils.SsnMapper;

@Service
public class SsnEnrolmentServiceImpl implements ISsnEnrolmentService {

	private StateEntityRepository stateRepo;

	private SsnEntityRepository ssnRepo;

	public SsnEnrolmentServiceImpl(StateEntityRepository stateRepo, SsnEntityRepository ssnRepo) {
		this.stateRepo = stateRepo;
		this.ssnRepo = ssnRepo;
	}

	@Override
	public List<String> getAllStates() {
		return stateRepo.getAllStateNames();
	}

	@Override
	public SsnDTO ssnEnrol(SsnDTO ssnEnrol) {
		SsnEntity ssnEntity = SsnMapper.convertSsnDTOToSsnEntity(ssnEnrol);
		SsnEntity savedInDb = ssnRepo.save(ssnEntity);
	
		return Optional.ofNullable(savedInDb)
					   .map(ssnEnt ->SsnMapper.convertSsnEntityToSsnDTO(ssnEnt))
					   .orElseThrow(()-> new SsnEntityNotFoundException("Failed To Create SsnEntity"));
	}
	
	@Override
	public SsnVerifier findBySsnAndStateName(String ssn, String stateName) {
		SsnEntity ssnEntityFrmDb = ssnRepo.findBySsnAndStateName(ssn, stateName);
		return Optional.ofNullable(ssnEntityFrmDb)
					   .map(ssnEnt -> SsnVerifier.VALID)
					   .orElse(SsnVerifier.INVALID);
	}
	
	@Override
	public String findBySsn(String ssn) {
		SsnEntity ssnSavedInDb = ssnRepo.findBySsn(ssn);
		
		return Optional.ofNullable(ssnSavedInDb)
					   .map(SsnEntity::getStateName)
					   .orElse("Plz Provide valid SSN Number");
	}
}