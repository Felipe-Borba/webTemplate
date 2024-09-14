package com.felipeborba.webTemplate.repositories;

import com.felipeborba.webTemplate.entities.Notification;
import com.felipeborba.webTemplate.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	Page<Notification> findByUser(User user, Pageable pageable);
}
