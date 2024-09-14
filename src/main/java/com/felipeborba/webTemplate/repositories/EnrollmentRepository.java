package com.felipeborba.webTemplate.repositories;

import com.felipeborba.webTemplate.entities.Enrollment;
import com.felipeborba.webTemplate.entities.pk.EnrollmentPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentRepository extends JpaRepository<Enrollment, EnrollmentPK> {

}
