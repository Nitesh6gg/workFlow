package com.workflow.exception;

import com.workflow.payload.ErrorResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import java.time.LocalDateTime;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private String generateTraceId() {
        return "Wf-" + UUID.randomUUID().toString();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponses> handleGlobalException(HttpServletRequest request, Exception ex) {
        ErrorResponses error = new ErrorResponses(ex.getMessage(),"Something went wrong!",request.getRequestURI(),request.getMethod(),HttpStatus.INTERNAL_SERVER_ERROR.value(),LocalDateTime.now(),generateTraceId());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponses> handleBadRequestException(HttpServletRequest request, BadRequestException ex) {
        ErrorResponses badRequest = new ErrorResponses( ex.getMessage(), "Bad request!", request.getRequestURI(), request.getMethod(), HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), generateTraceId());
        return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponses> handleRuntimeException(HttpServletRequest request, RuntimeException ex) {
        ErrorResponses errorResponse = new ErrorResponses(ex.getMessage(), "Runtime exception occurred!", request.getRequestURI(), request.getMethod(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now(), generateTraceId());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponses> handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException ex) {
        ErrorResponses errorResponse = new ErrorResponses(ex.getMessage(),"Invalid argument!",request.getRequestURI(), request.getMethod(),HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), generateTraceId());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnAuthoriseException.class)
    public ResponseEntity<ErrorResponses> handleUnauthorisedException(HttpServletRequest request, Exception ex) {
        ErrorResponses errorResponse = new ErrorResponses(ex.getMessage(), "Unauthorized access!", request.getRequestURI(), request.getMethod(), HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now(), generateTraceId());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponses> handleNullPointerException(HttpServletRequest request, NullPointerException ex) {
        ErrorResponses errorResponse = new ErrorResponses(ex.getMessage(), "Null pointer encountered!", request.getRequestURI(), request.getMethod(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now(), generateTraceId());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponses> handleNoHandlerFoundException(HttpServletRequest request, NoHandlerFoundException ex) {
        ErrorResponses errorResponse = new ErrorResponses(ex.getMessage(), "No handler found for the request!", request.getRequestURI(), request.getMethod(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), generateTraceId());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
