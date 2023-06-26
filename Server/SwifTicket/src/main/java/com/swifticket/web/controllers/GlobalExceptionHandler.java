package com.swifticket.web.controllers;

import com.swifticket.web.models.dtos.response.MessageDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxUploadSizeExceeded(MaxUploadSizeExceededException ex) {
        String error = "Maximum file upload size exceeded.";
        return new ResponseEntity<>(new MessageDTO(error), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<?> handleValidationExceptions(Exception ex) {
        List<MessageDTO> errors = new ArrayList<>();

        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validationException = (MethodArgumentNotValidException) ex;
            BindingResult bindingResult = validationException.getBindingResult();

            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                // If the field where the error happened is needed, It can be added as parameter on the MessageDTO constructor
                String field = fieldError.getField();
                String message = fieldError.getDefaultMessage();
                errors.add(new MessageDTO(/*field,*/ message));
            }
        } else if (ex instanceof ConstraintViolationException) {
            ConstraintViolationException validationException = (ConstraintViolationException) ex;
            Set<ConstraintViolation<?>> constraintViolations = validationException.getConstraintViolations();

            for (ConstraintViolation<?> constraintViolation : constraintViolations) {
                String propertyPath = constraintViolation.getPropertyPath().toString();
                String message = constraintViolation.getMessage();
                errors.add(new MessageDTO(/*propertyPath,*/ message));
            }
        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
