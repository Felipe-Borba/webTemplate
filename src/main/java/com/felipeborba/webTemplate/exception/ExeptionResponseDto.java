package com.felipeborba.webTemplate.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
public class ExeptionResponseDto {
    private String message;
    private HttpStatus status;
    private Integer messageCode;

    public ExeptionResponseDto(ApiException apiException) {
        this.messageCode = apiException.getMessageCode();
        this.message = apiException.getMessage();
        this.status = apiException.getStatus();
    }

    public ResponseEntity<ExeptionResponseDto> toResponseEntity() {
        return ResponseEntity.status(this.status).body(this);
    }
}
