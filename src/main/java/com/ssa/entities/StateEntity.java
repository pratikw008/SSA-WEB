package com.ssa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "STATE_MASTER")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StateEntity {
	
	@Id
	@Column(name = "STATE_ID")
	private Long stateId;
	
	@Column(name = "STATE_NAME")
	private String stateName;
}