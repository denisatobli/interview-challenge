package com.clevershuttle.interviewchallenge.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleBaseExceptionException(BaseException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getErrorId(), ex.getMessage()), ex.getStatus());
    }

    record ErrorResponse(String errorId, String errorMessage) {
    }

}
