package com.webwizard.jobofferservice.repository;

import com.webwizard.jobofferservice.builder.JobOfferCriteriaBuilder;
import com.webwizard.jobofferservice.model.JobOffer;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
//TODO implement tests
public class JobOfferCriteriaRepository {

    private final EntityManager entityManager;

    public List<JobOffer> findAllByFilters(
            Integer salaryMin, Integer salaryMax,
            String technology, String employmentType,
            String experience, String operatingMode,
            String typeOfWork, String orderBy,
            String sortBy, Integer page,
            Integer pageSize
    ) {
        JobOfferCriteriaBuilder criteriaBuilder = new JobOfferCriteriaBuilder(entityManager);

        CriteriaQuery<JobOffer> query = criteriaBuilder
                .addSalaryMin(salaryMin)
                .addSalaryMax(salaryMax)
                .addMainTechnology(technology)
                .addEmploymentType(employmentType)
                .addExperience(experience)
                .addOperatingMode(operatingMode)
                .addTypeOfWork(typeOfWork)
                .setSortOrder(orderBy, sortBy)
                .build();

        Query typedQuery = entityManager.createQuery(query);

        int firstResult = (page - 1) * pageSize;
        typedQuery.setFirstResult(firstResult);
        typedQuery.setMaxResults(pageSize);

        return typedQuery.getResultList();
    }
}
