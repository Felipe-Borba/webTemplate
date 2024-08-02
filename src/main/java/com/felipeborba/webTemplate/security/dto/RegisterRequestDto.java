package com.felipeborba.webTemplate.security.dto;

import com.felipeborba.webTemplate.user.UserRole;

public record RegisterRequestDto(String login, String password, UserRole role) {
}
