package org.example.diplomski.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({UserIdNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleUserNotFound(Exception ex, WebRequest request) {
        ex.printStackTrace();
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({PostNotFoundException.class})
    public ResponseEntity<ErrorResponse> handlePostNotFound(Exception ex, WebRequest request) {
        ex.printStackTrace();
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, request);
    }



    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleInvalidEnumValue(HttpMessageNotReadableException ex, WebRequest request) {
        if (ex.getMessage().contains("RoleType")) {
            return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request);
        }
        return buildErrorResponse(
                new Exception("Malformed JSON request."), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EmailTakenException.class)
    public ResponseEntity<ErrorResponse> handleEmailTaken(Exception ex, WebRequest request) {
        ex.printStackTrace();
        return buildErrorResponse(ex, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(UserEmailNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserEmailNotFound(Exception ex, WebRequest request) {
        ex.printStackTrace();
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND, request);
    }


    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception ex, HttpStatus status, WebRequest request) {
        ErrorResponse exceptionResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionResponse, status);
    }


}
