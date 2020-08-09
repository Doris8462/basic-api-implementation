package com.thoughtworks.rslist.exception;

public class InvalidIndexException extends RuntimeException {
    private String error;

    public InvalidIndexException(String error) {
        this.error = error;
    }

    @Override
    public String getMessage() {
        return error;
    }
}
