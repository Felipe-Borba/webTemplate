package com.felipeborba.webTemplate.repositories;

import com.felipeborba.webTemplate.entities.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

}
