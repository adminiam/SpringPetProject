package com.example.securityproject.exception;

public class SuppressedStackTraceException extends RuntimeException {
    public SuppressedStackTraceException(String message) {
        super(message, null, false, false);
    }
}