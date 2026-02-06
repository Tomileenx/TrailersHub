package com.example.Trailers.exception;

public class TrailerNotFoundException extends RuntimeException {
    public TrailerNotFoundException(String message) {
        super(message);
    }
}
