package com.swifticket.web.controllers;

import com.swifticket.web.models.dtos.response.MessageDTO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxUploadSizeExceeded(MaxUploadSizeExceededException ex) {
        String error = "Maximum file upload size exceeded.";
        return new ResponseEntity<>(new MessageDTO(error), HttpStatus.BAD_REQUEST);
    }
}
