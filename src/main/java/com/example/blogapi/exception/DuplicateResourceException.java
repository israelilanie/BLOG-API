package com.example.blogapi.exception;

public class DuplicateResourceException extends BlogApiException{
    public DuplicateResourceException(String message) {
        super(message);
    }
}
