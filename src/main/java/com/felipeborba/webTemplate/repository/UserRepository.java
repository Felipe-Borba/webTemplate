package com.felipeborba.webTemplate.repository;

import com.felipeborba.webTemplate.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByLogin(String login);
}
