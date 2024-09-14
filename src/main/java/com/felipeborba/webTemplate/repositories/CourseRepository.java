package com.felipeborba.webTemplate.repositories;

import com.felipeborba.webTemplate.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
