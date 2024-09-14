package com.felipeborba.webTemplate.repositories;

import com.felipeborba.webTemplate.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

}
