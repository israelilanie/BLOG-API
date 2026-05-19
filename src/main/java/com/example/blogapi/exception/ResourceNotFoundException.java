package com.example.blogapi.exception;

public class ResourceNotFoundException extends BlogApiException{
    public ResourceNotFoundException(String resource, Long id) {
        super(resource + " with id " + id + " not found");
    }
}
