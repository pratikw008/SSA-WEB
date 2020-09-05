package com.ssa.custom.exception;

public class SsnEntityNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1091917997495886694L;
	
	public SsnEntityNotFoundException(String message) {
		super(message);
	}
}
