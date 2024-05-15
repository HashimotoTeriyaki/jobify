package com.webwizard.jobofferservice.repository;

import com.webwizard.jobofferservice.model.EmploymentType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmploymentTypeRepository extends JpaRepository<EmploymentType, Integer> {
    EmploymentType findFirstByName(String name);
}
