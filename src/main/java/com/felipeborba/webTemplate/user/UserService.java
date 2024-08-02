package com.felipeborba.webTemplate.user;

import com.felipeborba.webTemplate.user.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(String login, String password, UserRole role) {
        if (this.userRepository.findByLogin(login) != null) {
            throw new UserAlreadyExistsException();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(password);
        User newUser = new User(login, encryptedPassword, role);
        return this.userRepository.save(newUser);
    }
}
