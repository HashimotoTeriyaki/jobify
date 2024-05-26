package com.webwizard.jobofferservice.repository;

import com.webwizard.jobofferservice.model.*;
import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
//TODO implement tests
public class JobOfferSearchDao {

    private final EntityManager entityManager;

    public List<JobOffer> findAllByFilters(
            Integer salaryMin,
            Integer salaryMax,
            String skill,
            String employmentType,
            String experience,
            String operatingMode,
            String typeOfWork
    ) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<JobOffer> criteriaQuery = criteriaBuilder.createQuery(JobOffer.class);
        Root<JobOffer> root = criteriaQuery.from(JobOffer.class);

        List<Predicate> predicates = new ArrayList<>();
        addSalaryPredicates(predicates, criteriaBuilder, root, salaryMin, salaryMax);
        addAttributeFilter(predicates, criteriaBuilder, root, "mainTechnology.name", skill);
        addAttributeFilter(predicates, criteriaBuilder, root, "experienceLevel.level", experience);
        addAttributeFilter(predicates, criteriaBuilder, root, "typeOfWork.name", typeOfWork);
        addAttributeFilter(predicates, criteriaBuilder, root, "employments.employmentType.name", employmentType);
        addAttributeFilter(predicates, criteriaBuilder, root, "offerOperatingModes.operatingMode.name", operatingMode);

        criteriaQuery.select(root).where(predicates.toArray(new Predicate[0]));
        TypedQuery<JobOffer> query = entityManager.createQuery(criteriaQuery);

        return query.getResultList();
    }

    private void addSalaryPredicates(List<Predicate> predicates, CriteriaBuilder criteriaBuilder, Root<JobOffer> root, Integer salaryMin, Integer salaryMax) {
        Join<JobOffer, Employment> employmentJoin = root.join("employments", JoinType.INNER);

        if (salaryMin != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(employmentJoin.get("salaryFrom"), salaryMin));
        }

        if (salaryMax != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(employmentJoin.get("salaryTo"), salaryMax));
        }
    }

    private void addAttributeFilter(List<Predicate> predicates, CriteriaBuilder criteriaBuilder, Root<JobOffer> root, String attributePath, String filterValue) {
        if (filterValue != null && !filterValue.isBlank()) {
            Path<String> path = getPath(root, attributePath);
            predicates.add(criteriaBuilder.equal(criteriaBuilder.upper(path), filterValue.toUpperCase()));
        }
    }

    private Path<String> getPath(Root<JobOffer> root, String attributePath) {
        String[] parts = attributePath.split("\\.");
        Path<String> path = root.get(parts[0]);
        for (int i = 1; i < parts.length; i++) {
            path = path.get(parts[i]);
        }
        return path;
    }
}
