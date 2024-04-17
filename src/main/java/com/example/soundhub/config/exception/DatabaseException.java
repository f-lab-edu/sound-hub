package com.example.soundhub.config.exception;

public class DatabaseException extends RuntimeException{
    private ErrorResponseStatus status;

    public DatabaseException(ErrorResponseStatus status) {
        this.status = status;
    }

    public ErrorResponseStatus getStatus() {
        return this.status;
    }
}
