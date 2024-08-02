package com.felipeborba.webTemplate.security.exception;

import com.felipeborba.webTemplate.exception.ApiException;
import org.springframework.http.HttpStatus;

public class CreateTokenException extends ApiException {
    public CreateTokenException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Error while generate token", 1000);
    }
}
