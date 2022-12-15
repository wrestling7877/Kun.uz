package com.example.controller;

import com.example.exp.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Tag(name = "ExceptionHandlerController API", description = "Api list for ExceptionHandlerController")

public class ExceptionHandlerController {

    @ExceptionHandler({JwtNotValidException.class})
    public ResponseEntity<?> handler(JwtNotValidException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler({AuthorizationFailedException.class})
    public ResponseEntity<?> handler(AuthorizationFailedException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<?> handler(ForbiddenException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }


    @ExceptionHandler({ItemNotFoundException.class})
    public ResponseEntity<?> handler(ItemNotFoundException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler({EmailAlreadyExistsException.class})
    public ResponseEntity<?> handler(EmailAlreadyExistsException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }


    @ExceptionHandler({AttachNotFoundException.class})
    private ResponseEntity<?> handler(AttachNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
