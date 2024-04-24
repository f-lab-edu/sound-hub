package com.example.soundhub.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getStatus().getCode(), ex.getMessage());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<Object> handleDatabaseException(DatabaseException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getStatus().getCode(), ex.getMessage());

		return new ResponseEntity<>(errorResponse, HttpStatusCode.valueOf(errorResponse.getCode()));
	}
}
