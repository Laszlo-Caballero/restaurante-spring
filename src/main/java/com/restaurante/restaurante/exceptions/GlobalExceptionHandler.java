package com.restaurante.restaurante.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.restaurante.restaurante.utils.ApiResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        ApiResponse<Map<String, String>> response = new ApiResponse<>(400, "Validation failed", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleSecurityException(Exception exception) {
        ApiResponse<Void> errorDetail = null;

        if (exception instanceof BadCredentialsException) {
            errorDetail = new ApiResponse<>(401, "The username or password is incorrect", null);

            return new ResponseEntity<>(errorDetail, HttpStatus.UNAUTHORIZED);
        }

        if (exception instanceof AccountStatusException) {
            errorDetail = new ApiResponse<>(403, "The account is locked", null);
            return new ResponseEntity<>(errorDetail, HttpStatus.FORBIDDEN);
        }

        if (exception instanceof AccessDeniedException) {
            errorDetail = new ApiResponse<>(403, "You are not authorized to access this resource", null);
            return new ResponseEntity<>(errorDetail, HttpStatus.FORBIDDEN);
        }

        if (exception instanceof SignatureException) {
            System.err.println("Signature exception occurred: " + exception.getMessage());
            errorDetail = new ApiResponse<>(403, "The JWT signature is invalid", null);
            return new ResponseEntity<>(errorDetail, HttpStatus.FORBIDDEN);
        }

        if (exception instanceof ExpiredJwtException) {
            errorDetail = new ApiResponse<>(403, "The JWT token has expired", null);
            return new ResponseEntity<>(errorDetail, HttpStatus.FORBIDDEN);
        }

        errorDetail = new ApiResponse<>(500, "Internal Server Error", null);
        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}