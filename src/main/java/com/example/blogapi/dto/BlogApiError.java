package com.example.blogapi.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


public class BlogApiError {
    private int status;
    private String message;
    private String errorCode;
    private LocalDateTime timestamp;
    private String path;

    public BlogApiError(int status, String message, String errorCode, String path) {
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
        this.path = path;
    }

}
