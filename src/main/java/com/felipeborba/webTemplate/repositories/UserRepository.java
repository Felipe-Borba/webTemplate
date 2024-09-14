package com.felipeborba.webTemplate.repositories;

import com.felipeborba.webTemplate.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
}
