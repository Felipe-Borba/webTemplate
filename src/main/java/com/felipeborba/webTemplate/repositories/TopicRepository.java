package com.felipeborba.webTemplate.repositories;

import com.felipeborba.webTemplate.entities.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {

}
