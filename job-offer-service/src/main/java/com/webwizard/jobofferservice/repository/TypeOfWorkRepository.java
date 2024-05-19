package com.webwizard.jobofferservice.repository;

import com.webwizard.jobofferservice.model.TypeOfWork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeOfWorkRepository extends JpaRepository<TypeOfWork, Integer> {
    TypeOfWork findFirstByName(String name);
}
