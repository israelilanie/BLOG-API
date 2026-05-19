package com.example.blogapi.exception;

public class AccessDeniedException extends BlogApiException{
    public AccessDeniedException(String message) {
        super(message);
    }
}
