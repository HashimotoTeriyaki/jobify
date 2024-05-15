package com.webwizard.jobofferservice.repository;

import com.webwizard.jobofferservice.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Integer> {
    Skill findFirstByName(String name);
}
