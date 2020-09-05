package com.ssa.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssa.entities.SsnEntity;

public interface SsnEntityRepository extends JpaRepository<SsnEntity, Serializable> {
	
	public SsnEntity findBySsnAndStateName(String ssn,String stateName);
	
	public SsnEntity findBySsn(String ssn);
	
}
