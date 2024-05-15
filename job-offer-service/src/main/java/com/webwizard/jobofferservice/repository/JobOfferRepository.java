package com.webwizard.jobofferservice.repository;

import com.webwizard.jobofferservice.model.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobOfferRepository extends JpaRepository<JobOffer, Integer> {
}
