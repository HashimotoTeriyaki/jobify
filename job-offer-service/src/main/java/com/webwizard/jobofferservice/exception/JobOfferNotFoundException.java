package com.webwizard.jobofferservice.exception;

public class JobOfferNotFoundException extends APIException {
    public JobOfferNotFoundException(int code, String message) {
        super(code, message);
    }
}
