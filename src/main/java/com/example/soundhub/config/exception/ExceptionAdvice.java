package com.example.soundhub.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getStatus().getCode(), ex.getStatus().getMessage());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<ErrorResponse> handleDatabaseException(DatabaseException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getStatus().getCode(), ex.getStatus().getMessage());

		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
