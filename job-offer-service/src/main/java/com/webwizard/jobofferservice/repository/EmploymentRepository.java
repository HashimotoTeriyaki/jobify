package com.webwizard.jobofferservice.repository;

import com.webwizard.jobofferservice.model.Employment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmploymentRepository extends JpaRepository<Employment, Integer> {
}
