package com.felipeborba.webTemplate.user.exception;

import com.felipeborba.webTemplate.exception.ApiException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends ApiException {
    public UserAlreadyExistsException() {
        super(HttpStatus.CONFLICT, "User already exists", 2000);
    }
}
