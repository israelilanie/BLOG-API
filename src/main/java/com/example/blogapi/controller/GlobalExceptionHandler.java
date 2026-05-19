package com.example.blogapi.controller;

import com.example.blogapi.dto.BlogApiError;
import com.example.blogapi.exception.AccessDeniedException;
import com.example.blogapi.exception.DuplicateResourceException;
import com.example.blogapi.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BlogApiError> handleNotFound(ResourceNotFoundException exception, HttpServletRequest request){
        BlogApiError blogApiError = new BlogApiError(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                "RESOURCE_NOT_FOUND",
                request.getRequestURI()
        );

        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(blogApiError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.put(err.getField(), err.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<BlogApiError> handleAccessDenied(
            AccessDeniedException exception, HttpServletRequest request) {
        BlogApiError blogApiError = new BlogApiError(
                HttpStatus.FORBIDDEN.value(),
                exception.getMessage(),
                "ACCESS_DENIED",
                request.getRequestURI()
        );

        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(blogApiError);

    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<BlogApiError> handleDuplicate(
            DuplicateResourceException ex,
            HttpServletRequest request) {

        BlogApiError blogApiError = new BlogApiError(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                "DUPLICATE_RESOURCE",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(blogApiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BlogApiError> handleGeneric(
            Exception ex,
            HttpServletRequest request) {

        BlogApiError blogApiError = new BlogApiError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Something went wrong",
                "INTERNAL_ERROR",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(blogApiError);
    }

}
