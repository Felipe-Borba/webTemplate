package com.felipeborba.webTemplate.user.dto;

import com.felipeborba.webTemplate.user.service.validation.UserInsertValid;
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
