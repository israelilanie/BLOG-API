package com.example.blogapi.exception;

public class InvalidRequestException extends BlogApiException{
    public InvalidRequestException(String message) {
        super(message);
    }
}
