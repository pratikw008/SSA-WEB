package com.ssa.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssa.entities.StateEntity;

public interface StateEntityRepository extends JpaRepository<StateEntity, Serializable> {
	
	@Query("select s.stateName from StateEntity s")
	public List<String> getAllStateNames();
}
