package com.webwizard.jobofferservice.repository;

import com.webwizard.jobofferservice.model.OperatingMode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperatingModeRepository extends JpaRepository<OperatingMode, Integer> {
    OperatingMode findFirstByName(String name);
}
