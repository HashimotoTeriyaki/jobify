package com.webwizard.jobofferservice.repository;

import com.webwizard.jobofferservice.model.ExperienceLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceLevelRepository extends JpaRepository<ExperienceLevel, Integer> {

    ExperienceLevel findFirstByLevel(String level);
}
