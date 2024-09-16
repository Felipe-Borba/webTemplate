package com.felipeborba.webTemplate.dto;

import com.felipeborba.webTemplate.entity.User;
import com.felipeborba.webTemplate.entity.UserRole;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    private String id;
    @NotBlank
    private String login;
    @Enumerated(EnumType.STRING)
    @NotNull
    private UserRole role;

    public UserDTO(User entity) {
        this.id = entity.getId();
        this.login = entity.getLogin();
        this.role = entity.getRole();
    }
}
