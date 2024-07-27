package com.myblog.exception;

import com.myblog.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFound e, WebRequest webRequest){

        ErrorDetails error = new ErrorDetails(new Date(), e.getMessage(),  webRequest.getDescription(true));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> globalExceptionHandler(Exception e,WebRequest webRequest){

        ErrorDetails error = new ErrorDetails(new Date(),e.getMessage(),webRequest.getDescription(true));
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);

    }


}
