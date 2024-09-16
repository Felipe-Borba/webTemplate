package com.felipeborba.webTemplate.dto;

import com.felipeborba.webTemplate.service.validation.UserInsertValid;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String password;
}
