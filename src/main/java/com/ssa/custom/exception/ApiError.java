package com.ssa.custom.exception;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiError {

	private LocalDateTime timestamp;
	
	private HttpStatus status;
	
	private int statusCode;
	
	private String message;

	private List<String> errors;
	
	private String path;

	public ApiError(LocalDateTime timestamp, HttpStatus status, int statusCode, String message, List<String> errors,
			String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.statusCode = statusCode;
		this.message = message;
		this.errors = errors;
		this.path = path;
	}

	public ApiError(LocalDateTime timestamp, HttpStatus status, int statusCode, String message, String error,
			String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.statusCode = statusCode;
		this.message = message;
		this.errors = Arrays.asList(error);
		this.path = path;
	}
}
