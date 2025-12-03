package com.desafio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String exception(Exception ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(ValidatorException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String validatorException(ValidatorException ex) {
        return ex.getMessage();
    }
}
