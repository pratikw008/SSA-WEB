package com.ssa.utils;

import java.util.Optional;

import org.springframework.beans.BeanUtils;

import com.ssa.custom.exception.SsnDTONotFoundException;
import com.ssa.custom.exception.SsnEntityNotFoundException;
import com.ssa.entities.SsnEntity;
import com.ssa.models.SsnDTO;

public class SsnMapper {
	
	public static SsnEntity convertSsnDTOToSsnEntity(SsnDTO ssnDTO) {
		return Optional.ofNullable(ssnDTO)
					   .map(dto -> {
						   SsnEntity ssnEntity = new SsnEntity();
						   BeanUtils.copyProperties(dto, ssnEntity);
						   return ssnEntity;
					   })
					   .orElseThrow(()-> new SsnDTONotFoundException("Exception During Convertion of SsnDTO To SsnEntity"));
	}
	
	public static SsnDTO convertSsnEntityToSsnDTO(SsnEntity ssnEntity) {
		return Optional.ofNullable(ssnEntity)
					   .map(ssnEnt -> {
						   SsnDTO ssnDTO = new SsnDTO();
						   BeanUtils.copyProperties(ssnEnt, ssnDTO);
						   return ssnDTO;
					   })
					   .orElseThrow(()-> new SsnEntityNotFoundException("Exception During Convertion of SsnEntity To SsnEnrolment"));
	}
}