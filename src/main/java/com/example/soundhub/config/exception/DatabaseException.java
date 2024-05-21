package com.example.soundhub.config.exception;

public class DatabaseException extends RuntimeException {
	private final ErrorResponseStatus status;

	public DatabaseException(ErrorResponseStatus status) {
		this.status = status;
	}

	public ErrorResponseStatus getStatus() {
		return this.status;
	}
}
