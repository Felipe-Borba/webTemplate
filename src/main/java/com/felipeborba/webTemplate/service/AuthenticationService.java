package com.felipeborba.webTemplate.service;

import com.felipeborba.webTemplate.entity.User;
import com.felipeborba.webTemplate.entity.UserRole;
import com.felipeborba.webTemplate.repository.UserRepository;
import com.felipeborba.webTemplate.service.exception.ForbiddenException;
import com.felipeborba.webTemplate.service.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public String authenticate(Authentication authentication) {
        return jwtService.generateToken(authentication);
    }

    @Transactional(readOnly = true)
    public User authenticated() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByLogin(username).orElseThrow(() -> new UnauthorizedException("Invalid user"));
    }

    public void validateSelfOrAdmin(String userId) {
        User user = authenticated();
        if (!user.getId().equals(userId) && !user.hasHole(UserRole.ADMIN)) {
            throw new ForbiddenException("Access denied");
        }
    }
}
