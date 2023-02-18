package com.librarymanagement.collegelibrarymanagementsystem.exception;

import org.modelmapper.spi.ErrorMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
@ControllerAdvice
public class LibraryManagementExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(LibraryException.class);

    @ExceptionHandler(value = {LibraryException.class})
    public ResponseEntity<Object> handlePipelineManagementServiceException(
            LibraryException exp, WebRequest request) {
        String errorDescription = exp.getLocalizedMessage();
        if (errorDescription == null || errorDescription.isEmpty()) {
            errorDescription = exp.getMessage();
        }

        logger.error(errorDescription, exp);

        ErrorMessage errorMessage = new ErrorMessage(errorDescription);
        return new ResponseEntity<>(
                errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST
        );
    }
}
