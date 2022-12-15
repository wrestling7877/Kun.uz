package com.example.exp;

public class AuthorizationFailedException extends RuntimeException{

    public AuthorizationFailedException(String message) {
        super(message);
    }
}
