package com.felipeborba.webTemplate.user.dto;

import com.felipeborba.webTemplate.user.entity.UserRole;

public record CreateUserRequestDto(String login, String password, UserRole role) {
}
