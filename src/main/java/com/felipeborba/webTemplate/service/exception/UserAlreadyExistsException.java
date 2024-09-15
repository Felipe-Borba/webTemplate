package com.felipeborba.webTemplate.service.exception;

import com.felipeborba.webTemplate.controller.exception.ApiException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends ApiException {
    public UserAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "User already exists", 2000);
    }
}
