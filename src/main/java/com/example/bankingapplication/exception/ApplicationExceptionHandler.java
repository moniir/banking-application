package com.example.bankingapplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @Created 06/07/2022
 * @Author monir.hossain
 */

@RestControllerAdvice
public class ApplicationExceptionHandler {


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> handleInvalidArguement(MethodArgumentNotValidException ex){
            Map<String, String> errorMap = new HashMap<>();
            ex.getBindingResult().getFieldErrors().forEach(error -> {
                errorMap.put(error.getField(),error.getDefaultMessage());
            });
            return errorMap;
    }

        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(NotFoundException.class)
        public Map<String,String> handleBusinessException(NotFoundException ex){
                Map<String,String> errorMap = new HashMap<>();
                errorMap.put("errorMessage",ex.getMessage());

                return errorMap;
        }
}
