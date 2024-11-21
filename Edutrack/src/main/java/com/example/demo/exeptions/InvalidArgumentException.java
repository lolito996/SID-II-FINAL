package com.example.demo.exeptions;

public class InvalidArgumentException extends RuntimeException {
    
    public InvalidArgumentException(String message) {
        super(message);
    }
}