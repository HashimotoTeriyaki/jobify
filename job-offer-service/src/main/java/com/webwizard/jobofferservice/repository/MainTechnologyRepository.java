package com.webwizard.jobofferservice.repository;

import com.webwizard.jobofferservice.model.MainTechnology;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MainTechnologyRepository extends JpaRepository<MainTechnology, Integer> {
    MainTechnology findFirstByName(String name);
}
