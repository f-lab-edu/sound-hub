package com.example.soundhub.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		BindingResult bindingResult = ex.getBindingResult();

		StringBuilder builder = new StringBuilder();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			builder.append("[");
			builder.append(fieldError.getField());
			builder.append("] 입력 에러 : ");
			builder.append(fieldError.getDefaultMessage());
			builder.append(" 입력된 값: [");
			builder.append(fieldError.getRejectedValue());
			builder.append("]");
			builder.append(System.lineSeparator());
		}
		ErrorResponse errorResponse = new ErrorResponse(2000, builder.toString());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
