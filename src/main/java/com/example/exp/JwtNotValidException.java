package com.example.exp;

public class JwtNotValidException extends RuntimeException{
    public JwtNotValidException(String message) {
        super(message);
    }
}
