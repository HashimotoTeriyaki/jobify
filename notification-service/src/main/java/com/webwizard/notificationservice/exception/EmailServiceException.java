package com.webwizard.notificationservice.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailServiceException extends RuntimeException {
    private final String customMessage;
    private final String exceptionMessage;
}
