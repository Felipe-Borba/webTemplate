package com.felipeborba.webTemplate.dto;

import com.felipeborba.webTemplate.services.validation.UserInsertValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@UserInsertValid
@NoArgsConstructor
@AllArgsConstructor
public class UserInsertDTO extends UserDTO {
    private String password;
}
