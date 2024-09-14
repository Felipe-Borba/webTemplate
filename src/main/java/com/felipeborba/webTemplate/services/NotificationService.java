package com.felipeborba.webTemplate.services;

import com.felipeborba.webTemplate.dto.NotificationDTO;
import com.felipeborba.webTemplate.entities.Notification;
import com.felipeborba.webTemplate.entities.User;
import com.felipeborba.webTemplate.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationService {

	@Autowired
	private NotificationRepository repository;

	@Autowired
	private AuthService authService;

	@Transactional(readOnly = true)
	public Page<NotificationDTO> notificationsForCurrentUser(Pageable pageable) {
		User user = authService.authenticated();
		Page<Notification> page = repository.findByUser(user, pageable);
		return page.map(x -> new NotificationDTO(x));
	}
}
