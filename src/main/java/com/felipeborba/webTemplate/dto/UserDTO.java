package com.felipeborba.webTemplate.dto;

import com.felipeborba.webTemplate.entities.Role;
import com.felipeborba.webTemplate.entities.User;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @Setter(AccessLevel.NONE)
    private Set<RoleDTO> roles;

    public UserDTO(User entity) {
        this.id = entity.getId();
        this.firstName = entity.getFirstName();
        this.lastName = entity.getFirstName();
        this.email = entity.getEmail();
        this.roles = entity.getRoles().stream().map(RoleDTO::new).collect(Collectors.toSet());
    }
}
