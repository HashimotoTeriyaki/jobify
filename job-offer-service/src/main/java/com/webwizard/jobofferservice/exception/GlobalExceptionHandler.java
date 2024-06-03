package com.webwizard.jobofferservice.exception;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorsResponseDto> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorsResponseDto response = getErrorsResponse();
        ex.getBindingResult().getAllErrors().forEach(error ->
                response.addErrorsItem(new ErrorDto(
                                error.getDefaultMessage(),
                                ((FieldError) error).getField()
                        )
                ));
        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorsResponseDto> handleException(HttpMessageNotReadableException ex) {
        ErrorsResponseDto response = getErrorsResponse();
        response.addErrorsItem(new ErrorDto(ex.getMessage(), "field"));
        return ResponseEntity
                .badRequest()
                .body(response);
    }

    private static ErrorsResponseDto getErrorsResponse() {
        return ErrorsResponseDto
                .builder()
                .timestamp(OffsetDateTime.now().format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"))
                )
                .statusCode(400)
                .build();
    }

}
