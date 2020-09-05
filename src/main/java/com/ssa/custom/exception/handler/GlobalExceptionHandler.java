package com.ssa.custom.exception.handler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ssa.custom.exception.ApiError;
import com.ssa.custom.exception.SsnDTONotFoundException;
import com.ssa.custom.exception.SsnEntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(SsnEntityNotFoundException.class)
	public ResponseEntity<ApiError> handleSsnEntityNotFoundException(SsnEntityNotFoundException ex ,WebRequest request) {
		ApiError error = new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), "SsnEntity Not Found", ex.getLocalizedMessage(), request.getDescription(false));
		return new ResponseEntity<ApiError>(error,HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(SsnDTONotFoundException.class)
	public ResponseEntity<ApiError> handleSsnDTONotFoundException(SsnDTONotFoundException ex ,WebRequest request) {
		ApiError error = new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), "SsnDTO Not Found", ex.getLocalizedMessage(), request.getDescription(false));
		return new ResponseEntity<ApiError>(error,HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

		List<String> fieldErrors = ex.getBindingResult().getFieldErrors()
												   .stream()
												   .map(field -> field.getField()+" : "+field.getRejectedValue())
												   .collect(Collectors.toList());
		
		List<String> objError = ex.getBindingResult()
								  .getAllErrors()
							 	  .stream()
							 	  .map(objectError -> objectError.getObjectName()+" : "+objectError.getDefaultMessage())
							 	  .collect(Collectors.toList());

		List<String> errors = Stream.concat(fieldErrors.stream(), objError.stream())
									.collect(Collectors.toList());

		ApiError error = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), "Validation Failed", errors, request.getDescription(false));
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Throwable mostSpecificCause = ex.getMostSpecificCause();

		ApiError apiError = Optional.ofNullable(mostSpecificCause)
									.map(throwable -> new ApiError(LocalDateTime.now(), 
																   HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), 
																   "Plz Provide Request Body", throwable.getMessage(), 
																   request.getDescription(false)))
									.orElse(new ApiError(LocalDateTime.now(), 
														 HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), 
														 "Plz Provide Request Body", mostSpecificCause.getMessage(), 
														 request.getDescription(false)));
		
		return new ResponseEntity<>(apiError,HttpStatus.BAD_REQUEST);
	}
}