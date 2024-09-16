package com.felipeborba.webTemplate.user.repository;

import com.felipeborba.webTemplate.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByLogin(String login);
}
