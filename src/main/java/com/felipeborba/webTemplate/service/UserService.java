package com.felipeborba.webTemplate.service;

import com.felipeborba.webTemplate.entity.User;
import com.felipeborba.webTemplate.entity.UserRole;
import com.felipeborba.webTemplate.repository.UserRepository;
import com.felipeborba.webTemplate.service.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User insert(String login, String password, UserRole role) {
        if (this.userRepository.findByLogin(login).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        String encryptedPassword = passwordEncoder.encode(password);
        User newUser = new User(login, encryptedPassword, role);
        return this.userRepository.save(newUser);
    }
}
