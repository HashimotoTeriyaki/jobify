package com.webwizard.jobofferservice.exception;

import lombok.*;

@Getter
@AllArgsConstructor
public class APIException extends RuntimeException {
    private final int code;
    private final String message;
}
