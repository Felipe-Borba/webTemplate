package com.felipeborba.webTemplate.dto;

import com.felipeborba.webTemplate.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO implements Serializable {
    private Long id;
    private String authrity;

    public RoleDTO(Role role) {
        this.id = role.getId();
        this.authrity = role.getAuthority();
    }
}
