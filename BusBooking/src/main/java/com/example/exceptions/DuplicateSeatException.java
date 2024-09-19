package com.example.exceptions;

public class DuplicateSeatException extends Exception {
    public DuplicateSeatException(String message) {
        super(message);
    }
}
