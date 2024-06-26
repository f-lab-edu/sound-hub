package com.example.soundhub.config.exception;

public class BadRequestException extends RuntimeException {
	private final ErrorResponseStatus status;

	public BadRequestException(ErrorResponseStatus status) {
		this.status = status;
	}

	public ErrorResponseStatus getStatus() {
		return this.status;
	}
}
