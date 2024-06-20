package com.webwizard.jobofferservice.builder;

import com.webwizard.jobofferservice.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JobOfferCriteriaBuilder {

    private final CriteriaBuilder builder;
    private final CriteriaQuery<JobOffer> query;
    private final Root<JobOffer> jobOfferRoot;
    private final List<Predicate> predicates;
    private final Join<JobOffer, Employment> employmentJoin;

    public JobOfferCriteriaBuilder(EntityManager entityManager) {
        this.builder = entityManager.getCriteriaBuilder();
        this.query = builder.createQuery(JobOffer.class);
        this.jobOfferRoot = query.from(JobOffer.class);
        this.predicates = new ArrayList<>();
        this.employmentJoin = jobOfferRoot.join("employments", JoinType.INNER);
    }

    public JobOfferCriteriaBuilder addSalaryMin(Integer salaryMin) {
        if (salaryMin != null) {
            predicates.add(builder.greaterThanOrEqualTo(
                    employmentJoin.get("salaryFrom"), salaryMin)
            );
        }
        return this;
    }

    public JobOfferCriteriaBuilder addSalaryMax(Integer salaryMax) {
        if (salaryMax != null) {
            predicates.add(builder.lessThanOrEqualTo(
                    employmentJoin.get("salaryTo"), salaryMax)
            );
        }
        return this;
    }

    public JobOfferCriteriaBuilder addMainTechnology(String mainTechnology) {
        if (mainTechnology != null && !mainTechnology.isBlank()) {
            predicates.add(builder.equal(
                    jobOfferRoot.join("mainTechnology").get("name"), mainTechnology.toUpperCase()
            ));
        }
        return this;
    }

    public JobOfferCriteriaBuilder addEmploymentType(String employmentType) {
        if (employmentType != null && !employmentType.isBlank()) {
            predicates.add(builder.equal(
                    employmentJoin.join("employmentType").get("name"), employmentType.toUpperCase()
            ));
        }
        return this;
    }

    public JobOfferCriteriaBuilder addExperience(String experience) {
        if (experience != null && !experience.isBlank()) {
            predicates.add(builder.equal(
                    jobOfferRoot.join("experienceLevel").get("level"), experience.toUpperCase()
            ));
        }
        return this;
    }

    public JobOfferCriteriaBuilder addOperatingMode(String operatingMode) {
        if (operatingMode != null && !operatingMode.isBlank()) {
            predicates.add(builder.equal(
                    jobOfferRoot
                            .join("offerOperatingModes")
                            .join("operatingMode").get("name"), operatingMode.toUpperCase()
            ));
        }
        return this;
    }

    public JobOfferCriteriaBuilder addTypeOfWork(String typeOfWork) {
        if (typeOfWork != null && !typeOfWork.isBlank()) {
            predicates.add(builder.equal(
                    jobOfferRoot.join("typeOfWork").get("name"), typeOfWork.toUpperCase()
            ));
        }
        return this;
    }

    public JobOfferCriteriaBuilder setSortOrder(String orderBy, String sortDirection) {
        if (orderBy.equals("salary")) {
            sortBySalary(sortDirection);
        } else {
            sortByCreationDate();
        }
        return this;
    }

    private void sortBySalary(String sortDirection) {
        query.groupBy(jobOfferRoot.get("id"));
        if (sortDirection.equals("ASC")) {
            query.orderBy(builder.asc(builder.min(employmentJoin.get("salaryFrom"))));
        } else {
            query.orderBy(builder.desc(builder.max(employmentJoin.get("salaryTo"))));
        }
    }

    private void sortByCreationDate() {
        query.orderBy(builder.desc(jobOfferRoot.get("createdDate")));
    }

    public CriteriaQuery<JobOffer> build() {
        query.select(jobOfferRoot);
        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[0]));
        }
        return query;
    }
}
