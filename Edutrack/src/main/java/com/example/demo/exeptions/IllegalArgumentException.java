package com.example.demo.exeptions;

public class IllegalArgumentException extends RuntimeException {
    
    public IllegalArgumentException(String message) {
        super(message);
    }
}
