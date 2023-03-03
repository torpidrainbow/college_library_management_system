package com.librarymanagement.collegelibrarymanagementsystem.exception;

import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class LibraryManagementExceptionHandler {

    @ExceptionHandler(value = {LibraryException.class})
    public ResponseEntity<Object> handleLibraryException(
            LibraryException exp, WebRequest request) {
        String errorDescription = exp.getMessage();

        return new ResponseEntity<>(
                new ErrorMessage(errorDescription), new HttpHeaders(), HttpStatus.BAD_REQUEST
        );
    }
}
