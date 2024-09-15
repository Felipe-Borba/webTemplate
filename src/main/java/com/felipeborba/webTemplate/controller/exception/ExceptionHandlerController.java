package com.felipeborba.webTemplate.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExeptionResponseDto> runtimeExeptionHandler(RuntimeException exception) {
        return new ExeptionResponseDto(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, 0).toResponseEntity();
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ExeptionResponseDto> authorizationDeniedHandles(AuthorizationDeniedException exception) {
        return new ExeptionResponseDto(exception.getMessage(), HttpStatus.UNAUTHORIZED, 1001).toResponseEntity();
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ExeptionResponseDto> apiExeptionHandler(ApiException exception) {
        return new ExeptionResponseDto(exception).toResponseEntity();
    }
}
