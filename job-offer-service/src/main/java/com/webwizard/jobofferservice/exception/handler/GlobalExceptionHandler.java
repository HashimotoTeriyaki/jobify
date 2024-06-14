package com.webwizard.jobofferservice.exception.handler;

import com.webwizard.jobofferservice.exception.JobOfferNotFoundException;
import com.webwizard.jobofferservice.openapi.v1.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String ERROR_PATH = "/error";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ValidationErrorResponseDto response = getErrorsResponse();
        ex.getBindingResult().getAllErrors().forEach(error ->
                response.addErrorsItem(new ValidationErrorDto(
                                ((FieldError) error).getField(),
                                error.getDefaultMessage()
                        )
                ));
        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(JobOfferNotFoundException.class)
    public ResponseEntity<ErrorDto> handleException(JobOfferNotFoundException ex) {
        ErrorDto response = ErrorDto.builder()
                .timestamp(getCurrentDateString())
                .message(ex.getMessage())
                .status(ex.getCode())
                .path(ERROR_PATH)
                .build();
        return ResponseEntity
                .status(ex.getCode())
                .body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDto> handleException(HttpMessageNotReadableException ex) {
        ErrorDto response = ErrorDto.builder()
                .timestamp(getCurrentDateString())
                .message(ex.getMessage())
                .status(400)
                .path(ERROR_PATH)
                .build();
        return ResponseEntity
                .badRequest()
                .body(response);
    }


    private static ValidationErrorResponseDto getErrorsResponse() {
        return ValidationErrorResponseDto
                .builder()
                .timestamp(getCurrentDateString())
                .status(400)
                .path(ERROR_PATH)
                .build();
    }

    private static String getCurrentDateString() {
        return OffsetDateTime.now().format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        );
    }
}
