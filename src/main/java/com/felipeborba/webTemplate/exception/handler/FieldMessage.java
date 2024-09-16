package com.felipeborba.webTemplate.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FieldMessage implements Serializable {
    private String fieldName;
    private String message;
}
