package com.example.soundhub.config.exception;

public class AwsS3Exception extends RuntimeException {

	private final ErrorResponseStatus status;

	public AwsS3Exception(ErrorResponseStatus status) {
		this.status = status;
	}

	public ErrorResponseStatus getStatus() {
		return this.status;
	}
}
