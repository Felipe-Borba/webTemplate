package com.felipeborba.webTemplate.user.repository;

import com.felipeborba.webTemplate.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByLogin(String login);
}
