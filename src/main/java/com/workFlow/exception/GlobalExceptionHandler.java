package com.workFlow.exception;

import com.workFlow.payload.ErrorResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponses> handleBadRequestException(HttpServletRequest request, BadRequestException ex) {
        ErrorResponses badRequest = new ErrorResponses("Bad request: " + ex.getMessage(),HttpStatus.BAD_REQUEST.value(),request.getRequestURI(),LocalDateTime.now(),"35" );
        return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponses> handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException ex) {
        ErrorResponses errorResponse = new ErrorResponses("Invalid argument: " + ex.getMessage(),HttpStatus.BAD_REQUEST.value(),request.getRequestURI(),LocalDateTime.now(),"36");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponses> handleNullPointerException(HttpServletRequest request, NullPointerException ex) {
        ErrorResponses errorResponse = new ErrorResponses("Null pointer encountered: " + ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value(),request.getRequestURI(),LocalDateTime.now(),"37");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponses> handleGlobalException(HttpServletRequest request, Exception ex) {
        ErrorResponses error = new ErrorResponses("Something went wrong: " + ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value(),request.getRequestURI(),LocalDateTime.now(),"500");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponses> handleNoHandlerFoundException(HttpServletRequest request, org.springframework.web.servlet.NoHandlerFoundException ex) {
        ErrorResponses errorResponse = new ErrorResponses("No handler found for the request",HttpStatus.NOT_FOUND.value(),request.getRequestURI(),LocalDateTime.now(),"404");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


}
